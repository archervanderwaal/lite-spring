package me.stormma.litespring.beans.factory.support;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.factory.BeanCreationException;
import me.stormma.litespring.beans.factory.BeanDefinitionStoreException;
import me.stormma.litespring.beans.factory.BeanFactory;
import me.stormma.litespring.utils.ClassUtils;
import me.stormma.litespring.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class DefaultBeanFactory implements BeanFactory {

    /**
     * id attribute in xml properties, convenient dom4j analysis
     */
    public static final String ID_ATTRIBUTE = "id";

    /**
     * class attribute in xml properties, convenient dom4j analysis
     */
    public static final String CLASS_ATTRIBUTE = "class";

    /**
     * load bean definitions to map
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public DefaultBeanFactory(String resource) {
        if (StringUtils.isNullOrEmpty(resource)) {
            throw new IllegalArgumentException("bean definition configuration file " +
                    "must be not null, [ resource = " + resource + "]");
        }
        loadBeanDefinition(resource);
    }

    public BeanDefinition getBeanDefinition(String beanId) {
        if (StringUtils.isNullOrEmpty(beanId)) {
            throw new IllegalArgumentException("bean's attribute id must be not null, [ beanID = " + beanId + " ]");
        }
        return this.beanDefinitionMap.get(beanId);
    }

    /**
     * @param beanId
     * @return bean's instance, guarantee that the result is not null.
     */
    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanId);
        if (Objects.isNull(beanDefinition)) {
            throw new BeanCreationException(String.format("bean definition '[beanId = %s]' does not exist.", beanId));
        }
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> beanClass = classLoader.loadClass(beanClassName);
            // non-parametric constructor
            return beanClass.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " failed.");
        }
    }

    private void loadBeanDefinition(String resource) {
        InputStream resourceStream = null;
        try {
            ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
            resourceStream = classLoader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(resourceStream);
            /*get beans "<beans>" label*/
            Element root = document.getRootElement();
            Iterator elementIterator = root.elementIterator();
            while (elementIterator.hasNext()) {
                Element element = (Element) elementIterator.next();
                String beanId = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefinition(beanId, beanClassName);
                this.beanDefinitionMap.put(beanId, beanDefinition);
            }
        } catch (DocumentException e) {
            throw new BeanDefinitionStoreException("parse xml configuration file failed, " +
                    "please check xml configuration file is there or spelling errors, 'resource = " + resource + "'.");
        } finally {
            if (!Objects.isNull(resourceStream)) {
                try {
                    resourceStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

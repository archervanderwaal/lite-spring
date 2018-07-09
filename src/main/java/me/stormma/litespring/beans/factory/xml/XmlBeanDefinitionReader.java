package me.stormma.litespring.beans.factory.xml;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.factory.BeanDefinitionStoreException;
import me.stormma.litespring.beans.factory.support.BeanDefinitionRegistry;
import me.stormma.litespring.beans.factory.support.GenericBeanDefinition;
import me.stormma.litespring.core.io.Resource;
import me.stormma.litespring.utils.ClassUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class XmlBeanDefinitionReader {

    /**
     * id attribute in xml properties, convenient dom4j analysis
     */
    public static final String ID_ATTRIBUTE = "id";

    /**
     * class attribute in xml properties, convenient dom4j analysis
     */
    public static final String CLASS_ATTRIBUTE = "class";

    /**
     * bean definition registry
     */
    private final BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * load bean definitions in xml resource configuration file.
     * <pre>as:</pre>
     * <code>
     *     BeanFactory factory = new DefaultBeanFactory("xxx.xml");
     * </code>
     * the xxx.xml is xml resource configuration file
     * @param resource
     */
    @Deprecated
    public void loadBeanDefinition(String resource) {
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
                registry.registerBeanDefinition(beanId, beanDefinition);
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

    /**
     * load bean definitions in xml resource configuration file.
     * <pre>as:</pre>
     * <code>
     *     BeanFactory factory = new DefaultBeanFactory("xxx.xml");
     * </code>
     * the xxx.xml is xml resource configuration file
     * @param resource
     */
    public void loadBeanDefinition(Resource resource) {
        InputStream resourceStream = null;
        try {
            resourceStream = resource.getInputStream();
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
                registry.registerBeanDefinition(beanId, beanDefinition);
            }
        } catch (DocumentException | FileNotFoundException e) {
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

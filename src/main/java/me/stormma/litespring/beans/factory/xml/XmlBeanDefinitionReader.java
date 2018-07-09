package me.stormma.litespring.beans.factory.xml;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.BeanScope;
import me.stormma.litespring.beans.factory.BeanDefinitionStoreException;
import me.stormma.litespring.beans.factory.support.BeanDefinitionRegistry;
import me.stormma.litespring.beans.factory.support.GenericBeanDefinition;
import me.stormma.litespring.core.io.Resource;
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
     * scope attribute in xml properties, singleton or prototype
     */
    public static final String SCOPE_ATTRIBUTE = "scope";

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
                String scope = element.attributeValue(SCOPE_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefinition(beanId, beanClassName);
                if (!Objects.isNull(scope)) {
                    beanDefinition.setScope(scope.equalsIgnoreCase("singleton") ? BeanScope.SINGLETON : BeanScope.PROTOTYPE);
                } else {
                    beanDefinition.setScope(BeanScope.DEFAULT);
                }
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

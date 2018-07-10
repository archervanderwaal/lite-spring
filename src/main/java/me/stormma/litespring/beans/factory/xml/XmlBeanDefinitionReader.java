package me.stormma.litespring.beans.factory.xml;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.BeanScope;
import me.stormma.litespring.beans.PropertyValue;
import me.stormma.litespring.beans.factory.BeanDefinitionStoreException;
import me.stormma.litespring.beans.factory.config.RuntimeBeanReference;
import me.stormma.litespring.beans.factory.config.TypedStringValue;
import me.stormma.litespring.beans.factory.support.BeanDefinitionRegistry;
import me.stormma.litespring.beans.factory.support.GenericBeanDefinition;
import me.stormma.litespring.core.io.Resource;
import me.stormma.litespring.utils.StringUtils;
import org.apache.log4j.Logger;
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
     * property attribute in xml properties
     */
    public static final String PROPERTY_ATTRIBUTE = "property";

    /**
     * reference attribute in xml properties
     */
    public static final String REFERENCE_ATTRIBUTE = "ref";

    /**
     * value attribute in xml properties
     */
    public static final String VALUE_ATTRIBUTE = "value";

    /**
     * name attribute in xml properties
     */
    public static final String NAME_ATTRIBUTE = "name";

    /**
     * bean definition registry
     */
    private final BeanDefinitionRegistry registry;

    protected final Logger logger = Logger.getLogger(XmlBeanDefinitionReader.class);

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * load bean definitions in xml resource configuration file.
     * <pre>following:</pre>
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
                parsePropertyElement(element, beanDefinition);
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

    /**
     * if xml configuration file has following code.
     * <code>
     *     <bean name="vipDao" class="me.stormma.vipDao"/>
     *     <bean name="userDao" class="me.stormma.UserDao">
     *         <property name="vipDao" ref="vipDao"/>
     *     </bean>
     * </code>
     * the function of this method is to parse <property> label and set propertyValues property of BeanDefinition,
     * For the above configuration file, the propertyValues property of BeanDefinition is:
     * [{name="vipDao", value="vipDao"(RuntimeBeanReference)}]
     * @param element
     * @param beanDefinition
     */
    private void parsePropertyElement(Element element, BeanDefinition beanDefinition) {
        Iterator elementIterator = element.elementIterator(PROPERTY_ATTRIBUTE);
        while (elementIterator.hasNext()) {
            Element propertyElement = (Element) elementIterator.next();
            String propertyName = propertyElement.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                logger.fatal("The 'property' label must have 'name' attribute.");
                return;
            }
            Object value = parsePropertyValue(propertyElement, beanDefinition, propertyName);
            PropertyValue propertyValue = new PropertyValue(propertyName, value);
            beanDefinition.getPropertyValues().add(propertyValue);
        }
    }

    /**
     * The auxiliary method of the above method parsePropertyElement, the function of this method is get of real value by
     * propertyName.
     * @param propertyElement
     * @param beanDefinition
     * @param propertyName
     * @return
     */
    private Object parsePropertyValue(Element propertyElement, BeanDefinition beanDefinition, String propertyName) {
        String elementName = StringUtils.isNotNullOrEmpty(propertyName) ?
                "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element";

        boolean hasReferenceAttribute = !Objects.isNull(propertyElement.attribute(REFERENCE_ATTRIBUTE));
        boolean hasValueAttribute = !Objects.isNull(propertyElement.attributeValue(VALUE_ATTRIBUTE));
        if (hasReferenceAttribute) {
            String refName = propertyElement.attributeValue(REFERENCE_ATTRIBUTE);
            if (!StringUtils.hasText(refName)) {
                logger.error(elementName + " contains empty 'ref' attribute");
            }
            return new RuntimeBeanReference(refName);
        } else if (hasValueAttribute) {
            return new TypedStringValue(propertyElement.attributeValue(VALUE_ATTRIBUTE));
        } else {
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }
}

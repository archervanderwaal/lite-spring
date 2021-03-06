package com.archervanderwaal.litespring.beans.factory.xml;

import com.archervanderwaal.litespring.beans.BeanDefinition;
import com.archervanderwaal.litespring.beans.factory.support.GenericBeanDefinition;
import com.archervanderwaal.litespring.context.annotation.ClassPathBeanDefinitionScanner;
import com.archervanderwaal.litespring.beans.BeanScope;
import com.archervanderwaal.litespring.beans.ConstructorArgument;
import com.archervanderwaal.litespring.beans.PropertyValue;
import com.archervanderwaal.litespring.beans.factory.BeanDefinitionStoreException;
import com.archervanderwaal.litespring.beans.factory.config.RuntimeBeanReference;
import com.archervanderwaal.litespring.beans.factory.config.TypedStringValue;
import com.archervanderwaal.litespring.beans.factory.support.BeanDefinitionRegistry;
import com.archervanderwaal.litespring.core.io.Resource;
import com.archervanderwaal.litespring.utils.StringUtils;
import com.archervanderwaal.litespring.aop.config.ConfigBeanDefinitionParser;
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
     * constructor_arg element
     */
    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

    /**
     * type attribute in constructor_arg label
     */
    public static final String TYPE_ATTRIBUTE = "type";

    /**
     * beans namespace
     */
    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    /**
     * context namespace
     */
    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";

    /**
     * base package attribute
     */
    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    /**
     * aop namespace uri
     */
    private static final String AOP_NAMESPACE_URI = "http://www.springframework.org/schema/aop";

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
                String namespaceUri = element.getNamespaceURI();
                if (this.isDefaultNamespace(namespaceUri)) {
                    // default bean
                    parseDefaultElement(element);
                } else if (this.isContextNamespace(namespaceUri)) {
                    // <context:component-scan>
                    parseComponentElement(element);
                } else if (this.isAOPNameSpace(namespaceUri)) {
                    parseAOPElement(element);
                }
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

    private void parseDefaultElement(Element element) {
        String beanId = element.attributeValue(ID_ATTRIBUTE);
        String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
        String scope = element.attributeValue(SCOPE_ATTRIBUTE);
        BeanDefinition beanDefinition = new GenericBeanDefinition(beanId, beanClassName);
        if (!Objects.isNull(scope)) {
            beanDefinition.setScope(scope.equalsIgnoreCase(
                    "singleton") ? BeanScope.SINGLETON : BeanScope.PROTOTYPE);
        } else {
            beanDefinition.setScope(BeanScope.DEFAULT);
        }
        parseConstructorArgumentElements(element, beanDefinition);
        parsePropertyElement(element, beanDefinition);
        registry.registerBeanDefinition(beanId, beanDefinition);
    }

    private void parseComponentElement(Element element) {
        String basePackages = element.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(this.registry);
        scanner.doScan(basePackages);
    }

    private void parseAOPElement(Element element) {
        ConfigBeanDefinitionParser parser = new ConfigBeanDefinitionParser();
        parser.parse(element, this.registry);
    }

    private boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    private boolean isContextNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }

    private boolean isAOPNameSpace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || AOP_NAMESPACE_URI.equals(namespaceUri));
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
        // if propertyName is not null or not empty, this case is <constructor-arg> label's attribute, else is
        // <property> label's attribute
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

    private void parseConstructorArgumentElements(Element beanElement, BeanDefinition beanDefinition) {
        Iterator elementIterator = beanElement.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while(elementIterator.hasNext()){
            Element constructorArgumentElement = (Element) elementIterator.next();
            parseConstructorArgumentElement(constructorArgumentElement, beanDefinition);
        }
    }

    private void parseConstructorArgumentElement(Element constructorArgumentElement, BeanDefinition beanDefinition) {
        String propertyType = constructorArgumentElement.attributeValue(TYPE_ATTRIBUTE);
        String propertyName = constructorArgumentElement.attributeValue(NAME_ATTRIBUTE);
        Object value = parsePropertyValue(constructorArgumentElement, beanDefinition, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
        if (StringUtils.hasLength(propertyType)) {
            valueHolder.setType(propertyType);
        }
        if (StringUtils.hasLength(propertyName)) {
            valueHolder.setName(propertyName);
        }
        beanDefinition.getConstructorArgument().addArgumentValue(valueHolder);
    }
}

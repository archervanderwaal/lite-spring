package me.stormma.litespring.test.v4;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import me.stormma.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import me.stormma.litespring.context.annotation.ScannedGenericBeanDefinition;
import me.stormma.litespring.core.annotation.AnnotationAttributes;
import me.stormma.litespring.core.io.ClassPathResource;
import me.stormma.litespring.core.io.Resource;
import me.stormma.litespring.core.type.AnnotationMetadata;
import me.stormma.litespring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/23
 */
public class XmlBeanDefinitionTestV4 {

    @Test
    public void testParseScannedBean() {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        beanDefinitionReader.loadBeanDefinition(resource);
        String component = Component.class.getName();

        {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition("petStore");
            Assert.assertTrue(beanDefinition instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition scannedGenericBeanDefinition = (ScannedGenericBeanDefinition) beanDefinition;
            AnnotationMetadata metadata = scannedGenericBeanDefinition.getMetadata();

            Assert.assertTrue(metadata.hasAnnotation(component));
            AnnotationAttributes annotationAttributes = metadata.getAnnotationAttributes(component);
            Assert.assertEquals("petStore", annotationAttributes.getString("value"));
        }

        {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition("accountDao");
            Assert.assertTrue(beanDefinition instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition scannedGenericBeanDefinition = (ScannedGenericBeanDefinition) beanDefinition;
            AnnotationMetadata metadata = scannedGenericBeanDefinition.getMetadata();

            Assert.assertTrue(metadata.hasAnnotation(component));
            AnnotationAttributes annotationAttributes = metadata.getAnnotationAttributes(component);
            Assert.assertEquals("accountDao", annotationAttributes.getString("value"));
        }

        {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition("itemDao");
            Assert.assertTrue(beanDefinition instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition scannedGenericBeanDefinition = (ScannedGenericBeanDefinition) beanDefinition;
            AnnotationMetadata metadata = scannedGenericBeanDefinition.getMetadata();

            Assert.assertTrue(metadata.hasAnnotation(component));
            AnnotationAttributes annotationAttributes = metadata.getAnnotationAttributes(component);
            Assert.assertEquals("itemDao", annotationAttributes.getString("value"));
        }
    }
}

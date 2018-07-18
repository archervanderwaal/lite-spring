package me.stormma.litespring.test.v4;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.context.annotation.ClassPathBeanDefinitionScanner;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import me.stormma.litespring.context.annotation.ScannedGenericBeanDefinition;
import me.stormma.litespring.core.type.AnnotationMetadata;
import me.stormma.litespring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/18
 */
public class ClassPathBeanDefinitionScannerTestV4 {

    @Test
    public void testParseScannedBean() {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        String basePackage = "me.stormma.litespring.test.v4.entity";
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(beanFactory);
        classPathBeanDefinitionScanner.doScan(basePackage);
        String annotation = Component.class.getName();
        {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition("petStore");
            Assert.assertTrue(beanDefinition instanceof ScannedGenericBeanDefinition);

            AnnotationMetadata annotationMetadata = ((ScannedGenericBeanDefinition) beanDefinition).getMetadata();
            Assert.assertEquals("petStore", annotationMetadata.getAnnotationAttributes(annotation).getString("value"));
        }
    }
}

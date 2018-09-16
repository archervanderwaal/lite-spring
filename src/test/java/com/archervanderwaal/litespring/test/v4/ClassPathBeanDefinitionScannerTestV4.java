package com.archervanderwaal.litespring.test.v4;

import com.archervanderwaal.litespring.beans.BeanDefinition;
import com.archervanderwaal.litespring.context.annotation.ClassPathBeanDefinitionScanner;
import com.archervanderwaal.litespring.beans.factory.support.DefaultBeanFactory;
import com.archervanderwaal.litespring.context.annotation.ScannedGenericBeanDefinition;
import com.archervanderwaal.litespring.core.type.AnnotationMetadata;
import com.archervanderwaal.litespring.stereotype.Component;
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
        String basePackage = "com.archervanderwaal.litespring.test.v4.entity";
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

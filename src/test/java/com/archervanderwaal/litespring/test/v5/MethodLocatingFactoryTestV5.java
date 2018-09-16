package com.archervanderwaal.litespring.test.v5;

import com.archervanderwaal.litespring.aop.config.MethodLocatingFactory;
import com.archervanderwaal.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import com.archervanderwaal.litespring.beans.factory.support.DefaultBeanFactory;
import com.archervanderwaal.litespring.core.io.ClassPathResource;
import com.archervanderwaal.litespring.test.v5.tx.TransactionManager;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class MethodLocatingFactoryTestV5 {

    @Test
    public void testGetMethod() throws Exception {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v5.xml"));

        MethodLocatingFactory locatingFactory = new MethodLocatingFactory();
        locatingFactory.setTargetBeanName("tx");
        locatingFactory.setMethodName("start");
        locatingFactory.setBeanFactory(beanFactory);

        Method method = (Method) locatingFactory.getObject();
        Assert.assertEquals(method, TransactionManager.class.getMethod("start"));
    }
}

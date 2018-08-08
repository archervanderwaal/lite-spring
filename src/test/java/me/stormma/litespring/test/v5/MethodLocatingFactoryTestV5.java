package me.stormma.litespring.test.v5;

import me.stormma.litespring.aop.config.MethodLocatingFactory;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import me.stormma.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import me.stormma.litespring.core.io.ClassPathResource;
import me.stormma.litespring.test.v5.tx.TransactionManager;
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

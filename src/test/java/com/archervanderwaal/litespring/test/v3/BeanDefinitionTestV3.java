package com.archervanderwaal.litespring.test.v3;

import com.archervanderwaal.litespring.beans.BeanDefinition;
import com.archervanderwaal.litespring.beans.factory.support.DefaultBeanFactory;
import com.archervanderwaal.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import com.archervanderwaal.litespring.beans.ConstructorArgument;
import com.archervanderwaal.litespring.core.io.ClassPathResource;
import com.archervanderwaal.litespring.utils.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/11
 */
public class BeanDefinitionTestV3 {

    private static final String resource = "petstore-v3.xml";

    private DefaultBeanFactory factory;

    private XmlBeanDefinitionReader reader;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource(resource));
    }

    @Test
    public void testConstructorArgument() {
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
        ConstructorArgument constructorArgument = beanDefinition.getConstructorArgument();
        List<ConstructorArgument.ValueHolder> valueHolders = constructorArgument.getArgumentValues();
        Assert.assertTrue(CollectionUtils.isNotNullOrEmpty(valueHolders));
    }
}

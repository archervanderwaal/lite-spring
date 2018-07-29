package me.stormma.litespring.test.v3;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.ConstructorArgument;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import me.stormma.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import me.stormma.litespring.core.io.ClassPathResource;
import me.stormma.litespring.utils.CollectionUtils;
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

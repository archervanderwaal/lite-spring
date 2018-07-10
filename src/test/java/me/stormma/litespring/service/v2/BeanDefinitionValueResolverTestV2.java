package me.stormma.litespring.service.v2;

import me.stormma.litespring.beans.factory.config.RuntimeBeanReference;
import me.stormma.litespring.beans.factory.config.TypedStringValue;
import me.stormma.litespring.beans.factory.support.BeanDefinitionValueResolver;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import me.stormma.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import me.stormma.litespring.core.io.ClassPathResource;
import me.stormma.litespring.service.v2.entity.AccountDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class BeanDefinitionValueResolverTestV2 {

    protected final String configurationFilePath = "petstore-v2.xml";

    private DefaultBeanFactory factory;

    private XmlBeanDefinitionReader reader;

    @Before
    public void setUp() throws Exception {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource(configurationFilePath));
    }

    @Test
    public void testResolveRuntimeBeanReference() {
        // create resolver
        BeanDefinitionValueResolver beanDefinitionValueResolver = new BeanDefinitionValueResolver(factory);
        RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference("accountDao");
        Object value = beanDefinitionValueResolver.resolveValueIfNecessary(runtimeBeanReference);
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void testResolveTypedStringValue() {
        BeanDefinitionValueResolver beanDefinitionValueResolver = new BeanDefinitionValueResolver(factory);
        TypedStringValue typedStringValue = new TypedStringValue("test");
        Object value = beanDefinitionValueResolver.resolveValueIfNecessary(typedStringValue);
        Assert.assertNotNull(value);
        Assert.assertEquals("test", value);
    }
}

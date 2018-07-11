package me.stormma.litespring.test.v3;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.factory.support.ConstructorResolver;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import me.stormma.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import me.stormma.litespring.core.io.ClassPathResource;
import me.stormma.litespring.test.v3.entity.PetStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/11
 */
public class ConstructorResolverTestV3 {

    private DefaultBeanFactory factory;

    private XmlBeanDefinitionReader reader;

    private static final String resource = "petstore-v3.xml";

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource(resource));
    }

    @Test
    public void testAutowireConstructor() {
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
        ConstructorResolver resolver = new ConstructorResolver(factory);
        PetStoreService petStoreService = (PetStoreService) resolver.autowireConstructor(beanDefinition);
        Assert.assertNotNull(petStoreService.getItemDao());
        Assert.assertTrue(petStoreService.getVersion() != 0);
        Assert.assertNotNull(petStoreService.getItemDao());
    }
}

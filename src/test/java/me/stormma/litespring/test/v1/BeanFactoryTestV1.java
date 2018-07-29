package me.stormma.litespring.test.v1;

import me.stormma.litespring.beans.factory.BeanCreationException;
import me.stormma.litespring.beans.factory.BeanDefinitionStoreException;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import me.stormma.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import me.stormma.litespring.core.io.ClassPathResource;
import me.stormma.litespring.test.v1.entity.PetStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class BeanFactoryTestV1 {

    private DefaultBeanFactory defaultBeanFactory = null;

    private XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp() {
        defaultBeanFactory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(defaultBeanFactory);
    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        PetStoreService petStoreService = (PetStoreService) defaultBeanFactory.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }

    @Test
    public void testGetSingletonBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        PetStoreService petStoreService1 = (PetStoreService) defaultBeanFactory.getBean("petStore");
        PetStoreService petStoreService2 = (PetStoreService) defaultBeanFactory.getBean("petStore");
        Assert.assertEquals(petStoreService1, petStoreService2);
    }

    @Test
    public void testGetPrototypeBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        PetStoreService petStoreService1 = (PetStoreService) defaultBeanFactory.getBean("petStorePrototype");
        PetStoreService petStoreService2 = (PetStoreService) defaultBeanFactory.getBean("petStorePrototype");
        Assert.assertNotEquals(petStoreService1, petStoreService2);
    }

    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        try {
            defaultBeanFactory.getBean("invalidBean");
        } catch (BeanCreationException exception) {
            return;
        }
        Assert.fail("expect BeanCreatingException ");
    }

    @Test
    public void testInvalidXMLResourceFile() {
        try {
            reader.loadBeanDefinition(new ClassPathResource("invalid.xml"));
        } catch (BeanDefinitionStoreException exception) {
            exception.printStackTrace();
            return;
        }
        Assert.fail("expect BeanDefinitionException.");
    }
}

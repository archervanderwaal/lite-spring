package me.stormma.litespring.service.v1;

import me.stormma.litespring.beans.factory.BeanCreationException;
import me.stormma.litespring.beans.factory.BeanDefinitionStoreException;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import me.stormma.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import me.stormma.litespring.service.v1.entity.PetStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class BeanFactoryTest {

    private DefaultBeanFactory defaultBeanFactory = null;

    private XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp() {
        defaultBeanFactory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(defaultBeanFactory);
    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefinition("petstore-v1.xml");
        PetStoreService petStoreService = (PetStoreService) defaultBeanFactory.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }

    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinition("petstore-v1.xml");
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
            reader.loadBeanDefinition("invalid.xml");
        } catch (BeanDefinitionStoreException exception) {
            exception.printStackTrace();
            return;
        }
        Assert.fail("expect BeanDefinitionException.");
    }
}

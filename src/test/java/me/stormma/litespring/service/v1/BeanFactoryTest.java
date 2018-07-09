package me.stormma.litespring.service.v1;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.factory.BeanCreationException;
import me.stormma.litespring.beans.factory.BeanDefinitionStoreException;
import me.stormma.litespring.beans.factory.BeanFactory;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class BeanFactoryTest {

    @Test
    public void testGetBean() {
        BeanFactory beanFactory = new DefaultBeanFactory("petstore-v1.xml");
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("petStore");
        Assert.assertEquals("me.stormma.litespring.service.v1.PetStoreService", beanDefinition.getBeanClassName());
        PetStoreService petStoreService  = (PetStoreService) beanFactory.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }

    @Test
    public void testInvalidBean() {
        BeanFactory beanFactory = new DefaultBeanFactory("petstore-v1.xml");
        try {
            beanFactory.getBean("invalidBean");
        } catch (BeanCreationException exception) {
            return;
        }
        Assert.fail("expect BeanCreatingException ");
    }

    @Test
    public void testInvalidXMLResourceFile() {
        try {
            BeanFactory beanFactory = new DefaultBeanFactory("invalid.xml");
        } catch (BeanDefinitionStoreException exception) {
            exception.printStackTrace();
            return;
        }
        Assert.fail("expect BeanDefinitionException.");
    }
}

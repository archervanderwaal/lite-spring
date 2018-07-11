package me.stormma.litespring.test.v3;

import me.stormma.litespring.beans.context.ApplicationContext;
import me.stormma.litespring.beans.context.support.ClassPathXmlApplicationContext;
import me.stormma.litespring.test.v3.entity.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/11
 */
public class ApplicationContextTestV3 {

    private static final String resource = "petstore-v3.xml";

    @Test
    public void testGetBeanProperty() {
        ApplicationContext context = new ClassPathXmlApplicationContext(resource);
        PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
        Assert.assertTrue(petStoreService.getVersion() != 0);
    }
}

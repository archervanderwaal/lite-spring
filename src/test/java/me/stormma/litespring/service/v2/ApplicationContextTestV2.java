package me.stormma.litespring.service.v2;

import me.stormma.litespring.beans.context.ApplicationContext;
import me.stormma.litespring.beans.context.support.ClassPathXmlApplicationContext;
import me.stormma.litespring.service.v2.entity.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ApplicationContextTestV2 {

    private static final String configurationFilePath = "petstore-v2.xml";

    @Test
    public void testGetBeanIncludeProperty() {
        ApplicationContext context = new ClassPathXmlApplicationContext(configurationFilePath);
        PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");

        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }
}

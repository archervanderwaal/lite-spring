package me.stormma.litespring.test.v4;

import me.stormma.litespring.beans.context.ApplicationContext;
import me.stormma.litespring.beans.context.support.ClassPathXmlApplicationContext;
import me.stormma.litespring.test.v4.entity.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/13
 */
public class ApplicationTextTestV4 {

    private static final String resource = "petstore-v4.xml";

    @Test
    public void testGetBeanFactory() {
        ApplicationContext context = new ClassPathXmlApplicationContext(resource);
        PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }
}

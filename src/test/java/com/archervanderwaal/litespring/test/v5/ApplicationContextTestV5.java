package com.archervanderwaal.litespring.test.v5;

import com.archervanderwaal.litespring.context.ApplicationContext;
import com.archervanderwaal.litespring.context.support.ClassPathXmlApplicationContext;
import com.archervanderwaal.litespring.test.v5.entity.PetStoreService;
import com.archervanderwaal.litespring.test.v5.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ApplicationContextTestV5 {

    @Before
    public void before() {
        MessageTracker.clearMessages();
    }

    @Test
    public void testPlaceOrder() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v5.xml");
        PetStoreService petStoreService = (PetStoreService) applicationContext.getBean("petStore");

        Assert.assertNotNull(petStoreService);
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
        petStoreService.placeOrder();

        List<String> messages = MessageTracker.getMessages();
        Assert.assertEquals("start tx", messages.get(0));
        Assert.assertEquals("place order", messages.get(1));
        Assert.assertEquals("commit tx", messages.get(2));
    }
}

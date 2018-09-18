package com.archervanderwaal.litespring.test.v6;

import com.archervanderwaal.litespring.context.ApplicationContext;
import com.archervanderwaal.litespring.context.support.ClassPathXmlApplicationContext;
import com.archervanderwaal.litespring.test.v5.util.MessageTracker;
import com.archervanderwaal.litespring.test.v6.entity.IPetStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author archer archer.vanderwaal@gmail.com
 */
public class ApplicationContextTestV6 {

    @Before
    public void setUp(){
        MessageTracker.clearMessages();
    }

    @Test
    public void testGetBeanProperty() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v6.xml");
        IPetStoreService petStore = (IPetStoreService) ctx.getBean("petStore");

        petStore.placeOrder();
        List<String> msgs = MessageTracker.getMessages();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));

    }
}

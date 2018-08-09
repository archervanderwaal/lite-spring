package me.stormma.litespring.test.v5;

import me.stormma.litespring.test.v5.entity.PetStoreService;
import me.stormma.litespring.test.v5.tx.TransactionManager;
import me.stormma.litespring.test.v5.util.MessageTracker;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class CGLibTestV5 {

    @Before
    public void setUp() {
        MessageTracker.clearMessages();
    }

    @Test
    public void testCallback() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService.class);
        enhancer.setCallback(new TransactionInterceptor());
        PetStoreService petStoreService = (PetStoreService) enhancer.create();
        petStoreService.placeOrder();
        Assert.assertEquals("start tx", MessageTracker.getMessages().get(0));
        Assert.assertEquals("place order", MessageTracker.getMessages().get(1));
        Assert.assertEquals("commit tx", MessageTracker.getMessages().get(2));
    }

    static class TransactionInterceptor implements MethodInterceptor {

        TransactionManager tx = new TransactionManager();

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            tx.start();
            Object res = methodProxy.invokeSuper(o, objects);
            tx.commit();
            return res;
        }
    }
}

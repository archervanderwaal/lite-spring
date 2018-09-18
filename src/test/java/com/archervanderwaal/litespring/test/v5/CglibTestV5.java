package com.archervanderwaal.litespring.test.v5;

import com.archervanderwaal.litespring.test.v5.entity.PetStoreService;
import com.archervanderwaal.litespring.test.v5.service.TransactionManager;
import com.archervanderwaal.litespring.test.v5.util.MessageTracker;
import net.sf.cglib.proxy.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class CglibTestV5 {

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

    @Test
    public void testFilter() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService.class);
        Callback[] callbacks = new Callback[]{new TransactionInterceptor(), NoOp.INSTANCE};
        Class<?>[] types = new Class[callbacks.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = callbacks[i].getClass();
        }
        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackTypes(types);
        PetStoreService petStoreService = (PetStoreService) enhancer.create();
        petStoreService.placeOrder();
        System.out.println(petStoreService.toString());
    }

    class TransactionInterceptor implements MethodInterceptor {

        TransactionManager tx = new TransactionManager();

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            tx.start();
            Object res = methodProxy.invokeSuper(o, objects);
            tx.commit();
            return res;
        }
    }

    class ProxyCallbackFilter implements CallbackFilter {

        /**
         * @param method
         * @return the index into the array of callbacks (as specified by {@link Enhancer#setCallbacks})
         * to use for the method,
         */
        @Override
        public int accept(Method method) {
            if (method.getName().startsWith("place")) {
                return 0;
            }
            return 1;
        }
    }
}

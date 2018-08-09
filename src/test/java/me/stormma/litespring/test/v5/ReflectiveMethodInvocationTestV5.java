package me.stormma.litespring.test.v5;

import me.stormma.litespring.aop.aspectj.AspectJAfterReturningAdvice;
import me.stormma.litespring.aop.aspectj.AspectJAfterThrowingAdvice;
import me.stormma.litespring.aop.aspectj.AspectJBeforeAdvice;
import me.stormma.litespring.aop.framework.ReflectiveMethodInvocation;
import me.stormma.litespring.test.v5.entity.PetStoreService;
import me.stormma.litespring.test.v5.tx.TransactionManager;
import me.stormma.litespring.test.v5.util.MessageTracker;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ReflectiveMethodInvocationTestV5 {

    private AspectJBeforeAdvice beforeAdvice = null;

    private AspectJAfterReturningAdvice afterReturningAdvice = null;

    private AspectJAfterThrowingAdvice afterThrowingAdvice = null;

    private PetStoreService petStoreService = null;

    private TransactionManager tx = null;

    private Method targetMethod = null;

    private Method targetMethodWithException = null;

    @Before
    public void setUp() throws NoSuchMethodException {
        petStoreService = new PetStoreService();
        tx = new TransactionManager();
        MessageTracker.clearMessages();
        beforeAdvice = new AspectJBeforeAdvice(
                TransactionManager.class.getMethod("start"),
                null,
                tx
        );
        afterReturningAdvice = new AspectJAfterReturningAdvice(
                TransactionManager.class.getMethod("commit"),
                null,
                tx
        );
        afterThrowingAdvice = new AspectJAfterThrowingAdvice(
                TransactionManager.class.getMethod("rollback"),
                null,
                tx
        );
        targetMethod = PetStoreService.class.getMethod("placeOrder");
        targetMethodWithException = PetStoreService.class.getMethod("placeOrderWithException");
    }

    @Test
    public void testMethodInvocation() throws Throwable {
        List<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(beforeAdvice);
        interceptors.add(afterReturningAdvice);
        commonMethodWithoutException(interceptors);
    }

    @Test
    public void testMethodInvocationWithoutSorted() throws Throwable {
        Method targetMethod = PetStoreService.class.getMethod("placeOrder");
        List<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(afterReturningAdvice);
        interceptors.add(beforeAdvice);
        commonMethodWithoutException(interceptors);
    }

    @Test
    public void testMethodInvocationWithException() throws Throwable {
        List<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(beforeAdvice);
        interceptors.add(afterThrowingAdvice);
        commonMethodWithException(interceptors);
    }

    private void commonMethodWithoutException(List<MethodInterceptor> interceptors) throws Throwable {
        ReflectiveMethodInvocation methodInvocation = new ReflectiveMethodInvocation(petStoreService, targetMethod
                , new Object[0], interceptors);
        methodInvocation.proceed();
        List<String> messages = MessageTracker.getMessages();
        Assert.assertEquals("start tx", messages.get(0));
        Assert.assertEquals("place order", messages.get(1));
        Assert.assertEquals("commit tx", messages.get(2));
    }

    private void commonMethodWithException(List<MethodInterceptor> interceptors) throws Throwable {
        ReflectiveMethodInvocation methodInvocation = new ReflectiveMethodInvocation(petStoreService, this.targetMethodWithException
                , new Object[0], interceptors);
        try {
            methodInvocation.proceed();
        } catch (Throwable throwable) {
            List<String> messages = MessageTracker.getMessages();
            Assert.assertEquals("start tx", messages.get(0));
            Assert.assertEquals("place order with exception", messages.get(1));
            Assert.assertEquals("rollback tx", messages.get(2));
            return;
        }
        Assert.fail("expected exception thrown");
    }
}

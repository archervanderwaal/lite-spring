package com.archervanderwaal.litespring.test.v5;

import com.archervanderwaal.litespring.aop.aspectj.AspectJAfterReturningAdvice;
import com.archervanderwaal.litespring.aop.aspectj.AspectJBeforeAdvice;
import com.archervanderwaal.litespring.aop.framework.AopConfigSupport;
import com.archervanderwaal.litespring.aop.framework.CglibAopProxyFactory;
import com.archervanderwaal.litespring.aop.aspectj.AspectJExpressionPointcut;
import com.archervanderwaal.litespring.aop.framework.AopConfig;
import com.archervanderwaal.litespring.test.v5.entity.PetStoreService;
import com.archervanderwaal.litespring.test.v5.tx.TransactionManager;
import com.archervanderwaal.litespring.test.v5.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class CglibAopProxyTestV5 {
    private AspectJBeforeAdvice beforeAdvice;

    private AspectJAfterReturningAdvice afterReturningAdvice;

    private PetStoreService petStoreService;

    private TransactionManager transactionManager;

    private AspectJExpressionPointcut expressionPointcut;

    @Before
    public void setUp() throws NoSuchMethodException {
        petStoreService = new PetStoreService();
        transactionManager = new TransactionManager();
        String expression = "execution(* me.stormma.litespring.test.v5.entity.*.placeOrder(..))";
        expressionPointcut = new AspectJExpressionPointcut();
        expressionPointcut.setExpression(expression);

        beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start")
                , expressionPointcut, transactionManager);

        afterReturningAdvice = new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit")
                , expressionPointcut, transactionManager);
    }

    @Test
    public void testGetProxy() {
        AopConfig aopConfig = new AopConfigSupport();
        aopConfig.addAdvice(beforeAdvice);
        aopConfig.addAdvice(afterReturningAdvice);
        aopConfig.setTargetObject(petStoreService);
        CglibAopProxyFactory proxyFactory = new CglibAopProxyFactory(aopConfig);
        PetStoreService proxy = (PetStoreService) proxyFactory.getProxy();
        proxy.placeOrder();

        // assert
        List<String> messages = MessageTracker.getMessages();
        Assert.assertEquals("start tx", messages.get(0));
        Assert.assertEquals("place order", messages.get(1));
        Assert.assertEquals("commit tx", messages.get(2));
    }
}

package com.archervanderwaal.litespring.test.v5;

import com.archervanderwaal.litespring.aop.Advice;
import com.archervanderwaal.litespring.aop.aspectj.AspectJAfterReturningAdvice;
import com.archervanderwaal.litespring.aop.aspectj.AspectJAfterThrowingAdvice;
import com.archervanderwaal.litespring.aop.aspectj.AspectJBeforeAdvice;
import com.archervanderwaal.litespring.beans.factory.BeanFactory;
import com.archervanderwaal.litespring.test.v5.tx.TransactionManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author archer archer.vanderwaal@gmail.com
 */
public class BeanFactoryTestV5 extends AbstractTestV5 {

    private static final String expression = "execution(* com.archervanderwaal.litespring.test.v5.entity.*.placeOrder(..))";

    @Test
    public void testGetBeanByType() throws NoSuchMethodException {
        BeanFactory factory = super.getBeanFactory("petstore-v5.xml");
        List<Object> advices = factory.getBeansByType(Advice.class);
        Assert.assertEquals(3, advices.size());

        {
            AspectJBeforeAdvice beforeAdvice = (AspectJBeforeAdvice) this.getAdvice(AspectJBeforeAdvice.class, advices);
            Assert.assertEquals(TransactionManager.class.getMethod("start"), beforeAdvice.getAdviceMethod());
            Assert.assertEquals(expression, beforeAdvice.getPointcut().getExpression());
            Assert.assertEquals(TransactionManager.class, beforeAdvice.getAdviceInstance().getClass());
        }

        {
            AspectJAfterReturningAdvice advice = (AspectJAfterReturningAdvice) this.getAdvice(AspectJAfterReturningAdvice.class, advices);
            Assert.assertEquals(TransactionManager.class.getMethod("commit"), advice.getAdviceMethod());
            Assert.assertEquals(expression, advice.getPointcut().getExpression());
            Assert.assertEquals(TransactionManager.class, advice.getAdviceInstance().getClass());
        }

        {
            AspectJAfterThrowingAdvice advice = (AspectJAfterThrowingAdvice) this.getAdvice(AspectJAfterThrowingAdvice.class, advices);
            Assert.assertEquals(TransactionManager.class.getMethod("rollback"), advice.getAdviceMethod());
            Assert.assertEquals(expression, advice.getPointcut().getExpression());
            Assert.assertEquals(TransactionManager.class, advice.getAdviceInstance().getClass());

        }
    }

    private Object getAdvice(Class<?> type, List<Object> objects) {
        for (Object object : objects) {
            if (object.getClass().equals(type)) {
                return object;
            }
        }
        return null;
    }
}

package com.archervanderwaal.litespring.test.v5;

import com.archervanderwaal.litespring.aop.MethodMatcher;
import com.archervanderwaal.litespring.aop.aspectj.AspectJExpressionPointcut;
import com.archervanderwaal.litespring.test.v5.entity.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class PointcutTestV5 {

    @Test
    public void testPointCut() throws NoSuchMethodException {
        String expression = "execution(* com.archervanderwaal.litespring.test.v5.entity.*.placeOrder(..))";
        AspectJExpressionPointcut apc = new AspectJExpressionPointcut();
        apc.setExpression(expression);
        MethodMatcher methodMatcher = apc.getMethodMatcher();
        Class<?> targetClass = PetStoreService.class;
        Method method1 = targetClass.getMethod("placeOrder");
        Assert.assertTrue(methodMatcher.matches(method1));

        Method method2 = targetClass.getMethod("getAccountDao");
        Assert.assertFalse(methodMatcher.matches(method2));
    }
}

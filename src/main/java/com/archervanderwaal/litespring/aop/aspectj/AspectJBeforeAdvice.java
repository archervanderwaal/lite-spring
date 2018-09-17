package com.archervanderwaal.litespring.aop.aspectj;

import com.archervanderwaal.litespring.aop.Pointcut;
import com.archervanderwaal.litespring.aop.config.AspectInstanceFactory;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice {

    public AspectJBeforeAdvice(Method adviceMethod, Pointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
        super(adviceMethod, pointcut, adviceObjectFactory);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // invoke adviceObject's method adviceMethod
        this.invokeAdviceMethod();
        return invocation.proceed();
    }
}

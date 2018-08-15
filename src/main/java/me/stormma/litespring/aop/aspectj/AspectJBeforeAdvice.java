package me.stormma.litespring.aop.aspectj;

import me.stormma.litespring.aop.Pointcut;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice {

    public AspectJBeforeAdvice(Method adviceMethod, Pointcut pointcut, Object adviceObject) {
        super(adviceMethod, pointcut, adviceObject);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // invoke adviceObject's method adviceMethod
        this.invokeAdviceMethod();
        return invocation.proceed();
    }
}

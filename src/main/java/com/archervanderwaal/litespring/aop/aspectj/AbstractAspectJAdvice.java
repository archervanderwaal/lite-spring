package com.archervanderwaal.litespring.aop.aspectj;

import com.archervanderwaal.litespring.aop.Advice;
import com.archervanderwaal.litespring.aop.Pointcut;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class AbstractAspectJAdvice implements Advice {

    private Method adviceMethod;

    private Pointcut pointcut;

    private Object adviceObject;

    public AbstractAspectJAdvice(Method adviceMethod, Pointcut pointcut, Object adviceObject) {
        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.adviceObject = adviceObject;
    }

    public void invokeAdviceMethod() throws Throwable {
        this.adviceMethod.invoke(this.adviceObject);
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return null;
    }
}

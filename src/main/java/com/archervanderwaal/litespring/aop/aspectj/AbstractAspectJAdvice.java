package com.archervanderwaal.litespring.aop.aspectj;

import com.archervanderwaal.litespring.aop.Advice;
import com.archervanderwaal.litespring.aop.Pointcut;
import com.archervanderwaal.litespring.aop.config.AspectInstanceFactory;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class AbstractAspectJAdvice implements Advice {

    private Method adviceMethod;

    private Pointcut pointcut;

    private AspectInstanceFactory adviceObjectFactory;

    public AbstractAspectJAdvice(Method adviceMethod,
                                 Pointcut pointcut,
                                 AspectInstanceFactory adviceObjectFactory) {
        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.adviceObjectFactory = adviceObjectFactory;
    }

    public void invokeAdviceMethod() throws Throwable {
        this.adviceMethod.invoke(this.adviceObjectFactory.getAspectInstance());
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return null;
    }

    public Method getAdviceMethod() {
        return this.adviceMethod;
    }

    public Object getAdviceInstance() {
        return adviceObjectFactory.getAspectInstance();
    }
}

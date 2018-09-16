package com.archervanderwaal.litespring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object targetObject;

    protected final Method targetMethod;

    protected Object[] args;

    protected final List<MethodInterceptor> interceptors;

    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object targetObject, Method targetMethod
            , Object[] args, List<MethodInterceptor> interceptors) {
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.args = args;
        this.interceptors = interceptors;
    }

    @Override
    public Method getMethod() {
        return this.targetMethod;
    }

    @Override
    public Object[] getArguments() {
        return this.args != null ? this.args : new Object[0];
    }

    @Override
    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == this.interceptors.size() - 1) {
            return invokeJoinPoint();
        }
        this.currentInterceptorIndex++;
        MethodInterceptor methodInterceptor = interceptors.get(this.currentInterceptorIndex);
        return methodInterceptor.invoke(this);
    }


    @Override
    public Object getThis() {
        return this.targetObject;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return this.targetMethod;
    }

    private Object invokeJoinPoint() throws Throwable {
        return this.targetMethod.invoke(this.targetObject, this.args);
    }
}

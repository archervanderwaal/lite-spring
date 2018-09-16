package com.archervanderwaal.litespring.aop.framework;

import com.archervanderwaal.litespring.aop.Advice;
import com.archervanderwaal.litespring.aop.Pointcut;
import com.archervanderwaal.litespring.utils.Assert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class AopConfigSupport implements AopConfig {

    private List<Advice> advices;

    private boolean proxyTargetClass = false;

    private Object targetObject;

    private List<Class<?>> interfaces;

    public AopConfigSupport() {
        advices = new ArrayList<>();
        interfaces = new ArrayList<>();
    }

    @Override
    public void addInterface(Class<?> intf) {
        Assert.assertNotNull(intf, "Interface must be not null");
        if (!intf.isInterface()) {
            throw new IllegalArgumentException("[" + intf.getName() + "] is not an interface");
        }
        if (!this.interfaces.contains(intf)) {
            this.interfaces.add(intf);
        }
    }

    @Override
    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    @Override
    public void setTargetObject(Object obj) {
        this.targetObject = obj;
    }

    @Override
    public Class<?> getTargetClass() {
        Assert.assertNotNull(this.targetObject, "targetObject must be not null");
        return this.targetObject.getClass();
    }

    @Override
    public Object getTargetObject() {
        return this.targetObject;
    }

    @Override
    public List<Advice> getAdvices() {
        return this.advices;
    }

    @Override
    public void addAdvice(Advice advice) {
        Assert.assertNotNull(advice, "advice must be not null");
        this.advices.add(advice);
    }

    @Override
    public List<Advice> getAdvices(Method method) {
        List<Advice> result = new ArrayList<>();
        for(Advice advice : this.getAdvices()){
            Pointcut pointcut = advice.getPointcut();
            if(pointcut.getMethodMatcher().matches(method)){
                result.add(advice);
            }
        }
        return result;
    }

    @Override
    public boolean isProxyTargetClass() {
        return this.proxyTargetClass;
    }

    @Override
    public Class<?>[] getProxiedInterfaces() {
        return this.interfaces.toArray(new Class[this.interfaces.size()]);
    }

    @Override
    public boolean isInterfaceProxied(Class<?> intf) {
        for (Class proxyInterface : this.interfaces) {
            if (intf.isAssignableFrom(proxyInterface)) {
                return true;
            }
        }
        return false;
    }
}

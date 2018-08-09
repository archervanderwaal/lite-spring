package me.stormma.litespring.aop.framework;

import me.stormma.litespring.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface AopConfig {

    //===================================//
    //           cglib proxy             //
    //===================================//
    Class<?> getTargetClass();

    void setProxyTargetClass(boolean proxyTargetClass);

    Object getTargetObject();

    List<Advice> getAdvices();

    void addAdvice(Advice advice);

    List<Advice> getAdvices(Method method);

    void setTargetObject(Object obj);

    //===================================//
    //            jdk proxy              //
    //===================================//
    boolean isProxyTargetClass();

    Class<?>[] getProxiedInterfaces();

    boolean isInterfaceProxied(Class<?> intf);

    void addInterface(Class<?> intf);
}

package me.stormma.litespring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface Advice extends MethodInterceptor {

    Pointcut getPointcut();
}

package me.stormma.litespring.aop.framework;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface AopProxyFactory {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}

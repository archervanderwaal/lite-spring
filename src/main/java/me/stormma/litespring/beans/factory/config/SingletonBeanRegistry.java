package me.stormma.litespring.beans.factory.config;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface SingletonBeanRegistry {

    void registerSingletonBean(String beanId, Object singletonBean);

    Object getSingletonBean(String beanId);
}

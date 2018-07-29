package me.stormma.litespring.beans.factory.config;

import me.stormma.litespring.beans.BeansException;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    void postProcessPropertyValues(Object bean, String beanName) throws BeansException;
}

package me.stormma.litespring.beans.factory.config;

import me.stormma.litespring.beans.BeansException;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public interface InstantiationAwareBeanPosrProcessor extends BeanPostProcessor {

    Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    boolean afterInstantiation(Object bean, String beanName) throws BeansException;

    void postProcessPropertyValues(Object bean, String beanName) throws BeansException;
}

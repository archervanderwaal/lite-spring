package me.stormma.litespring.beans.factory.config;

import me.stormma.litespring.beans.BeansException;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}

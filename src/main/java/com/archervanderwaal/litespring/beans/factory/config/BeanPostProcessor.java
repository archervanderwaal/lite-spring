package com.archervanderwaal.litespring.beans.factory.config;

import com.archervanderwaal.litespring.beans.BeansException;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}

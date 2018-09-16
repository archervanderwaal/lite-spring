package com.archervanderwaal.litespring.beans.factory.support;

import com.archervanderwaal.litespring.beans.BeanDefinition;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface BeanDefinitionRegistry {

    /**
     * @param beanId
     * @return
     */
    BeanDefinition getBeanDefinition(String beanId);

    /**
     * @param beanId
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanId, BeanDefinition beanDefinition);
}

package me.stormma.litespring.beans.factory.support;

import me.stormma.litespring.beans.BeanDefinition;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/18
 */
public interface BeanNameGenerator {

    String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry);
}

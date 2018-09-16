package com.archervanderwaal.litespring.beans.factory;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface BeanFactory {

    Object getBean(String petStore);

    Class<?> getType(String beanName) throws NoSuchBeanDefinitionException;
}

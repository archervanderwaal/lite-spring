package com.archervanderwaal.litespring.beans.factory;

import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface BeanFactory {

    Object getBean(String petStore);

    Class<?> getType(String beanName) throws NoSuchBeanDefinitionException;

    List<Object> getBeansByType(Class<?> type);
}

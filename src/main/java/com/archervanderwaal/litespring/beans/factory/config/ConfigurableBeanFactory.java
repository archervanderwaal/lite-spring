package com.archervanderwaal.litespring.beans.factory.config;

import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {

    void setBeanClassLoader(ClassLoader classLoader);

    ClassLoader getClassLoader();

    void addBeanPostProcess(BeanPostProcessor beanPostProcessor);

    List<BeanPostProcessor> getBeanPostProcessors();
}

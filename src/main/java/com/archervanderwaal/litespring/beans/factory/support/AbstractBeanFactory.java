package com.archervanderwaal.litespring.beans.factory.support;

import com.archervanderwaal.litespring.beans.BeanDefinition;
import com.archervanderwaal.litespring.beans.factory.BeanCreationException;
import com.archervanderwaal.litespring.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author archer archer.vanderwaal@gmail.com
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    protected abstract Object createBean(BeanDefinition beanDefinition) throws BeanCreationException;
}

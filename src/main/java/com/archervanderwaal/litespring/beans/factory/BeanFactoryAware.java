package com.archervanderwaal.litespring.beans.factory;

import com.archervanderwaal.litespring.beans.factory.BeanFactory;

/**
 * @author archer archer.vanderwaal@gmail.com
 */
public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory);
}

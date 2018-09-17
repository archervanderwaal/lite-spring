package com.archervanderwaal.litespring.aop.config;

import com.archervanderwaal.litespring.beans.factory.BeanFactory;
import com.archervanderwaal.litespring.utils.StringUtils;
import com.archervanderwaal.litespring.beans.factory.BeanFactoryAware;

/**
 * @author archer archer.vanderwaal@gmail.com
 */
public class AspectInstanceFactory implements BeanFactoryAware {

    private String aspectBeanName;

    private BeanFactory beanFactory;

    public void setAspectBeanName(String aspectBeanName) {
        this.aspectBeanName = aspectBeanName;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        if (!StringUtils.hasText(this.aspectBeanName)) {
            throw new IllegalArgumentException("'aspectBeanName' is required");
        }
    }

    public Object getAspectInstance() {
        return this.beanFactory.getBean(this.aspectBeanName);
    }
}

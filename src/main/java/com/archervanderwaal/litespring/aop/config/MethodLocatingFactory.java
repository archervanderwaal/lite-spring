package com.archervanderwaal.litespring.aop.config;

import com.archervanderwaal.litespring.beans.BeanUtils;
import com.archervanderwaal.litespring.beans.factory.BeanFactory;
import com.archervanderwaal.litespring.utils.StringUtils;

import java.lang.reflect.Method;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class MethodLocatingFactory {

    private String targetBeanName;

    private String methodName;

    private Method method;

    public void setTargetBeanName(String beanName) {
        this.targetBeanName = beanName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        if (!StringUtils.hasText(this.targetBeanName)) {
            throw new IllegalArgumentException("Property 'targetBeanName' is required");
        }
        if (!StringUtils.hasText(this.methodName)) {
            throw new IllegalArgumentException("Property 'methodName' is required");
        }

        Class<?> beanClass = beanFactory.getType(this.targetBeanName);
        // assert beanClass must be not null
        this.method = BeanUtils.resolveSignature(methodName, beanClass);
        if (this.method == null) {
            throw new IllegalArgumentException("Unable to locate method [" + this.methodName
                    + "] on bean [" + this.targetBeanName + "]");
        }
    }

    public Object getObject() {
        return this.method;
    }
}

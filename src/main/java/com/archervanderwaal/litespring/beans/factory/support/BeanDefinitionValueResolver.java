package com.archervanderwaal.litespring.beans.factory.support;

import com.archervanderwaal.litespring.beans.factory.BeanFactory;
import com.archervanderwaal.litespring.beans.factory.config.RuntimeBeanReference;
import com.archervanderwaal.litespring.beans.factory.config.TypedStringValue;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class BeanDefinitionValueResolver {

    private final BeanFactory factory;

    public BeanDefinitionValueResolver(final BeanFactory factory) {
        this.factory = factory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) value;
            String beanId = runtimeBeanReference.getBeanId();
            return factory.getBean(beanId);
        }
        if (value instanceof TypedStringValue) {
            TypedStringValue typedStringValue = (TypedStringValue) value;
            return typedStringValue.getValue();
        }
        // TODO
        throw new RuntimeException("the value " + value + "has not implemented.");
    }
}

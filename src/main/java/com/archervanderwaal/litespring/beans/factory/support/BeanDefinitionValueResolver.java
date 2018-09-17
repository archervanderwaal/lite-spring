package com.archervanderwaal.litespring.beans.factory.support;

import com.archervanderwaal.litespring.beans.BeanDefinition;
import com.archervanderwaal.litespring.beans.BeansException;
import com.archervanderwaal.litespring.beans.factory.BeanCreationException;
import com.archervanderwaal.litespring.beans.factory.FactoryBean;
import com.archervanderwaal.litespring.beans.factory.config.RuntimeBeanReference;
import com.archervanderwaal.litespring.beans.factory.config.TypedStringValue;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class BeanDefinitionValueResolver {

    private final AbstractBeanFactory factory;

    public BeanDefinitionValueResolver(final AbstractBeanFactory factory) {
        this.factory = factory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) value;
            String beanId = runtimeBeanReference.getBeanId();
            return factory.getBean(beanId);
        } else if (value instanceof TypedStringValue) {
            TypedStringValue typedStringValue = (TypedStringValue) value;
            return typedStringValue.getValue();
        } else if (value instanceof BeanDefinition) {
            BeanDefinition beanDefinition = (BeanDefinition) value;
            String innerBeanId = "(inner bean)" + beanDefinition.getBeanClassName() + "#" +
                    Integer.toHexString(System.identityHashCode(beanDefinition));
            return resolveInnerBean(innerBeanId, beanDefinition);
        } else {
            return value;
        }
        // TODO
        // throw new RuntimeException("the value " + value + " has not implemented.");
    }

    private Object resolveInnerBean(String innerBeanId, BeanDefinition beanDefinition) {
        try {
            Object innerBean = this.factory.createBean(beanDefinition);
            if (innerBean instanceof FactoryBean) {
                try {
                    return ((FactoryBean<?>) innerBean).getObject();
                } catch (Exception e) {
                    throw new BeanCreationException(innerBeanId, "FactoryBean threw exception on object creation", e);
                }
            } else {
                return innerBean;
            }
        } catch (BeansException ex) {
            throw new BeanCreationException(
                    innerBeanId,
                    "Cannot create inner bean '" + innerBeanId + "' " +
                            (beanDefinition != null
                                    && beanDefinition.getBeanClassName() != null
                                    ? "of type [" + beanDefinition.getBeanClassName() + "] " : "")
                    , ex);
        }
    }
}

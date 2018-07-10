package me.stormma.litespring.beans.factory.support;

import me.stormma.litespring.beans.factory.config.RuntimeBeanReference;
import me.stormma.litespring.beans.factory.config.TypedStringValue;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class BeanDefinitionValueResolver {

    private final DefaultBeanFactory factory;

    public BeanDefinitionValueResolver(final DefaultBeanFactory factory) {
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

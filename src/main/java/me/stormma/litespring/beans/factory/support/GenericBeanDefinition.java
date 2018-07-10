package me.stormma.litespring.beans.factory.support;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.BeanScope;
import me.stormma.litespring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class GenericBeanDefinition implements BeanDefinition {

    private String beanId;

    private String beanClassName;

    private boolean isSingleton = true;

    private boolean isPrototype = false;

    private BeanScope scope = BeanScope.DEFAULT;

    private List<PropertyValue> propertyValues;

    public GenericBeanDefinition(String beanId, String beanClassName) {
        this.beanId = beanId;
        this.beanClassName = beanClassName;
        this.propertyValues = new ArrayList<>();
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public boolean isSingleton() {
        return this.isSingleton;
    }

    @Override
    public boolean isPrototype() {
        return this.isPrototype;
    }

    @Override
    public void setScope(BeanScope scope) {
        this.scope = scope;
        isSingleton = this.scope.equals(BeanScope.SINGLETON) || this.scope.equals(BeanScope.DEFAULT);
        isPrototype = this.scope.equals(BeanScope.PROTOTYPE);
    }

    @Override
    public BeanScope getScope() {
        return this.scope;
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }
}

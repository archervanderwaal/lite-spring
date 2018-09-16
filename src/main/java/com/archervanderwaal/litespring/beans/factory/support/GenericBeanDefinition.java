package com.archervanderwaal.litespring.beans.factory.support;

import com.archervanderwaal.litespring.beans.BeanDefinition;
import com.archervanderwaal.litespring.beans.BeanScope;
import com.archervanderwaal.litespring.beans.ConstructorArgument;
import com.archervanderwaal.litespring.beans.PropertyValue;

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

    private List<PropertyValue> propertyValues = new ArrayList<>();

    private ConstructorArgument constructorArgument = new ConstructorArgument();

    private Class<?> beanClass;

    public GenericBeanDefinition() {

    }

    public GenericBeanDefinition(String beanId, String beanClassName) {
        this.beanId = beanId;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanId() {
        return beanId;
    }

    @Override
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    @Override
    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    @Override
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

    @Override
    public ConstructorArgument getConstructorArgument() {
        return this.constructorArgument;
    }

    @Override
    public Class<?> getBeanClass() {
        if(this.beanClass == null){
            throw new IllegalStateException(
                    "Bean class name [" + this.getBeanClassName() + "] has not been resolved into an actual Class");
        }
        return this.beanClass;
    }

    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        String className = this.getBeanClassName();
        if (className == null) {
            return null;
        }
        Class<?> resolvedClass = classLoader.loadClass(className);
        this.beanClass = resolvedClass;
        return resolvedClass;
    }

    @Override
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public boolean hasConstructorArgumentValues() {
        return !this.constructorArgument.isEmpty();
    }

    @Override
    public boolean hasBeanClass() {
        return this.beanClass != null;
    }
}

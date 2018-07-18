package me.stormma.litespring.beans.factory.support;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.BeanScope;
import me.stormma.litespring.beans.ConstructorArgument;
import me.stormma.litespring.beans.PropertyValue;
import me.stormma.litespring.beans.factory.BeanCreationException;
import me.stormma.litespring.beans.factory.config.ConfigurableBeanFactory;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private SoftReference<Class<?>> beanClass;

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
        return java.util.Objects.isNull(this.beanClass) ? null : this.beanClass.get();
    }

    public Class<?> resolveBeanClass(ConfigurableBeanFactory beanFactory) {
        Class<?> _beanClass = null;
        if (Objects.isNull(getBeanClass())) {
            // reload
            try {
                setBeanClass(_beanClass = beanFactory.getClassLoader().loadClass(this.beanClassName));
            } catch (ClassNotFoundException e) {
                throw new BeanCreationException("create bean for " + beanClassName + " failed.");
            }
        }
        return _beanClass;
    }

    @Override
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = new SoftReference<>(beanClass);
    }

    @Override
    public boolean hasConstructorArgumentValues() {
        return !this.constructorArgument.isEmpty();
    }
}

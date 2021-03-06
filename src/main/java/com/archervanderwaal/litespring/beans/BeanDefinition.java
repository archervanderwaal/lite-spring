package com.archervanderwaal.litespring.beans;

import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface BeanDefinition {

    /**
     * get bean id
     * @return
     */
    String getBeanId();

    /**
     * set bean id
     * @param beanId
     */
    void setBeanId(String beanId);

    /**
     * get bean's class name
     * @return
     */
    String getBeanClassName();

    /**
     * set bean class name
     */
    void setBeanClassName(String beanClassName);

    /**
     * judge bean is singleton bean?
     * @return
     */
    boolean isSingleton();

    /**
     * judge bean is prototype bean
     * @return
     */
    boolean isPrototype();

    /**
     * set bean's scope attribute
     * @param scope
     */
    void setScope(BeanScope scope);

    /**
     * get bean's scope attribute
     * @return
     */
    BeanScope getScope();

    /**
     * get bean's property values attribute
     * @return
     */
    List<PropertyValue> getPropertyValues();

    /**
     * get constructor argument
     * @return
     */
    ConstructorArgument getConstructorArgument();

    /**
     * get bean class
     * @return
     */
    Class<?> getBeanClass();

    /**
     * set bean class
     */
    void setBeanClass(Class<?> beanClass);

    /**
     * has constructor argument values
     * @return
     */
    boolean hasConstructorArgumentValues();

    /**
     * @param classLoader
     * @return
     */
    void resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException;

    /**
     * @return
     */
    boolean hasBeanClass();

    /**
     * is synthetic bean
     * @return
     */
    boolean isSynthetic();
}

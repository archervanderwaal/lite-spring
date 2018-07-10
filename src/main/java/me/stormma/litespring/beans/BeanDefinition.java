package me.stormma.litespring.beans;

import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface BeanDefinition {

    /**
     * get bean's class name
     * @return
     */
    String getBeanClassName();

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
}

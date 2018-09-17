package com.archervanderwaal.litespring.beans.factory;

/**
 * @author archer archer.vanderwaal@gmail.com
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();
}

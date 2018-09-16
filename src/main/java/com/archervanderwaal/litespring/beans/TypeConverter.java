package com.archervanderwaal.litespring.beans;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface TypeConverter {

    <T> T convertIfNecessary(Object value, Class<T> targetClass) throws TypeMismatchException;
}

package com.archervanderwaal.litespring.beans.factory;

import com.archervanderwaal.litespring.beans.BeansException;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class BeanDefinitionStoreException extends BeansException {

    public BeanDefinitionStoreException(String message) {
        super(message);
    }

    public BeanDefinitionStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}

package me.stormma.litespring.beans.factory;

import me.stormma.litespring.beans.BeansException;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class BeanCreationException extends BeansException {

    /**
     * beanId attribute
     */
    private String beanId;

    public BeanCreationException(String message) {
        super(message);
    }

    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanCreationException(String beanId, String message) {
        super("Error creating bean with id '" + beanId + "': " + message);
        this.beanId = beanId;
    }

    public BeanCreationException(String beanId, String message, Throwable cause) {
        this(beanId, message);
        initCause(cause);
    }

    public String getBeanId() {
        return this.beanId;
    }
}

package me.stormma.litespring.beans.factory.config;

import org.jetbrains.annotations.NotNull;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class RuntimeBeanReference {

    private final String beanId;

    public RuntimeBeanReference(@NotNull final String beanId) {
        this.beanId = beanId;
    }

    public String getBeanId() {
        return this.beanId;
    }
}

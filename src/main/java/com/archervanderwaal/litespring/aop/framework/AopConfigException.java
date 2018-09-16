package com.archervanderwaal.litespring.aop.framework;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class AopConfigException extends RuntimeException {

    public AopConfigException(String message) {
        super(message);
    }

    public AopConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
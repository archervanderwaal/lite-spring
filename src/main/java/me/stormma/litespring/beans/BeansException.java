package me.stormma.litespring.beans;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class BeansException extends RuntimeException {

    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
/**/
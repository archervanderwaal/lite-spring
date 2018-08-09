package me.stormma.litespring.aop;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class AopInvocationException extends RuntimeException {

    public AopInvocationException(String message) {
        super(message);
    }

    public AopInvocationException(String message, Throwable cause) {
        super(message, cause);
    }
}

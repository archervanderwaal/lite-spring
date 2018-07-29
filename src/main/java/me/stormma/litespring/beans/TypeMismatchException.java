package me.stormma.litespring.beans;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class TypeMismatchException extends BeansException {

    private transient Object value;

    private Class<?> targetClass;

    public TypeMismatchException(Object value, Class<?> targetClass) {
        super("Failed convert value " + value + "to type " + targetClass);
        this.value = value;
        this.targetClass = targetClass;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }
}

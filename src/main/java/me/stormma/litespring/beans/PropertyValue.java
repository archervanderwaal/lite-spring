package me.stormma.litespring.beans;

import org.jetbrains.annotations.Nullable;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class PropertyValue {

    private final String name;

    private final Object value;

    private boolean converted = false;

    private Object convertedValue;

    public PropertyValue(final String name, @Nullable final Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public synchronized boolean isConverted() {
        return converted;
    }

    public Object getConvertedValue() {
        return convertedValue;
    }

    public void setConvertedValue(final Object convertedValue) {
        this.converted = true;
        this.convertedValue = convertedValue;
    }
}

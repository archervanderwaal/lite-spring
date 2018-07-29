package me.stormma.litespring.beans.factory.config;

import org.jetbrains.annotations.NotNull;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class TypedStringValue {

    private final String value;

    public TypedStringValue(@NotNull final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

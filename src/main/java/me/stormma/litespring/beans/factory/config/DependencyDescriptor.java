package me.stormma.litespring.beans.factory.config;

import me.stormma.litespring.utils.Assert;

import java.lang.reflect.Field;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public class DependencyDescriptor {

    private Field field;

    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.assertNotNull(field, "Field must be not null");
        this.field = field;
        this.required = required;
    }

    public Class<?> getDependencyType() {
        if (this.field != null) {
            return this.field.getType();
        }
        // TODO others injection
        throw new RuntimeException("only support field injection");
    }

    public boolean isRequired() {
        return this.required;
    }
}

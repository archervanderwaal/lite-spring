package me.stormma.litespring.beans.factory.annotation;

import me.stormma.litespring.beans.factory.BeanCreationException;
import me.stormma.litespring.beans.factory.config.AutowireCapableBeanFactory;
import me.stormma.litespring.beans.factory.config.DependencyDescriptor;
import me.stormma.litespring.utils.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public class AutowiredFieldElement extends InjectionElement {

    private boolean required;

    public AutowiredFieldElement(Field field, AutowireCapableBeanFactory factory, boolean required) {
        super(field, factory);
        this.required = required;
    }

    public Field getField() {
        return (Field) this.member;
    }

    @Override
    void inject(Object target) {
        Field field = this.getField();
        try {
            DependencyDescriptor descriptor = new DependencyDescriptor(field, this.required);
            Object res = this.factory.resolveDependency(descriptor);
            if (res != null) {
                ReflectionUtils.makeAccessible(field);
                field.set(target, res);
            }
        } catch (IllegalAccessException e) {
            throw new BeanCreationException("Could not autowire field :" + field, e);
         }
    }
}

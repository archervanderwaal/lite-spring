package me.stormma.litespring.beans.factory.annotation;

import me.stormma.litespring.utils.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public class InjectionMetadata {

    private final Class<?> targetClass;

    private final List<InjectionElement> elements;

    public InjectionMetadata(final Class<?> targetClass, final List<InjectionElement> elements) {
        this.targetClass = targetClass;
        this.elements = elements;
    }

    public List<InjectionElement> getInjectionElements() {
        return this.elements;
    }

    public void inject(Object target) {
        if (CollectionUtils.isNullOrEmpty(elements) || Objects.isNull(target)) {
            return;
        }
        for (InjectionElement element : elements) {
            element.inject(target);
        }
    }
}

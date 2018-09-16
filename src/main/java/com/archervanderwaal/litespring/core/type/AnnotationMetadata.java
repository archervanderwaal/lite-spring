package com.archervanderwaal.litespring.core.type;

import com.archervanderwaal.litespring.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/18
 */
public interface AnnotationMetadata extends ClassMetadata {

    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationType);

    AnnotationAttributes getAnnotationAttributes(String annotationType);
}

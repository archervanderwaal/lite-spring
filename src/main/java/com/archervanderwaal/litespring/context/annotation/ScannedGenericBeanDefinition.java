package com.archervanderwaal.litespring.context.annotation;

import com.archervanderwaal.litespring.beans.factory.support.GenericBeanDefinition;
import com.archervanderwaal.litespring.beans.factory.annotation.AnnotatedBeanDefinition;
import com.archervanderwaal.litespring.core.type.AnnotationMetadata;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/18
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetadata annotationMetadata;

    public ScannedGenericBeanDefinition(final AnnotationMetadata annotationMetadata) {
        super();
        this.annotationMetadata = annotationMetadata;
        setBeanClassName(this.annotationMetadata.getClassName());
    }

    @Override
    public final AnnotationMetadata getMetadata() {
        return this.annotationMetadata;
    }
}

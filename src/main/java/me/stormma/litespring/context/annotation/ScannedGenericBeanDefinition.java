package me.stormma.litespring.context.annotation;

import me.stormma.litespring.beans.factory.annotation.AnnotatedBeanDefinition;
import me.stormma.litespring.beans.factory.support.GenericBeanDefinition;
import me.stormma.litespring.core.type.AnnotationMetadata;

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

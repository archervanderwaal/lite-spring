package me.stormma.litespring.core.type.classreading;

import me.stormma.litespring.core.io.Resource;
import me.stormma.litespring.core.type.AnnotationMetadata;
import me.stormma.litespring.core.type.ClassMetadata;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/18
 */
public interface MetadataReader {

    Resource getResource();

    ClassMetadata getClassMetadata();

    AnnotationMetadata getAnnotationMetadata();
}

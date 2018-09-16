package com.archervanderwaal.litespring.core.type.classreading;

import com.archervanderwaal.litespring.core.io.Resource;
import com.archervanderwaal.litespring.core.type.AnnotationMetadata;
import com.archervanderwaal.litespring.core.type.ClassMetadata;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/18
 */
public interface MetadataReader {

    Resource getResource();

    ClassMetadata getClassMetadata();

    AnnotationMetadata getAnnotationMetadata();
}

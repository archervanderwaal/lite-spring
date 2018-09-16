package com.archervanderwaal.litespring.test.v4;

import com.archervanderwaal.litespring.core.annotation.AnnotationAttributes;
import com.archervanderwaal.litespring.core.io.ClassPathResource;
import com.archervanderwaal.litespring.core.type.AnnotationMetadata;
import com.archervanderwaal.litespring.core.type.classreading.MetadataReader;
import com.archervanderwaal.litespring.core.type.classreading.SimpleMetadataReader;
import com.archervanderwaal.litespring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/18
 */
public class MetadataReaderTestV4 {

    @Test
    public void testGetMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource("com/archervanderwaal/litespring/test/v4/entity/PetStoreService.class");
        MetadataReader reader = new SimpleMetadataReader(resource);
        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
        String annotation = Component.class.getName();
        Assert.assertTrue(annotationMetadata.hasAnnotation(annotation));
        AnnotationAttributes annotationAttributes = annotationMetadata.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", annotationAttributes.getString("value"));

        Assert.assertFalse(annotationMetadata.isAbstract());
    }
}

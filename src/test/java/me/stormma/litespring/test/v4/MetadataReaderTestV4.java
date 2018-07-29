package me.stormma.litespring.test.v4;

import me.stormma.litespring.core.annotation.AnnotationAttributes;
import me.stormma.litespring.core.io.ClassPathResource;
import me.stormma.litespring.core.type.AnnotationMetadata;
import me.stormma.litespring.core.type.classreading.MetadataReader;
import me.stormma.litespring.core.type.classreading.SimpleMetadataReader;
import me.stormma.litespring.stereotype.Component;
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
        ClassPathResource resource = new ClassPathResource("me/stormma/litespring/test/v4/entity/PetStoreService.class");
        MetadataReader reader = new SimpleMetadataReader(resource);
        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
        String annotation = Component.class.getName();
        Assert.assertTrue(annotationMetadata.hasAnnotation(annotation));
        AnnotationAttributes annotationAttributes = annotationMetadata.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", annotationAttributes.getString("value"));

        Assert.assertFalse(annotationMetadata.isAbstract());
    }
}

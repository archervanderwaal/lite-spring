package me.stormma.litespring.core.type.classreading;

import me.stormma.litespring.core.annotation.AnnotationAttributes;
import me.stormma.litespring.core.type.AnnotationMetadata;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/18
 */
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata {

    private final Set<String> annotations = new LinkedHashSet<>(4);

    private final Map<String, AnnotationAttributes> attributesMap = new LinkedHashMap<>(4);


    @Override
    public AnnotationVisitor visitAnnotation(String s, boolean b) {
        String annotationName = Type.getType(s).getClassName();
        this.annotations.add(annotationName);
        return new AnnotationAttributesReadingVisitor(annotationName, this.attributesMap);
    }

    @Override
    public Set<String> getAnnotationTypes() {
        return this.annotations;
    }

    @Override
    public boolean hasAnnotation(String annotationName) {
        return this.annotations.contains(annotationName);
    }

    @Override
    public AnnotationAttributes getAnnotationAttributes(String annotation) {
        return this.attributesMap.get(annotation);
    }
}

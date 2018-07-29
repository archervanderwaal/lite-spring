package me.stormma.litespring.core.type.classreading;

import me.stormma.litespring.core.annotation.AnnotationAttributes;
import org.objectweb.asm.AnnotationVisitor;

import java.util.Map;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/18
 */
public class AnnotationAttributesReadingVisitor implements AnnotationVisitor {

    private final String annotationType;

    private final Map<String, AnnotationAttributes> attributesMap;

    AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;
    }

    @Override
    public void visit(String s, Object o) {
        this.attributes.put(s, o);
    }

    @Override
    public void visitEnum(String s, String s1, String s2) {

    }

    @Override
    public AnnotationVisitor visitAnnotation(String s, String s1) {
        return null;
    }

    @Override
    public AnnotationVisitor visitArray(String s) {
        return null;
    }

    @Override
    public void visitEnd() {
        this.attributesMap.put(this.annotationType, this.attributes);
    }
}

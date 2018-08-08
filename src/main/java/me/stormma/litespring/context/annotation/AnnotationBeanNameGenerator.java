package me.stormma.litespring.context.annotation;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.factory.annotation.AnnotatedBeanDefinition;
import me.stormma.litespring.beans.factory.support.BeanDefinitionRegistry;
import me.stormma.litespring.beans.factory.support.BeanNameGenerator;
import me.stormma.litespring.core.annotation.AnnotationAttributes;
import me.stormma.litespring.core.type.AnnotationMetadata;
import me.stormma.litespring.stereotype.Component;
import me.stormma.litespring.utils.ClassUtils;
import me.stormma.litespring.utils.StringUtils;

import java.beans.Introspector;
import java.util.Map;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/18
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if (StringUtils.hasText(beanName)) {
                return beanName;
            }
        }
        return buildDefaultBeanName(definition, registry);
    }

    /**
     * Derive a bean name from one of the annotations on the class.
     * @param annotatedDef the annotation-aware bean definition
     * @return the bean name, or {@code null} if none is found
     */
    protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition annotatedDef) {
        AnnotationMetadata amd = annotatedDef.getMetadata();
        String beanName = null;
        AnnotationAttributes attributes;
        if (amd.hasAnnotation(Component.class.getName()) && (attributes = amd
                .getAnnotationAttributes(Component.class.getName())) != null) {
            String val;
            if (isStereotypeWithNameValue(attributes) &&
                            StringUtils.hasLength((val = attributes.getString("value")))) {
                beanName = val;
            }
        }
        return beanName;
    }

    protected boolean isStereotypeWithNameValue(Map<String, Object> attributes) {
        return attributes.containsKey("value");
    }

    /**
     * Derive a default bean name from the given bean definition.
     * <p>The default implementation delegates to {@link #buildDefaultBeanName(BeanDefinition)}.
     * @param definition the bean definition to build a bean name for
     * @param registry the registry that the given bean definition is being registered with
     * @return the default bean name (never {@code null})
     */
    protected String buildDefaultBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return buildDefaultBeanName(definition);
    }

    /**
     * Derive a default bean name from the given bean definition.
     * <p>The default implementation simply builds a decapitalized version
     * of the short class name: e.g. "mypackage.MyJdbcDao" -> "myJdbcDao".
     * <p>Note that inner classes will thus have names of the form
     * "outerClassName.InnerClassName", which because of the period in the
     * name may be an issue if you are autowiring by name.
     * @param definition the bean definition to build a bean name for
     * @return the default bean name (never {@code null})
     */
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }
}

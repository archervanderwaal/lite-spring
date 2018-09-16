package com.archervanderwaal.litespring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/13
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    /**
     * Declares whether the annotated dependency is required.
     * <p>Defaults to {@code true}.
     */
    boolean required() default true;
}

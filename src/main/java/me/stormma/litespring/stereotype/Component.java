package me.stormma.litespring.stereotype;

import java.lang.annotation.*;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any
     */
    String value() default "";
}

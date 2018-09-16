package com.archervanderwaal.litespring.aop;

import java.lang.reflect.Method;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface MethodMatcher {

    boolean matches(Method method/*, Class<?> targetClass*/);
}

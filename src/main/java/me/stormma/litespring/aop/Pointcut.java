package me.stormma.litespring.aop;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface Pointcut {

    MethodMatcher getMethodMatcher();

    String getExpression();
}

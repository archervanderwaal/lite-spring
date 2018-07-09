package me.stormma.litespring.beans;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface BeanDefinition {

    /**
     * singleton scope
     */
    String SCOPE_SINGLETON = "singleton";

    /**
     * prototype scope
     */
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * @return
     */
    String getBeanClassName();
}

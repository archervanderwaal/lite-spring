package me.stormma.litespring.beans.factory.config;

import me.stormma.litespring.beans.factory.BeanFactory;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface ConfigurableBeanFactory extends BeanFactory {

    void setBeanClassLoader(ClassLoader classLoader);

    ClassLoader getClassLoader();
}

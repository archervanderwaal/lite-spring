package me.stormma.litespring.beans.factory;

import me.stormma.litespring.beans.BeanDefinition;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface BeanFactory {

    BeanDefinition getBeanDefinition(String petStore);

    Object getBean(String petStore);
}

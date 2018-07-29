package me.stormma.litespring.beans.factory.config;

import me.stormma.litespring.beans.factory.BeanFactory;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object resolveDependency(DependencyDescriptor descriptor);
}

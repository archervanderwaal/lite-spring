package com.archervanderwaal.litespring.beans.factory.config;

import com.archervanderwaal.litespring.beans.factory.BeanFactory;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object resolveDependency(DependencyDescriptor descriptor);
}

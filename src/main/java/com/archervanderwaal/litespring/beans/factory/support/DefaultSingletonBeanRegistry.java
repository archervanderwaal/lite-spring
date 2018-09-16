package com.archervanderwaal.litespring.beans.factory.support;

import com.archervanderwaal.litespring.utils.Assert;
import com.archervanderwaal.litespring.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonBeans = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingletonBean(String beanId, Object singletonBean) {
        Assert.assertNotNull(beanId, "'beanId' must not be null.");
        Object oldSingletonBean = this.singletonBeans.get(beanId);
        if (!Objects.isNull(oldSingletonBean)) {
            throw new IllegalArgumentException("could not register bean [" + singletonBean +
                    "] under bean id '" + beanId + "': there is already object [" + oldSingletonBean + "]");
        }
        this.singletonBeans.put(beanId, singletonBean);
    }

    @Override
    public Object getSingletonBean(String beanId) {
        return this.singletonBeans.get(beanId);
    }
}

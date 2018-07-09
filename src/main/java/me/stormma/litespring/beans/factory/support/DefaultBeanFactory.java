package me.stormma.litespring.beans.factory.support;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.factory.BeanCreationException;
import me.stormma.litespring.beans.factory.BeanFactory;
import me.stormma.litespring.utils.ClassUtils;
import me.stormma.litespring.utils.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    /**
     * load bean definitions to map
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public DefaultBeanFactory() {

    }

    /**
     * get BeanDefinition
     * @param beanId
     * @return
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        if (StringUtils.isNullOrEmpty(beanId)) {
            throw new IllegalArgumentException("bean's attribute id must be not null, [ beanID = " + beanId + " ]");
        }
        return this.beanDefinitionMap.get(beanId);
    }

    @Override
    public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanId, beanDefinition);
    }

    /**
     * @param beanId
     * @return bean's instance, guarantee that the result is not null.
     */
    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanId);
        if (Objects.isNull(beanDefinition)) {
            throw new BeanCreationException(String.format("bean definition '[beanId = %s]' does not exist.", beanId));
        }
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> beanClass = classLoader.loadClass(beanClassName);
            // non-parametric constructor
            return beanClass.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " failed.");
        }
    }
}

package com.archervanderwaal.litespring.context.support;

import com.archervanderwaal.litespring.beans.factory.NoSuchBeanDefinitionException;
import com.archervanderwaal.litespring.beans.factory.annotation.AutowiredAnnotationProcessor;
import com.archervanderwaal.litespring.beans.factory.config.ConfigurableBeanFactory;
import com.archervanderwaal.litespring.context.ApplicationContext;
import com.archervanderwaal.litespring.beans.factory.support.DefaultBeanFactory;
import com.archervanderwaal.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import com.archervanderwaal.litespring.core.io.Resource;
import com.archervanderwaal.litespring.utils.ClassUtils;

/**
 * @author stormma stormmaybin@gmail.com
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory;

    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configurationFile) {
        this.factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this.factory);
        Resource resource = this.getResourceByPath(configurationFile);
        reader.loadBeanDefinition(resource);
        this.factory.setBeanClassLoader(this.getClassLoader());
        registerBeanPostProcessor(this.factory);
    }

    @Override
    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    protected abstract Resource getResourceByPath(String configurationFile);

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }

    protected void registerBeanPostProcessor(ConfigurableBeanFactory factory) {
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(factory);
        this.factory.addBeanPostProcess(processor);
    }

    @Override
    public Class<?> getType(String beanName) throws NoSuchBeanDefinitionException {
        return this.factory.getType(beanName);
    }
}

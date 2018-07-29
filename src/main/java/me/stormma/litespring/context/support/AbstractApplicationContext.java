package me.stormma.litespring.context.support;

import me.stormma.litespring.beans.factory.annotation.AutowiredAnnotationProcessor;
import me.stormma.litespring.beans.factory.config.ConfigurableBeanFactory;
import me.stormma.litespring.context.ApplicationContext;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import me.stormma.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import me.stormma.litespring.core.io.Resource;
import me.stormma.litespring.utils.ClassUtils;

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
}

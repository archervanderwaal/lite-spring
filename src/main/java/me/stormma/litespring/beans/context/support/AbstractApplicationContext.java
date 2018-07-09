package me.stormma.litespring.beans.context.support;

import me.stormma.litespring.beans.context.ApplicationContext;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import me.stormma.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import me.stormma.litespring.core.io.Resource;

/**
 * @author stormma stormmaybin@gmail.com
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory;

    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configurationFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configurationFile);
        reader.loadBeanDefinition(resource);
    }

    @Override
    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    protected abstract Resource getResourceByPath(String configurationFile);
}

package me.stormma.litespring.context.support;

import me.stormma.litespring.core.io.ClassPathResource;
import me.stormma.litespring.core.io.Resource;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String configurationFile) {
        super(configurationFile);
    }

    @Override
    protected Resource getResourceByPath(String configurationFile) {
        return new ClassPathResource(configurationFile, this.getClassLoader());
    }
}

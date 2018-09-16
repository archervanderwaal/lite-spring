package com.archervanderwaal.litespring.context.support;

import com.archervanderwaal.litespring.core.io.ClassPathResource;
import com.archervanderwaal.litespring.core.io.Resource;

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

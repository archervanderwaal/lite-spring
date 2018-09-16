package com.archervanderwaal.litespring.context.support;

import com.archervanderwaal.litespring.core.io.FileSystemResource;
import com.archervanderwaal.litespring.core.io.Resource;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

    public FileSystemXmlApplicationContext(String configurationFile) {
        super(configurationFile);
    }

    @Override
    protected Resource getResourceByPath(String configurationFile) {
        return new FileSystemResource(configurationFile);
    }
}

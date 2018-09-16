package com.archervanderwaal.litespring.core.io;

import com.archervanderwaal.litespring.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ClassPathResource implements Resource {

    private String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        InputStream inputStream = this.classLoader.getResourceAsStream(this.path);
        if (Objects.isNull(inputStream)) {
            throw new FileNotFoundException(this.path + " cannot be opened.");
        }
        return inputStream;
    }

    @Override
    public String getDescription() {
        return "Resource file in [" + this.path + "]";
    }

    @Override
    public String toString() {
        return getDescription();
    }
}

package me.stormma.litespring.core.io;

import me.stormma.litespring.utils.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class FileSystemResource implements Resource {

    private String path;

    private File file;

    public FileSystemResource(String path) {
        Assert.assertNotNull(path, "configuration file path must be not null.");
        this.path = path;
        this.file = new File(this.path);
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(this.file);
    }

    @Override
    public String getDescription() {
        return "configuration file in [" + this.file.getAbsolutePath() + "]";
    }
}

package me.stormma.litespring.core.io;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface Resource {

    InputStream getInputStream() throws FileNotFoundException;

    String getDescription();
}

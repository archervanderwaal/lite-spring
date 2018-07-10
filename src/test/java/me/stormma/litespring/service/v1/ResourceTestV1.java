package me.stormma.litespring.service.v1;

import me.stormma.litespring.core.io.ClassPathResource;
import me.stormma.litespring.core.io.FileSystemResource;
import me.stormma.litespring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ResourceTestV1 {

    @Test
    public void testClassPathResource() throws IOException {
        Resource resource = new ClassPathResource("petstore-v1.xml");
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            Assert.assertNotNull(inputStream);
        } finally {
            if (Objects.isNull(inputStream)) {
                inputStream.close();
            }
        }
    }

    @Test
    public void testFileSystemResource() throws IOException {
        Resource resource = new FileSystemResource("/Users/stormma/coding/java-project/lite-spring/src/test/resources/petstore-v1.xml");
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            Assert.assertNotNull(inputStream);
        } finally {
            if (Objects.isNull(inputStream)) {
                inputStream.close();
            }
        }
    }
}

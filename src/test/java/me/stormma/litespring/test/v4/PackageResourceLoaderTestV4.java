package me.stormma.litespring.test.v4;

import me.stormma.litespring.core.io.support.PackageResourceLoader;
import me.stormma.litespring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/13
 */
public class PackageResourceLoaderTestV4 {

    private static final String packageResource = "me.stormma.litespring.test.v4.entity";

    @Test
    public void testGetResources() {
        PackageResourceLoader packageResourceLoader = new PackageResourceLoader();
        Resource[] resources = packageResourceLoader.getResources(packageResource);
        Assert.assertTrue(resources.length != 0);
    }
}

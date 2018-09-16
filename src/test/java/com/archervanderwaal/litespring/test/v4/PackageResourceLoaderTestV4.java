package com.archervanderwaal.litespring.test.v4;

import com.archervanderwaal.litespring.core.io.support.PackageResourceLoader;
import com.archervanderwaal.litespring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/13
 */
public class PackageResourceLoaderTestV4 {

    private static final String packageResource = "com.archervanderwaal.litespring.test.v4.entity";

    @Test
    public void testGetResources() {
        PackageResourceLoader packageResourceLoader = new PackageResourceLoader();
        Resource[] resources = packageResourceLoader.getResources(packageResource);
        Assert.assertTrue(resources.length != 0);
    }
}

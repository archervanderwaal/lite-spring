package com.archervanderwaal.litespring.core.io.support;

import com.archervanderwaal.litespring.utils.Assert;
import com.archervanderwaal.litespring.utils.ClassUtils;
import com.archervanderwaal.litespring.core.io.FileSystemResource;
import com.archervanderwaal.litespring.core.io.Resource;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/13
 */
public class PackageResourceLoader {

    private static final Logger logger = Logger.getLogger(PackageResourceLoader.class);

    private final ClassLoader classLoader;

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.assertNotNull(classLoader, "ResourceLoader must not be null");
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public Resource[] getResources(String basePackage) {
        Assert.assertNotNull(basePackage, "basePackage  must be not null");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader cl = getClassLoader();
        URL url = cl.getResource(location);
        assert url != null;
        File rootDir = new File(url.getFile());
        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        Resource[] result = new Resource[matchingFiles.size()];
        int i=0;
        for (File file : matchingFiles) {
            result[i++]=new FileSystemResource(file);
        }
        return result;
    }

    protected Set<File> retrieveMatchingFiles(File rootDir) {
        if (!rootDir.exists()) {
            logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it does not exist");
            return Collections.emptySet();
        }
        if (!rootDir.isDirectory()) {
            logger.warn("Skipping [" + rootDir.getAbsolutePath() + "] because it does not denote a directory");
            return Collections.emptySet();
        }
        if (!rootDir.canRead()) {
            logger.warn("Cannot search for matching files underneath directory [" + rootDir.getAbsolutePath() +
                        "] because the application is not allowed to read the directory");
            return Collections.emptySet();
        }
        Set<File> result = new LinkedHashSet<>(8);
        doRetrieveMatchingFiles(rootDir, result);
        return result;
    }

    protected void doRetrieveMatchingFiles(File dir, Set<File> result) {
        File[] dirContents = dir.listFiles();
        if (dirContents == null) {
            logger.warn("Could not retrieve contents of directory [" + dir.getAbsolutePath() + "]");
            return;
        }
        for (File content : dirContents) {
            if (content.isDirectory() ) {
                if (!content.canRead()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Skipping subdirectory [" + dir.getAbsolutePath() +
                                "] because the application is not allowed to read the directory");
                    }
                } else {
                    doRetrieveMatchingFiles(content, result);
                }
            } else{
                result.add(content);
            }
        }
    }
}

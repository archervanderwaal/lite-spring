package com.archervanderwaal.litespring.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationTextTestV4.class,
        AutowiredAnnotationProcessorTestV4.class,
        ClassPathBeanDefinitionScannerTestV4.class,
        ClassReaderTestV4.class,
        DependencyDescriptorTestV4.class,
        InjectionMetadataTestV4.class,
        MetadataReaderTestV4.class,
        PackageResourceLoaderTestV4.class,
        XmlBeanDefinitionTestV4.class
})
public class V4AllTests {
}

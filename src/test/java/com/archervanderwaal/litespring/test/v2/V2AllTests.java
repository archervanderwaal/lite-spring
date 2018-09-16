package com.archervanderwaal.litespring.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author stormma stormmaybin@gmail.com
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTestV2.class,
        BeanDefinitionTestV2.class,
        BeanDefinitionValueResolverTestV2.class,
        CustomBooleanEditorTestV2.class,
        CustomNumberEditorTestV2.class,
        TypeConverterTestV2.class
})
public class V2AllTests {
}

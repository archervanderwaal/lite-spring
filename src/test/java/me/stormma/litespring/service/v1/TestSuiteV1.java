package me.stormma.litespring.service.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author stormma stormmaybin@gmail.com
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTestV1.class,
        BeanFactoryTestV1.class,
        ResourceTestV1.class
})
public class TestSuiteV1 {
}

package me.stormma.litespring.service.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author stormma stormmaybin@gmail.com
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTest.class,
        BeanFactoryTest.class,
        ResourceTest.class
})
public class V1TestSuite {
}

package me.stormma.litespring.test.v5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author stormma stormmaybin@gmail.com
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTestV5.class,
        CGLibTestV5.class,
        MethodLocatingFactoryTestV5.class,
        PointcutTestV5.class,
        ReflectiveMethodInvocationTestV5.class
})
public class V5AllTests {
}

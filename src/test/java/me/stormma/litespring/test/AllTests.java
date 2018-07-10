package me.stormma.litespring.test;

import me.stormma.litespring.test.v1.V1AllTests;
import me.stormma.litespring.test.v2.V2AllTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author stormma stormmaybin@gmail.com
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        V1AllTests.class,
        V2AllTests.class
})
public class AllTests {
}

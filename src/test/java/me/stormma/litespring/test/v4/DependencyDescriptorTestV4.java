package me.stormma.litespring.test.v4;

import me.stormma.litespring.beans.factory.config.DependencyDescriptor;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import me.stormma.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import me.stormma.litespring.core.io.ClassPathResource;
import me.stormma.litespring.core.io.Resource;
import me.stormma.litespring.test.v4.entity.AccountDao;
import me.stormma.litespring.test.v4.entity.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public class DependencyDescriptorTestV4 {

    @Test
    public void testResolveDependency() throws NoSuchFieldException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinition(resource);

        Field field = PetStoreService.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(field, true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof AccountDao);
    }
}

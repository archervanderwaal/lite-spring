package com.archervanderwaal.litespring.test.v4;

import com.archervanderwaal.litespring.beans.factory.config.DependencyDescriptor;
import com.archervanderwaal.litespring.beans.factory.support.DefaultBeanFactory;
import com.archervanderwaal.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import com.archervanderwaal.litespring.core.io.ClassPathResource;
import com.archervanderwaal.litespring.core.io.Resource;
import com.archervanderwaal.litespring.test.v4.entity.AccountDao;
import com.archervanderwaal.litespring.test.v4.entity.PetStoreService;
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

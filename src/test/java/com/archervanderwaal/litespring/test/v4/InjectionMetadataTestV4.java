package com.archervanderwaal.litespring.test.v4;

import com.archervanderwaal.litespring.beans.factory.annotation.InjectionElement;
import com.archervanderwaal.litespring.beans.factory.annotation.AutowiredFieldElement;
import com.archervanderwaal.litespring.beans.factory.annotation.InjectionMetadata;
import com.archervanderwaal.litespring.beans.factory.support.DefaultBeanFactory;
import com.archervanderwaal.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import com.archervanderwaal.litespring.core.io.ClassPathResource;
import com.archervanderwaal.litespring.core.io.Resource;
import com.archervanderwaal.litespring.test.v4.entity.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public class InjectionMetadataTestV4 {

    @Test
    public void testInjection() throws NoSuchFieldException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinition(resource);

        Class<?> clazz = PetStoreService.class;
        List<InjectionElement> elements = new LinkedList<>();
        {
            Field field = clazz.getDeclaredField("accountDao");
            InjectionElement injectionElement = new AutowiredFieldElement(field, factory, true);
            elements.add(injectionElement);
        }

        {
            Field field = clazz.getDeclaredField("itemDao");
            InjectionElement injectionElement = new AutowiredFieldElement(field, factory, true);
            elements.add(injectionElement);
        }

        InjectionMetadata metadata = new InjectionMetadata(clazz, elements);
        PetStoreService petStoreService = new PetStoreService();
        metadata.inject(petStoreService);
        Assert.assertTrue(petStoreService.getAccountDao() != null);
        Assert.assertTrue(petStoreService.getItemDao() != null);
    }
}

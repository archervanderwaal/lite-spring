package com.archervanderwaal.litespring.test.v4;

import com.archervanderwaal.litespring.beans.factory.annotation.AutowiredAnnotationProcessor;
import com.archervanderwaal.litespring.beans.factory.annotation.AutowiredFieldElement;
import com.archervanderwaal.litespring.beans.factory.annotation.InjectionElement;
import com.archervanderwaal.litespring.beans.factory.annotation.InjectionMetadata;
import com.archervanderwaal.litespring.test.v4.entity.PetStoreService;
import com.archervanderwaal.litespring.utils.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public class AutowiredAnnotationProcessorTestV4 {

    @Test
    public void testGetInjectionMetadata() {
        AutowiredAnnotationProcessor autowiredAnnotationProcessor = new AutowiredAnnotationProcessor();
        InjectionMetadata metadata = autowiredAnnotationProcessor.buildAutowiringMetadata(PetStoreService.class);
        List<InjectionElement> elements = metadata.getInjectionElements();
        Assert.assertTrue(elements.size() != 0);
        assertExistsField(elements, "accountDao");
    }

    private void assertExistsField(List<InjectionElement> elements, String fieldName) {
        if (CollectionUtils.isNullOrEmpty(elements)) {
            Assert.fail("elements is null or empty");
        }
        for (InjectionElement element : elements) {
            AutowiredFieldElement fieldElement = (AutowiredFieldElement) element;
            if (fieldElement.getField().getName().equalsIgnoreCase(fieldName)) {
                return;
            }
        }
        Assert.fail(fieldName + " not exist");
    }
}

package com.archervanderwaal.litespring.context.annotation;

import com.archervanderwaal.litespring.beans.BeanDefinition;
import com.archervanderwaal.litespring.beans.factory.BeanDefinitionStoreException;
import com.archervanderwaal.litespring.beans.factory.support.BeanDefinitionRegistry;
import com.archervanderwaal.litespring.beans.factory.support.BeanNameGenerator;
import com.archervanderwaal.litespring.core.io.Resource;
import com.archervanderwaal.litespring.core.io.support.PackageResourceLoader;
import com.archervanderwaal.litespring.core.type.classreading.MetadataReader;
import com.archervanderwaal.litespring.core.type.classreading.SimpleMetadataReader;
import com.archervanderwaal.litespring.stereotype.Component;
import com.archervanderwaal.litespring.utils.CollectionUtils;
import com.archervanderwaal.litespring.utils.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/18
 */
public class ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    private final BeanNameGenerator beanNameGenerator;

    private final Logger logger = Logger.getLogger(ClassPathBeanDefinitionScanner.class);

    public ClassPathBeanDefinitionScanner(final BeanDefinitionRegistry registry) {
        this.registry = registry;
        this.beanNameGenerator = new AnnotationBeanNameGenerator();
    }

    public Set<BeanDefinition> doScan(String basePackageToScan) {
        if (!StringUtils.hasText(basePackageToScan)) {
            return new LinkedHashSet<>();
        }
        String[] basePackages = StringUtils.tokenizeToStringArray(basePackageToScan, ",");
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            if (CollectionUtils.isNotNullOrEmpty(candidates)) {
                for (BeanDefinition candidate : candidates) {
                    beanDefinitions.add(candidate);
                    this.registry.registerBeanDefinition(candidate.getBeanId(), candidate);
                }
            }
        }
        return beanDefinitions;
    }

    private Set<BeanDefinition> findCandidateComponents(String basePackage) {
        if (!StringUtils.hasText(basePackage)) return null;
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Resource[] resources = this.resourceLoader.getResources(basePackage);
        for (Resource resource : resources) {
            try {
                MetadataReader reader = new SimpleMetadataReader(resource);
                if (reader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
                    ScannedGenericBeanDefinition beanDefinition = new ScannedGenericBeanDefinition(
                            reader.getAnnotationMetadata());
                    String beanName = this.beanNameGenerator.generateBeanName(beanDefinition, this.registry);
                    beanDefinition.setBeanId(beanName);
                    candidates.add(beanDefinition);
                }
            } catch (IOException e) {
                throw new BeanDefinitionStoreException(
                        "Filed to read candidate component class");
            }
        }
        return candidates;
    }
}

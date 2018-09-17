package com.archervanderwaal.litespring.test.v5;

import com.archervanderwaal.litespring.aop.config.AspectInstanceFactory;
import com.archervanderwaal.litespring.beans.factory.BeanFactory;
import com.archervanderwaal.litespring.beans.factory.support.DefaultBeanFactory;
import com.archervanderwaal.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import com.archervanderwaal.litespring.core.io.ClassPathResource;
import com.archervanderwaal.litespring.core.io.Resource;
import com.archervanderwaal.litespring.test.v5.tx.TransactionManager;

import java.lang.reflect.Method;

/**
 * @author archer archer.vanderwaal@gmail.com
 */
public class AbstractTestV5 {

    protected BeanFactory getBeanFactory(String configFile) {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        Resource resource = new ClassPathResource(configFile);
        reader.loadBeanDefinition(resource);
        return beanFactory;
    }

    protected Method getAdviceMethod(String methodName) throws Exception {
        return TransactionManager.class.getMethod(methodName);
    }

    protected AspectInstanceFactory getAspectInstanceFactory(String targetBeanName) {
        AspectInstanceFactory aspectInstanceFactory = new AspectInstanceFactory();
        aspectInstanceFactory.setAspectBeanName(targetBeanName);
        return aspectInstanceFactory;
    }
}

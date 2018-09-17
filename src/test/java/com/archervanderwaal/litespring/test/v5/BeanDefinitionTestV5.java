package com.archervanderwaal.litespring.test.v5;

import com.archervanderwaal.litespring.aop.aspectj.AspectJBeforeAdvice;
import com.archervanderwaal.litespring.aop.aspectj.AspectJExpressionPointcut;
import com.archervanderwaal.litespring.aop.config.AspectInstanceFactory;
import com.archervanderwaal.litespring.aop.config.MethodLocatingFactory;
import com.archervanderwaal.litespring.beans.BeanDefinition;
import com.archervanderwaal.litespring.beans.ConstructorArgument;
import com.archervanderwaal.litespring.beans.PropertyValue;
import com.archervanderwaal.litespring.beans.factory.config.RuntimeBeanReference;
import com.archervanderwaal.litespring.beans.factory.support.DefaultBeanFactory;
import com.archervanderwaal.litespring.test.v5.tx.TransactionManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author archer archer.vanderwaal@gmail.com
 */
public class BeanDefinitionTestV5 extends AbstractTestV5 {

    @Test
    public void testAOPBean() {
        DefaultBeanFactory defaultBeanFactory = (DefaultBeanFactory) this.getBeanFactory("petstore-v5.xml");
        {
            BeanDefinition beanDefinition = defaultBeanFactory.getBeanDefinition("tx");
            Assert.assertTrue(beanDefinition.getBeanClassName().equalsIgnoreCase(TransactionManager.class.getName()));
        }

        {
            BeanDefinition beanDefinition = defaultBeanFactory.getBeanDefinition("placeOrder");
            // assert synthetic bean
            Assert.assertTrue(beanDefinition.isSynthetic());
            Assert.assertTrue(beanDefinition.getBeanClassName().equalsIgnoreCase(AspectJExpressionPointcut.class.getName()));

            PropertyValue propertyValue = beanDefinition.getPropertyValues().get(0);
            Assert.assertEquals("expression", propertyValue.getName());
            Assert.assertEquals("execution(* com.archervanderwaal.litespring.test.v5.entity.*.placeOrder(..))",
                                        propertyValue.getValue());
        }

        {
            String name = AspectJBeforeAdvice.class.getName()+ "#0";
            BeanDefinition beanDefinition = defaultBeanFactory.getBeanDefinition(name);
            Assert.assertTrue(AspectJBeforeAdvice.class.getName().equalsIgnoreCase(beanDefinition.getBeanClassName()));
            Assert.assertTrue(beanDefinition.isSynthetic());
            List<ConstructorArgument.ValueHolder> valueHolders
                    = beanDefinition.getConstructorArgument().getArgumentValues();
            //
            Assert.assertEquals(3, valueHolders.size());
            // check the first argument, the first argument to find method
            {
                BeanDefinition innerBeanDefinition = (BeanDefinition) valueHolders.get(0).getValue();

                // synthetic bean
                Assert.assertTrue(innerBeanDefinition.isSynthetic());
                Assert.assertTrue(innerBeanDefinition.getBeanClass().equals(MethodLocatingFactory.class));
                List<PropertyValue> propertyValues = innerBeanDefinition.getPropertyValues();
                Assert.assertEquals("targetBeanName", propertyValues.get(0).getName());
                Assert.assertEquals("tx", propertyValues.get(0).getValue());
                Assert.assertEquals("methodName", propertyValues.get(1).getName());
                Assert.assertEquals("start", propertyValues.get(1).getValue());
            }
            // check the second argument
            {
                RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) valueHolders.get(1).getValue();
                Assert.assertEquals("placeOrder", runtimeBeanReference.getBeanId());
            }
            // check the third argument
            {
                BeanDefinition innerBeanDefinition = (BeanDefinition) valueHolders.get(2).getValue();
                Assert.assertTrue(innerBeanDefinition.isSynthetic());
                Assert.assertTrue(innerBeanDefinition.getBeanClass().equals(AspectInstanceFactory.class));

                List<PropertyValue> propertyValues = innerBeanDefinition.getPropertyValues();
                Assert.assertEquals("aspectBeanName", propertyValues.get(0).getName());
                Assert.assertEquals("tx", propertyValues.get(0).getValue());
            }
        }
    }
}

package com.archervanderwaal.litespring.aop.aspectj;

import com.archervanderwaal.litespring.aop.Advice;
import com.archervanderwaal.litespring.aop.MethodMatcher;
import com.archervanderwaal.litespring.aop.Pointcut;
import com.archervanderwaal.litespring.aop.framework.AopConfigSupport;
import com.archervanderwaal.litespring.aop.framework.AopProxyFactory;
import com.archervanderwaal.litespring.aop.framework.CglibAopProxyFactory;
import com.archervanderwaal.litespring.aop.framework.JdkAopProxyFactory;
import com.archervanderwaal.litespring.beans.BeansException;
import com.archervanderwaal.litespring.beans.factory.config.BeanPostProcessor;
import com.archervanderwaal.litespring.beans.factory.config.ConfigurableBeanFactory;
import com.archervanderwaal.litespring.utils.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author archer archer.vanderwaal@gmail.com
 */
public class AspectJAutoProxyProcessor implements BeanPostProcessor {

    private ConfigurableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // create proxy
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        List<Advice> advices = getCandidateAdvices(bean);
        if (advices.isEmpty()) {
            return bean;
        }
        return createProxy(advices, bean);
    }

    public void setBeanFactory(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected Object createProxy(List<Advice> advices, Object bean) {
        AopConfigSupport config = new AopConfigSupport();
        for (Advice advice : advices) {
            config.addAdvice(advice);
        }

        Set<Class> targetInterfaces = ClassUtils.getAllInterfacesForClassAsSet(bean.getClass());
        for (Class<?> targetInterface : targetInterfaces) {
            config.addInterface(targetInterface);
        }

        config.setTargetObject(bean);

        AopProxyFactory proxyFactory;
        // choose proxy factory, finished on 2018/09/18
        if (config.getProxiedInterfaces().length == 0) {
            proxyFactory = new CglibAopProxyFactory(config);
        } else {
            proxyFactory = new JdkAopProxyFactory(config);
        }
        return proxyFactory.getProxy();
    }

    private List<Advice> getCandidateAdvices(Object bean) {
        List<Object> advices = this.beanFactory.getBeansByType(Advice.class);
        List<Advice> result = new ArrayList<>();
        for (Object advice : advices) {
            Pointcut pc = ((Advice) advice).getPointcut();
            if (canApply(pc, bean.getClass())) {
                result.add((Advice) advice);
            }
        }
        return result;
    }

    private boolean canApply(Pointcut pc, Class<?> targetClass) {
        MethodMatcher methodMatcher = pc.getMethodMatcher();
        Set<Class> classes = new LinkedHashSet<>(ClassUtils.getAllInterfacesForClassAsSet(targetClass));
        classes.add(targetClass);
        for (Class<?> clazz : classes) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (methodMatcher.matches(method/*, targetClass*/)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass);
    }
}

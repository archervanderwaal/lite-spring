package me.stormma.litespring.beans.factory.annotation;

import me.stormma.litespring.beans.BeansException;
import me.stormma.litespring.beans.factory.BeanCreationException;
import me.stormma.litespring.beans.factory.config.AutowireCapableBeanFactory;
import me.stormma.litespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import me.stormma.litespring.core.annotation.AnnotationUtils;
import me.stormma.litespring.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;

    private String requiredParameterName = "required";

    private boolean requiredParameterValue = true;

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes =
            new LinkedHashSet<>();

    public AutowiredAnnotationProcessor() {
        this.autowiredAnnotationTypes.add(Autowired.class);
    }

    public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
        List<InjectionElement> elements = new LinkedList<>();
        Class<?> targetClass = clazz;
        do {
            List<InjectionElement> currElements = new LinkedList<>();
            for (Field field : targetClass.getDeclaredFields()) {
                Annotation ann = findAutowiredAnnotation(field);
                if (ann != null) {
                    // static field is not need injection
                    if (Modifier.isStatic(field.getModifiers())) continue;
                    boolean required = determineRequiredStatus(ann);
                    currElements.add(new AutowiredFieldElement(field, beanFactory, required));
                }
            }

            for (Method method : targetClass.getDeclaredMethods()) {
                //TODO 处理方法注入
            }
            elements.addAll(0, currElements);
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
        return new InjectionMetadata(clazz, elements);
    }

    private boolean determineRequiredStatus(Annotation ann) {
        try {
            Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName);
            // Annotations like @Inject and @Value don't have a method (attribute) named "required"
// -> default to required status
            return method == null ||
                    (this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method, ann));
        } catch (Exception ex) {
            // An exception was thrown during reflective invocation of the required attribute
            // -> default to required status
            return true;
        }
    }

    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            Annotation ann = AnnotationUtils.getAnnotation(ao, type);
            if (ann != null) {
                return ann;
            }
        }
        return null;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //do nothing
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // do nothing
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        // do nothing
        return true;
    }

    @Override
    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = buildAutowiringMetadata(bean.getClass());
        try {
            metadata.inject(bean);
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
        }
    }
}

package me.stormma.litespring.beans.factory.support;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.PropertyValue;
import me.stormma.litespring.beans.SimpleTypeConverter;
import me.stormma.litespring.beans.factory.BeanCreationException;
import me.stormma.litespring.beans.factory.BeanFactory;
import me.stormma.litespring.beans.factory.NoSuchBeanDefinitionException;
import me.stormma.litespring.beans.factory.config.BeanPostProcessor;
import me.stormma.litespring.beans.factory.config.ConfigurableBeanFactory;
import me.stormma.litespring.beans.factory.config.DependencyDescriptor;
import me.stormma.litespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import me.stormma.litespring.utils.ClassUtils;
import me.stormma.litespring.utils.CollectionUtils;
import me.stormma.litespring.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class DefaultBeanFactory
        extends DefaultSingletonBeanRegistry
        implements BeanFactory, BeanDefinitionRegistry, ConfigurableBeanFactory {

    /**
     * load bean definitions to map
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * bean post processors.
     */
    private final List<BeanPostProcessor> postProcessors = new ArrayList<>(16);

    private ClassLoader classLoader;

    protected final Logger logger = Logger.getLogger(DefaultBeanFactory.class);

    public DefaultBeanFactory() {

    }

    /**
     * get BeanDefinition
     * @param beanId
     * @return
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        if (StringUtils.isNullOrEmpty(beanId)) {
            throw new IllegalArgumentException("bean's attribute id must be not null, [ beanID = " + beanId + " ]");
        }
        return this.beanDefinitionMap.get(beanId);
    }

    @Override
    public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanId, beanDefinition);
    }

    /**
     * @param beanId
     * @return bean's instance, guarantee that the result is not null.
     */
    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanId);
        if (Objects.isNull(beanDefinition)) {
            throw new BeanCreationException(String.format("bean definition '[beanId = %s]' does not exist.", beanId));
        }
        if (beanDefinition.isSingleton()) {
            Object singletonBean = this.getSingletonBean(beanId);
            if (Objects.isNull(singletonBean)) {
                singletonBean = createBean(beanDefinition);
                this.registerSingletonBean(beanId, singletonBean);
            }
            return singletonBean;
        }
        return createBean(beanDefinition);
    }

    private Object createBean(BeanDefinition beanDefinition) {
        // create bean's instance
        Object bean = instantiateBean(beanDefinition);
        // populate bean's property, also can be use populateBeanUseCommonsBeanUtils
        populateBean(beanDefinition, bean);
        return bean;
    }

    private void populateBean(BeanDefinition beanDefinition, Object bean) {
        // invoke postProcessProperty hook method
        for (BeanPostProcessor processor : this.getBeanPostProcessors()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                ((InstantiationAwareBeanPostProcessor) processor)
                        .postProcessPropertyValues(bean, beanDefinition.getBeanId());
            }
        }
        // get bean's all property values, configuration in xml, such as: <property name = "dao" ref = "dao" /> or
        // <property name = "name" value = "storm">
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        if (CollectionUtils.isNotNullOrEmpty(propertyValues)) {
            // create BeanDefinitionValueResolver
            BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
            // get a simple type converter
            SimpleTypeConverter typeConverter = new SimpleTypeConverter();
            String populatePossibleErrorProperty = null;
            try {
                for (PropertyValue propertyValue : propertyValues) {
                    String propertyName = propertyValue.getName();
                    // originalValue instanceOf RuntimeBeanReference or TypedStringValue
                    // (name and Original value) attribute make up a instance of PropertyValue
                    Object originalValue = propertyValue.getValue();
                    // resolvedValue, if <bean> config is <property name="userDao" ref="userDao">,
                    // so resolvedValue is instance of UserDao, but if TypedStringValue, resolvedValue is itself.
                    Object resolvedValue = resolver.resolveValueIfNecessary(originalValue);
                    BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                        if (propertyDescriptor.getName().equals(propertyName)) {
                            populatePossibleErrorProperty = propertyDescriptor.getName();
                            Object convertValue = typeConverter.convertIfNecessary(resolvedValue, propertyDescriptor.getPropertyType());
                            propertyDescriptor.getWriteMethod().invoke(bean, convertValue);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("populate bean failed, " + e.getMessage());
                throw new BeanCreationException("failed to populate instance of class " + bean.getClass().getName()
                                    + " error property is '" + populatePossibleErrorProperty + "'");
            }
        }

    }

    @Deprecated
    private void populateBeanUseCommonsBeanUtils(BeanDefinition beanDefinition, Object bean) {
        // get bean's all property values, configuration in xml, such as: <property name = "dao" ref = "dao" /> or
        // <property name = "name" value = "storm">
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        if (CollectionUtils.isNotNullOrEmpty(propertyValues)) {
            // create BeanDefinitionValueResolver
            BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
            // get a simple type converter
            SimpleTypeConverter typeConverter = new SimpleTypeConverter();
            String populatePossibleErrorProperty = null;
            try {
                for (PropertyValue propertyValue : propertyValues) {
                    String propertyName = propertyValue.getName();
                    // originalValue instanceOf RuntimeBeanReference or TypedStringValue
                    // (name and Original value) attribute make up a instance of PropertyValue
                    Object originalValue = propertyValue.getValue();
                    // resolvedValue, if <bean> config is <property name="userDao" ref="userDao">,
                    // so resolvedValue is instance of UserDao, but if TypedStringValue, resolvedValue is itself.
                    Object resolvedValue = resolver.resolveValueIfNecessary(originalValue);
                    populatePossibleErrorProperty = propertyName;
                    BeanUtils.setProperty(bean, propertyName, resolvedValue);
                }
            } catch (Exception e) {
                logger.error("populate bean failed, " + e.getMessage());
                throw new BeanCreationException("failed to populate instance of class " + bean.getClass().getName()
                        + " error property is '" + populatePossibleErrorProperty + "'");
            }
        }
    }

    private Object instantiateBean(BeanDefinition beanDefinition) {
        if (beanDefinition.hasConstructorArgumentValues()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(beanDefinition);
        }
        ClassLoader classLoader = this.getClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            return classLoader.loadClass(beanClassName).newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " failed.");
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader();
    }

    @Override
    public void addBeanPostProcess(BeanPostProcessor beanPostProcessor) {
        this.postProcessors.add(beanPostProcessor);
    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.postProcessors;
    }

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyType();
        for (BeanDefinition beanDefinition : this.beanDefinitionMap.values()) {
            // resolveBeanClass
            resolveBeanClass(beanDefinition);
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (typeToMatch.isAssignableFrom(beanClass)) {
                return this.getBean(beanDefinition.getBeanId());
            }
        }
        return null;
    }

    @Override
    public Class<?> getType(String beanName) throws NoSuchBeanDefinitionException {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
        if (Objects.isNull(beanDefinition)) {
            throw new NoSuchBeanDefinitionException(beanName);
        }
        resolveBeanClass(beanDefinition);
        return beanDefinition.getBeanClass();
    }

    private void resolveBeanClass(BeanDefinition beanDefinition) {
        if(!beanDefinition.hasBeanClass()){
            try {
                beanDefinition.resolveBeanClass(this.getClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can't load class:" + beanDefinition.getBeanClassName());
            }
        }
    }
}

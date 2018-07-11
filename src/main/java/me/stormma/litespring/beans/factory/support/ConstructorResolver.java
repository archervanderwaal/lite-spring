package me.stormma.litespring.beans.factory.support;

import me.stormma.litespring.beans.BeanDefinition;
import me.stormma.litespring.beans.ConstructorArgument;
import me.stormma.litespring.beans.SimpleTypeConverter;
import me.stormma.litespring.beans.TypeMismatchException;
import me.stormma.litespring.beans.factory.BeanCreationException;
import me.stormma.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.Objects;

import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/11
 */
public class ConstructorResolver {

    private final ConfigurableBeanFactory beanFactory;

    private final Logger logger = Logger.getLogger(ConstructorResolver.class);

    public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object autowireConstructor(final BeanDefinition beanDefinition) {
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;
        Class<?> beanClass = Objects.isNull(beanDefinition.getBeanClass())
                ? beanDefinition.resolveBeanClass(this.beanFactory) : beanDefinition.getBeanClass();
        Constructor<?>[] candidates = beanClass.getConstructors();
        BeanDefinitionValueResolver valueResolver =
                new BeanDefinitionValueResolver(this.beanFactory);
        ConstructorArgument constructorArgument = beanDefinition.getConstructorArgument();
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        for (Constructor<?> candidate : candidates) {
            Class<?>[] parameterTypes = candidate.getParameterTypes();
            // if the count of this constructor's params not equals beanDefinition's constructorArgument.valueHolders
            if (parameterTypes.length != constructorArgument.getArgumentCount()) {
                continue;
            }
            argsToUse = new Object[parameterTypes.length];
            boolean result = this.valuesMatchTypes(parameterTypes, constructorArgument.getArgumentValues(),
                    argsToUse, valueResolver, typeConverter);
            if (result) {
                constructorToUse = candidate;
                break;
            }
        }
        if (Objects.isNull(constructorToUse)) {
            throw new BeanCreationException(beanDefinition.getBeanId(), "can't find a suitable constructor");
        }
        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(beanDefinition.getBeanId(), "can't find a create instance using " + constructorToUse);
        }
    }

    private boolean valuesMatchTypes(Class<?>[] parameterTypes,
                                     List<ConstructorArgument.ValueHolder> valueHolders,
                                     Object[] argsToUse,
                                     BeanDefinitionValueResolver valueResolver,
                                     SimpleTypeConverter typeConverter) {
        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder
                    = valueHolders.get(i);
            // originalValue instanceof RuntimeBeanReference or TypedStringValue
            Object originalValue = valueHolder.getValue();
            try {
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                argsToUse[i] = convertedValue;
            } catch (TypeMismatchException e) {
                return false;
            }
        }
        return true;
    }
}

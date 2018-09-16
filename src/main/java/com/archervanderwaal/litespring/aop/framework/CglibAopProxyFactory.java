package com.archervanderwaal.litespring.aop.framework;

import com.archervanderwaal.litespring.aop.Advice;
import com.archervanderwaal.litespring.aop.LiteSpringNamingPolicy;
import com.archervanderwaal.litespring.utils.Assert;
import net.sf.cglib.core.CodeGenerationException;
import net.sf.cglib.proxy.*;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class CglibAopProxyFactory implements AopProxyFactory {

    // Constants for CGLIB callback array indices
    private static final int AOP_PROXY = 0;
    private static final int INVOKE_TARGET = 1;
    private static final int NO_OVERRIDE = 2;
    private static final int DISPATCH_TARGET = 3;
    private static final int DISPATCH_ADVISED = 4;
    private static final int INVOKE_EQUALS = 5;
    private static final int INVOKE_HASHCODE = 6;

    protected static final Logger logger = Logger.getLogger(CglibAopProxyFactory.class);

    protected final AopConfig config;

    private Object[] constructorArgs;

    private Class<?>[] constructorArgTypes;

    public CglibAopProxyFactory(AopConfig config) throws AopConfigException {
        Assert.assertNotNull(config, "AopConfigSupport must be not null");
        if (config.getAdvices().size() == 0) {
            throw new AopConfigException("No advisors specified");
        }
        this.config = config;
    }

    public Object getProxy() {
        return getProxy(null);
    }

    public Object getProxy(ClassLoader classLoader) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating CGLIB proxy: target source is " + this.config.getTargetClass());
        }
        try {
            Class<?> rootClass = this.config.getTargetClass();
            // Configure CGLIB Enhancer...
            Enhancer enhancer = new Enhancer();
            if (classLoader != null) {
                enhancer.setClassLoader(classLoader);
            }
            enhancer.setSuperclass(rootClass);
            enhancer.setInterceptDuringConstruction(false);
            enhancer.setNamingPolicy(LiteSpringNamingPolicy.INSTANCE);
            Callback[] callbacks = getCallbacks(rootClass);
            Class<?>[] types = new Class<?>[callbacks.length];
            for (int x = 0; x < types.length; x++) {
                types[x] = callbacks[x].getClass();
            }
            enhancer.setCallbackFilter(new ProxyCallbackFilter(this.config));
            enhancer.setCallbackTypes(types);
            enhancer.setCallbacks(callbacks);
            // Generate the proxy class and create a proxy instance.
            return enhancer.create();
        } catch (CodeGenerationException ex) {
            throw new AopConfigException("Could not generate CGLIB subclass of class [" +
                    this.config.getTargetClass() + "]: " +
                    "Common causes of this problem include using a final class or a non-visible class",
                    ex);
        } catch (IllegalArgumentException ex) {
            throw new AopConfigException("Could not generate CGLIB subclass of class [" +
                    this.config.getTargetClass() + "]: " +
                    "Common causes of this problem include using a final class or a non-visible class",
                    ex);
        } catch (Exception ex) {
            throw new AopConfigException("Unexpected AOP exception", ex);
        }
    }

    private Callback[] getCallbacks(Class<?> rootClass) {
        Callback aopInterceptor = new DynamicAdvisedInterceptor(this.config);
        return new Callback[] {
                aopInterceptor
        };
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor, Serializable {
        private final AopConfig config;

        public DynamicAdvisedInterceptor(AopConfig advised) {
            this.config = advised;
        }

        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object target = this.config.getTargetObject();
            List<Advice> chain = this.config.getAdvices(method);
            Object retVal;
            if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())) {
                retVal = methodProxy.invoke(target, args);
            } else {
                List<org.aopalliance.intercept.MethodInterceptor> interceptors =
                        new ArrayList<>(chain);
                retVal = new ReflectiveMethodInvocation(target, method, args, interceptors).proceed();
            }
            return retVal;
        }
    }

    private static class ProxyCallbackFilter implements CallbackFilter {
        private final AopConfig config;

        public ProxyCallbackFilter(AopConfig advised) {
            this.config = advised;
        }

        public int accept(Method method) {
            return AOP_PROXY;
        }
    }
}
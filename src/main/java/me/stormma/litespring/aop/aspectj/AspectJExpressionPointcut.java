package me.stormma.litespring.aop.aspectj;

import me.stormma.litespring.aop.MethodMatcher;
import me.stormma.litespring.aop.Pointcut;
import me.stormma.litespring.utils.ClassUtils;
import me.stormma.litespring.utils.StringUtils;
import org.aspectj.weaver.reflect.ReflectionWorld;
import org.aspectj.weaver.tools.*;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class AspectJExpressionPointcut implements MethodMatcher, Pointcut {

    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
    }

    private String expression;

    private PointcutExpression pointcutExpression;

    private ClassLoader pointcutClassLoader;

    public AspectJExpressionPointcut() {

    }

    public void setExpression(String expression) {
        this.expression = expression;
    }


    @Override
    public boolean matches(Method method) {
        checkReadyToMatch();
        ShadowMatch shadowMatch = getShadowMatch(method);
        return shadowMatch.alwaysMatches();
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    private ShadowMatch getShadowMatch(Method method) {
        ShadowMatch shadowMatch;
        try {
            shadowMatch = this.pointcutExpression.matchesMethodExecution(method);
        }
        catch (ReflectionWorld.ReflectionWorldException ex) {
            throw new RuntimeException("not implemented yet");
			/*
			try {
				fallbackExpression = getFallbackPointcutExpression(methodToMatch.getDeclaringClass());
				if (fallbackExpression != null) {
					shadowMatch = fallbackExpression.matchesMethodExecution(methodToMatch);
				}
			}
			catch (ReflectionWorldException ex2) {
				fallbackExpression = null;
			}
			*/
        }
        return shadowMatch;
    }


    private void checkReadyToMatch() {
        if (this.getExpression() == null) {
            throw new IllegalStateException("Must set property 'expression' before attempting to match");
        }
        if (this.pointcutExpression == null) {
            this.pointcutClassLoader = ClassUtils.getDefaultClassLoader();
            this.pointcutExpression = buildPointcutExpression(this.pointcutClassLoader);
        }
    }

    private PointcutExpression buildPointcutExpression(ClassLoader classLoader) {
        PointcutParser parser = PointcutParser
                .getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                        SUPPORTED_PRIMITIVES, classLoader);

		/*
		PointcutParameter[] pointcutParameters = new PointcutParameter[this.pointcutParameterNames.length];
		for (int i = 0; i < pointcutParameters.length; i++) {
			pointcutParameters[i] = parser.createPointcutParameter(
					this.pointcutParameterNames[i], this.pointcutParameterTypes[i]);
		}
		*/
        return parser.parsePointcutExpression(replaceBooleanOperators(getExpression()),
                null, new PointcutParameter[0]);
    }

    private String replaceBooleanOperators(String pcExpr) {
        String result = StringUtils.replace(pcExpr, " and ", " && ");
        result = StringUtils.replace(result, " or ", " || ");
        result = StringUtils.replace(result, " not ", " ! ");
        return result;
    }
}

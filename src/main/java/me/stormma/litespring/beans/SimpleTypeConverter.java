package me.stormma.litespring.beans;

import me.stormma.litespring.beans.propertyeditors.CustomBooleanEditor;
import me.stormma.litespring.beans.propertyeditors.CustomNumberEditor;
import me.stormma.litespring.utils.ClassUtils;

import java.beans.PropertyEditor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class SimpleTypeConverter implements TypeConverter {

    private Map<Class<?>, PropertyEditor> defaultEditors;

    public SimpleTypeConverter() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convertIfNecessary(Object value, Class<T> targetClass) throws TypeMismatchException {
        if (ClassUtils.isAssignableValue(targetClass, value)) {
            return (T) value;
        } else {
            if (value instanceof String) {
                PropertyEditor editor = findDefaultEditor(targetClass);
                try {
                    editor.setAsText((String) value);
                } catch (IllegalArgumentException e) {
                    throw new TypeMismatchException(value, targetClass);
                }
                return (T)editor.getValue();
            } else {
                throw new RuntimeException("Todo: cannot convert value " + value + "to class " + targetClass);
            }
        }
    }

    private PropertyEditor findDefaultEditor(Class<?> targetType) {
        PropertyEditor editor = this.getDefaultEditor(targetType);
        if (Objects.isNull(editor)) {
            throw new RuntimeException("Editor for " + targetType + " has not been implemented.");
        }
        return editor;
    }

    public PropertyEditor getDefaultEditor(Class<?> targetType) {
        if (this.defaultEditors == null) {
            createDefaultEditors();
        }
        return this.defaultEditors.get(targetType);
    }

    private void createDefaultEditors() {
        this.defaultEditors = new HashMap<>(64);
        // Spring's CustomBooleanEditor accepts more flag values than the JDK's default editor.
        this.defaultEditors.put(boolean.class, new CustomBooleanEditor(false));
        this.defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));
        // The JDK does not contain default editors for number wrapper types!
        // Override JDK primitive number editors with our own CustomNumberEditor.
		this.defaultEditors.put(byte.class, new CustomNumberEditor(Byte.class, false));
		this.defaultEditors.put(Byte.class, new CustomNumberEditor(Byte.class, true));
		this.defaultEditors.put(short.class, new CustomNumberEditor(Short.class, false));
		this.defaultEditors.put(Short.class, new CustomNumberEditor(Short.class, true));
        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
		this.defaultEditors.put(long.class, new CustomNumberEditor(Long.class, false));
		this.defaultEditors.put(Long.class, new CustomNumberEditor(Long.class, true));
		this.defaultEditors.put(float.class, new CustomNumberEditor(Float.class, false));
		this.defaultEditors.put(Float.class, new CustomNumberEditor(Float.class, true));
		this.defaultEditors.put(double.class, new CustomNumberEditor(Double.class, false));
		this.defaultEditors.put(Double.class, new CustomNumberEditor(Double.class, true));
		this.defaultEditors.put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
		this.defaultEditors.put(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
    }
}

package me.stormma.litespring.test.v2;

import me.stormma.litespring.beans.SimpleTypeConverter;
import me.stormma.litespring.beans.TypeConverter;
import me.stormma.litespring.beans.TypeMismatchException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class TypeConverterTestV2 {

    @Test
    public void testTypeConvertString2Integer() throws TypeMismatchException {
        TypeConverter converter = new SimpleTypeConverter();
        Integer integer = converter.convertIfNecessary("3", Integer.class);
        Assert.assertTrue(integer.equals(3));

        try {
            converter.convertIfNecessary("3.1", Integer.class);
        } catch (TypeMismatchException exception) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testTypeConvertString2Boolean() throws TypeMismatchException {
        TypeConverter converter = new SimpleTypeConverter();
        Boolean b = converter.convertIfNecessary("true", Boolean.class);
        Assert.assertTrue(b);

        try {
            converter.convertIfNecessary("invalid input", Boolean.class);
        } catch (TypeMismatchException exception) {
            return;
        }
        Assert.fail();
    }
}

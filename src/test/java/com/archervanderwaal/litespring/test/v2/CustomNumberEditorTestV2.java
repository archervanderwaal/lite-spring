package com.archervanderwaal.litespring.test.v2;

import com.archervanderwaal.litespring.beans.propertyeditors.CustomNumberEditor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class CustomNumberEditorTestV2 {

    @Test
    public void testConvertString2Number() {
        CustomNumberEditor customNumberEditor = new CustomNumberEditor(Integer.class, true);

        // normal
        customNumberEditor.setAsText("3");
        Object value = customNumberEditor.getValue();
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(3, ((Integer) value).intValue());

        // border
        customNumberEditor.setAsText("");
        Assert.assertTrue(customNumberEditor.getValue() == null);

        // exception
        try {
            customNumberEditor.setAsText("3.1");
        } catch (IllegalArgumentException exception) {
            return;
        }
        Assert.fail();
    }
}

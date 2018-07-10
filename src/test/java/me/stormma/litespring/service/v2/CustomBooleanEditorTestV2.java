package me.stormma.litespring.service.v2;

import me.stormma.litespring.beans.propertyeditors.CustomBooleanEditor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class CustomBooleanEditorTestV2 {

    @Test
    public void testConvertString2Boolean() {
        CustomBooleanEditor booleanEditor = new CustomBooleanEditor(true);
        //normal
        booleanEditor.setAsText("true");
        Assert.assertTrue((Boolean) booleanEditor.getValue());
        booleanEditor.setAsText("false");
        Assert.assertFalse((Boolean) booleanEditor.getValue());

        // other
        booleanEditor.setAsText("on");
        Assert.assertTrue((Boolean) booleanEditor.getValue());
        booleanEditor.setAsText("off");
        Assert.assertFalse((Boolean) booleanEditor.getValue());

        booleanEditor.setAsText("yes");
        Assert.assertTrue((Boolean) booleanEditor.getValue());
        booleanEditor.setAsText("no");
        Assert.assertFalse((Boolean) booleanEditor.getValue());

        try {
            booleanEditor.setAsText("invalid input");
        } catch (IllegalArgumentException exception) {
            return;
        }
        Assert.fail();
    }
}

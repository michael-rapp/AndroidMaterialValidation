/*
 * Copyright 2015 - 2018 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.mrapp.android.validation;

import android.content.Context;
import android.os.Build;
import android.test.AndroidTestCase;
import android.util.AttributeSet;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

/**
 * Tests the functionality of the class {@link EditText}.
 *
 * @author Michael Rapp
 */
public class EditTextTest extends AndroidTestCase {

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a context as a
     * parameter.
     */
    public final void testConstructorWithContextParameter() {
        Context context = getContext();
        EditText editText = new EditText(context);
        assertEquals(context, editText.getContext());
        assertEquals(-1, editText.getMaxNumberOfCharacters());
    }

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a context and an
     * attribute set as parameters.
     */
    public final void testConstructorWithContextAndAttributeSetParameters() {
        Context context = getContext();
        XmlPullParser xmlPullParser = context.getResources().getXml(R.xml.edit_text);
        AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);
        EditText editText = new EditText(context, attributeSet);
        assertEquals(context, editText.getContext());
        assertEquals(-1, editText.getMaxNumberOfCharacters());
    }

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a context, an
     * attribute set and a default style as parameters.
     */
    public final void testConstructorWithContextAttributeSetAndDefaultStyleParameters() {
        Context context = getContext();
        int defaultStyle = 0;
        XmlPullParser xmlPullParser = context.getResources().getXml(R.xml.edit_text);
        AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);
        EditText editText = new EditText(context, attributeSet, defaultStyle);
        assertEquals(context, editText.getContext());
        assertEquals(-1, editText.getMaxNumberOfCharacters());
    }

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a context, an
     * attribute set, a default style and a default style attribute as parameters.
     */
    public final void testConstructorWithContextAttributeSetAndDefaultStyleAndDefaultStyleAttributeParameters() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Context context = getContext();
            int defaultStyle = 0;
            int defaultStyleAttribute = 0;
            XmlPullParser xmlPullParser = context.getResources().getXml(R.xml.edit_text);
            AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);
            EditText editText =
                    new EditText(context, attributeSet, defaultStyle, defaultStyleAttribute);
            assertEquals(context, editText.getContext());
            assertEquals(-1, editText.getMaxNumberOfCharacters());
        }
    }

    /**
     * Tests the functionality of the method, which allows to set the maximum number of characters.
     */
    public final void testSetMaxNumberOfCharacters() {
        int maxNumberOfCharacters = 2;
        EditText editText = new EditText(getContext());
        editText.setMaxNumberOfCharacters(maxNumberOfCharacters);
        assertEquals(maxNumberOfCharacters, editText.getMaxNumberOfCharacters());
    }

}
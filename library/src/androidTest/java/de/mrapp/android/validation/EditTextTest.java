/*
 * AndroidMaterialValidation Copyright 2015 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package de.mrapp.android.validation;

import android.content.Context;
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
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
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.test.AndroidTestCase;
import android.util.AttributeSet;
import android.util.Xml;
import android.widget.ArrayAdapter;

import org.xmlpull.v1.XmlPullParser;

/**
 * Tests the functionality of the class {@link Spinner}.
 *
 * @author Michael Rapp
 */
public class SpinnerTest extends AndroidTestCase {

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a context as a
     * parameter.
     */
    public final void testConstructorWithContextParameter() {
        Context context = getContext();
        Spinner spinner = new Spinner(context);
        assertEquals(context, spinner.getContext());
        assertNull(spinner.getHint());
        assertNotNull(spinner.getHintTextColors());
    }

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a context and an
     * attribute set as parameters.
     */
    public final void testConstructorWithContextAndAttributeSetParameters() {
        Context context = getContext();
        XmlPullParser xmlPullParser = context.getResources().getXml(R.xml.edit_text);
        AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);
        Spinner spinner = new Spinner(context, attributeSet);
        assertEquals(context, spinner.getContext());
        assertNull(spinner.getHint());
        assertNotNull(spinner.getHintTextColors());
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
        Spinner spinner = new Spinner(context, attributeSet, defaultStyle);
        assertEquals(context, spinner.getContext());
        assertNull(spinner.getHint());
        assertNotNull(spinner.getHintTextColors());
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
        Spinner spinner = new Spinner(context, attributeSet, defaultStyle, defaultStyleAttribute);
        assertEquals(context, spinner.getContext());
        assertNull(spinner.getHint());
        assertNotNull(spinner.getHintTextColors());
    }

    /**
     * Tests the functionality of the method, which allows to set the hint and expects a char
     * sequence as a parameter.
     */
    public final void testSetHintWithCharSequenceParameter() {
        CharSequence hint = "hint";
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new CharSequence[]{"entry1", "entry2"});
        Spinner spinner = new Spinner(getContext());
        spinner.setAdapter(adapter);
        spinner.setHint(hint);
        assertEquals(hint, spinner.getHint());
    }

    /**
     * Tests the functionality of the method, which allows to set the hint and expects a resource id
     * as a parameter.
     */
    public final void testSetHintWithResourceIdParameter() {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new CharSequence[]{"entry1", "entry2"});
        Spinner spinner = new Spinner(getContext());
        spinner.setAdapter(adapter);
        spinner.setHint(android.R.string.cancel);
        assertEquals(getContext().getText(android.R.string.cancel), spinner.getHint());
    }

    /**
     * Tests the functionality of the method, which allows to set the hint text color and expects an
     * integer as a parameter.
     */
    public final void testSetHintTextColorWithIntegerParameter() {
        int color = Color.BLACK;
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new CharSequence[]{"entry1", "entry2"});
        Spinner spinner = new Spinner(getContext());
        spinner.setAdapter(adapter);
        spinner.setHintTextColor(color);
        assertEquals(color, spinner.getHintTextColors().getDefaultColor());
    }

    /**
     * Tests the functionality of the method, which allows to set the hint text color and expects a
     * color state list as a parameter.
     */
    public final void testSetHintTextColorWithColorStateListParameter() {
        ColorStateList colorStateList = ColorStateList.valueOf(Color.BLACK);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new CharSequence[]{"entry1", "entry2"});
        Spinner spinner = new Spinner(getContext());
        spinner.setAdapter(adapter);
        spinner.setHintTextColor(colorStateList);
        assertEquals(colorStateList, spinner.getHintTextColors());
    }

}
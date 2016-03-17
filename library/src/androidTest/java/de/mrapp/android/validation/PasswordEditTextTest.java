/*
 * Copyright 2015 - 2016 Michael Rapp
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
import android.graphics.Color;
import android.os.Build;
import android.test.AndroidTestCase;
import android.util.AttributeSet;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Tests the functionality of the class {@link PasswordEditText}.
 *
 * @author Michael Rapp
 */
public class PasswordEditTextTest extends AndroidTestCase {

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a context as a
     * parameter.
     */
    public final void testConstructorWithContextParameter() {
        Context context = getContext();
        PasswordEditText passwordEditText = new PasswordEditText(context);
        assertEquals(context, passwordEditText.getContext());
        assertTrue(passwordEditText.getConstraints().isEmpty());
        assertTrue(passwordEditText.getHelperTexts().isEmpty());
        assertTrue(passwordEditText.getHelperTextColors().isEmpty());
        assertEquals(getContext().getText(R.string.password_verification_prefix),
                passwordEditText.getPasswordVerificationPrefix());
    }

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a context and an
     * attribute set as parameters.
     */
    public final void testConstructorWithContextAndAttributeSetParameters() {
        Context context = getContext();
        XmlPullParser xmlPullParser = context.getResources().getXml(R.xml.edit_text);
        AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);
        PasswordEditText passwordEditText = new PasswordEditText(context, attributeSet);
        assertEquals(context, passwordEditText.getContext());
        assertTrue(passwordEditText.getConstraints().isEmpty());
        assertTrue(passwordEditText.getHelperTexts().isEmpty());
        assertTrue(passwordEditText.getHelperTextColors().isEmpty());
        assertEquals(getContext().getText(R.string.password_verification_prefix),
                passwordEditText.getPasswordVerificationPrefix());
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
        PasswordEditText passwordEditText =
                new PasswordEditText(context, attributeSet, defaultStyle);
        assertEquals(context, passwordEditText.getContext());
        assertTrue(passwordEditText.getConstraints().isEmpty());
        assertTrue(passwordEditText.getHelperTexts().isEmpty());
        assertTrue(passwordEditText.getHelperTextColors().isEmpty());
        assertEquals(getContext().getText(R.string.password_verification_prefix),
                passwordEditText.getPasswordVerificationPrefix());
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
            PasswordEditText passwordEditText =
                    new PasswordEditText(context, attributeSet, defaultStyle,
                            defaultStyleAttribute);
            assertEquals(context, passwordEditText.getContext());
            assertTrue(passwordEditText.getConstraints().isEmpty());
            assertTrue(passwordEditText.getHelperTexts().isEmpty());
            assertTrue(passwordEditText.getHelperTextColors().isEmpty());
            assertEquals(getContext().getText(R.string.password_verification_prefix),
                    passwordEditText.getPasswordVerificationPrefix());
        }
    }

    /**
     * Tests the functionality of the method, which allows to add a constraint.
     */
    public final void testAddConstraint() {
        Constraint<CharSequence> constraint = Constraints.containsNumber();
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addConstraint(constraint);
        passwordEditText.addConstraint(constraint);
        assertEquals(1, passwordEditText.getConstraints().size());
        assertEquals(constraint, passwordEditText.getConstraints().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to add all constraints, which are
     * contained by a collection.
     */
    public final void testAddAllConstraintsFromCollection() {
        Constraint<CharSequence> constraint1 = Constraints.containsNumber();
        Constraint<CharSequence> constraint2 = Constraints.containsLetter();
        Collection<Constraint<CharSequence>> constraints1 = new LinkedList<>();
        constraints1.add(constraint1);
        constraints1.add(constraint2);
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllConstraints(constraints1);
        passwordEditText.addAllConstraints(constraints1);
        Collection<Constraint<CharSequence>> constraints2 = passwordEditText.getConstraints();
        assertEquals(constraints1.size(), constraints2.size());
        Iterator<Constraint<CharSequence>> iterator = constraints2.iterator();
        assertEquals(constraint1, iterator.next());
        assertEquals(constraint2, iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to add all constraints, which are
     * contained by an array.
     */
    @SuppressWarnings("unchecked")
    public final void testAddAllConstraintsFromArray() {
        Constraint<CharSequence> constraint1 = Constraints.containsNumber();
        Constraint<CharSequence> constraint2 = Constraints.containsLetter();
        Constraint<CharSequence>[] constraints1 = new Constraint[2];
        constraints1[0] = constraint1;
        constraints1[1] = constraint2;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllConstraints(constraints1);
        passwordEditText.addAllConstraints(constraints1);
        Collection<Constraint<CharSequence>> constraints2 = passwordEditText.getConstraints();
        assertEquals(constraints1.length, constraints2.size());
        Iterator<Constraint<CharSequence>> iterator = constraints2.iterator();
        assertEquals(constraint1, iterator.next());
        assertEquals(constraint2, iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to remove a constraint.
     */
    public final void testRemoveConstraint() {
        Constraint<CharSequence> constraint1 = Constraints.containsNumber();
        Constraint<CharSequence> constraint2 = Constraints.containsLetter();
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addConstraint(constraint1);
        passwordEditText.addConstraint(constraint2);
        passwordEditText.removeConstraint(constraint1);
        passwordEditText.removeConstraint(constraint1);
        assertEquals(1, passwordEditText.getConstraints().size());
        assertEquals(constraint2, passwordEditText.getConstraints().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all constraints, which are
     * contained by a collection.
     */
    public final void testRemoveAllConstraintsFromCollection() {
        Constraint<CharSequence> constraint1 = Constraints.containsNumber();
        Constraint<CharSequence> constraint2 = Constraints.containsLetter();
        Constraint<CharSequence> constraint3 = Constraints.containsSymbol();
        Collection<Constraint<CharSequence>> constraints = new LinkedList<>();
        constraints.add(constraint1);
        constraints.add(constraint2);
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllConstraints(constraints);
        passwordEditText.addConstraint(constraint3);
        passwordEditText.removeAllConstraints(constraints);
        passwordEditText.removeAllConstraints(constraints);
        assertEquals(1, passwordEditText.getConstraints().size());
        assertEquals(constraint3, passwordEditText.getConstraints().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all constraints, which are
     * contained by an array.
     */
    @SuppressWarnings("unchecked")
    public final void testRemoveAllConstraintsFromArray() {
        Constraint<CharSequence> constraint1 = Constraints.containsNumber();
        Constraint<CharSequence> constraint2 = Constraints.containsLetter();
        Constraint<CharSequence> constraint3 = Constraints.containsSymbol();
        Constraint<CharSequence>[] constraints = new Constraint[2];
        constraints[0] = constraint1;
        constraints[1] = constraint2;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllConstraints(constraints);
        passwordEditText.addConstraint(constraint3);
        passwordEditText.removeAllConstraints(constraints);
        passwordEditText.removeAllConstraints(constraints);
        assertEquals(1, passwordEditText.getConstraints().size());
        assertEquals(constraint3, passwordEditText.getConstraints().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all constraints.
     */
    public final void testRemoveAllConstraints() {
        Constraint<CharSequence> constraint1 = Constraints.containsNumber();
        Constraint<CharSequence> constraint2 = Constraints.containsLetter();
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addConstraint(constraint1);
        passwordEditText.addConstraint(constraint2);
        passwordEditText.removeAllConstraints();
        passwordEditText.removeAllConstraints();
        assertTrue(passwordEditText.getConstraints().isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to add a helper text.
     */
    public final void testAddHelperText() {
        CharSequence helperText = "helperText";
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addHelperText(helperText);
        assertEquals(1, passwordEditText.getHelperTexts().size());
        assertEquals(helperText, passwordEditText.getHelperTexts().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to add a helper text by its resource id.
     */
    public final void testAddHelperTextId() {
        CharSequence helperText = getContext().getText(android.R.string.cancel);
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addHelperTextId(android.R.string.cancel);
        assertEquals(1, passwordEditText.getHelperTexts().size());
        assertEquals(helperText, passwordEditText.getHelperTexts().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to add all helper texts, which are
     * contained by a collection.
     */
    public final void testAddAllHelperTextsFromCollection() {
        CharSequence helperText1 = "helperText1";
        CharSequence helperText2 = "helperText2";
        Collection<CharSequence> helperTexts1 = new LinkedList<>();
        helperTexts1.add(helperText1);
        helperTexts1.add(helperText2);
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTexts(helperTexts1);
        passwordEditText.addAllHelperTexts(helperTexts1);
        Collection<CharSequence> helperTexts2 = passwordEditText.getHelperTexts();
        assertEquals(helperTexts1.size(), helperTexts2.size());
        Iterator<CharSequence> iterator = helperTexts2.iterator();
        assertEquals(helperText1, iterator.next());
        assertEquals(helperText2, iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to add all helper texts by the ids, which
     * are contained by a collection.
     */
    public final void testAddAllHelperTextIdsFromCollection() {
        CharSequence helperText1 = getContext().getText(android.R.string.cancel);
        CharSequence helperText2 = getContext().getText(android.R.string.copy);
        Collection<Integer> helperTextIds = new LinkedList<>();
        helperTextIds.add(android.R.string.cancel);
        helperTextIds.add(android.R.string.copy);
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTextIds(helperTextIds);
        passwordEditText.addAllHelperTextIds(helperTextIds);
        Collection<CharSequence> helperTexts = passwordEditText.getHelperTexts();
        assertEquals(helperTextIds.size(), helperTexts.size());
        Iterator<CharSequence> iterator = helperTexts.iterator();
        assertEquals(helperText1, iterator.next());
        assertEquals(helperText2, iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to add all helper texts, which are
     * contained by an array.
     */
    public final void testAddAllHelperTextsFromArray() {
        CharSequence helperText1 = "helperText1";
        CharSequence helperText2 = "helperText2";
        CharSequence[] helperTexts1 = new CharSequence[2];
        helperTexts1[0] = helperText1;
        helperTexts1[1] = helperText2;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTexts(helperTexts1);
        passwordEditText.addAllHelperTexts(helperTexts1);
        Collection<CharSequence> helperTexts2 = passwordEditText.getHelperTexts();
        assertEquals(helperTexts1.length, helperTexts2.size());
        Iterator<CharSequence> iterator = helperTexts2.iterator();
        assertEquals(helperText1, iterator.next());
        assertEquals(helperText2, iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to add all helper texts by the ids, which
     * are contained by an array.
     */
    public final void testAddAllHelperTextIdsFromArray() {
        CharSequence helperText1 = getContext().getText(android.R.string.cancel);
        CharSequence helperText2 = getContext().getText(android.R.string.copy);
        int[] helperTextIds = new int[2];
        helperTextIds[0] = android.R.string.cancel;
        helperTextIds[1] = android.R.string.copy;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTextIds(helperTextIds);
        passwordEditText.addAllHelperTextIds(helperTextIds);
        Collection<CharSequence> helperTexts = passwordEditText.getHelperTexts();
        assertEquals(helperTextIds.length, helperTexts.size());
        Iterator<CharSequence> iterator = helperTexts.iterator();
        assertEquals(helperText1, iterator.next());
        assertEquals(helperText2, iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to remove a helper text.
     */
    public final void testRemoveHelperText() {
        CharSequence helperText1 = "helperText1";
        CharSequence helperText2 = "helperText2";
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addHelperText(helperText1);
        passwordEditText.addHelperText(helperText2);
        passwordEditText.removeHelperText(helperText1);
        passwordEditText.removeHelperText(helperText1);
        assertEquals(1, passwordEditText.getHelperTexts().size());
        assertEquals(helperText2, passwordEditText.getHelperTexts().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove a helper text by its id.
     */
    public final void testRemoveHelperTextId() {
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addHelperTextId(android.R.string.cancel);
        passwordEditText.addHelperTextId(android.R.string.copy);
        passwordEditText.removeHelperTextId(android.R.string.cancel);
        passwordEditText.removeHelperTextId(android.R.string.cancel);
        assertEquals(1, passwordEditText.getHelperTexts().size());
        assertEquals(getContext().getText(android.R.string.copy),
                passwordEditText.getHelperTexts().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all helper texts, which are
     * contained by a collection.
     */
    public final void testRemoveAllHelperTextsFromCollection() {
        CharSequence helperText1 = "helperText1";
        CharSequence helperText2 = "helperText2";
        CharSequence helperText3 = "helperText3";
        Collection<CharSequence> helperTexts = new LinkedList<>();
        helperTexts.add(helperText1);
        helperTexts.add(helperText2);
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTexts(helperTexts);
        passwordEditText.addHelperText(helperText3);
        passwordEditText.removeAllHelperTexts(helperTexts);
        passwordEditText.removeAllHelperTexts(helperTexts);
        assertEquals(1, passwordEditText.getHelperTexts().size());
        assertEquals(helperText3, passwordEditText.getHelperTexts().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all helper texts, which are
     * contained by an array.
     */
    public final void testRemoveAllHelperTextsFromArray() {
        CharSequence helperText1 = "helperText1";
        CharSequence helperText2 = "helperText2";
        CharSequence helperText3 = "helperText3";
        CharSequence[] helperTexts = new CharSequence[2];
        helperTexts[0] = helperText1;
        helperTexts[1] = helperText2;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTexts(helperTexts);
        passwordEditText.addHelperText(helperText3);
        passwordEditText.removeAllHelperTexts(helperTexts);
        passwordEditText.removeAllHelperTexts(helperTexts);
        assertEquals(1, passwordEditText.getHelperTexts().size());
        assertEquals(helperText3, passwordEditText.getHelperTexts().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all helper texts by the ids,
     * which are contained by a collection.
     */
    public final void testRemoveAllHelperTextIdsFromCollection() {
        Collection<Integer> helperTextIds = new LinkedList<>();
        helperTextIds.add(android.R.string.cancel);
        helperTextIds.add(android.R.string.copy);
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTextIds(helperTextIds);
        passwordEditText.addHelperTextId(android.R.string.copyUrl);
        passwordEditText.removeAllHelperTextIds(helperTextIds);
        passwordEditText.removeAllHelperTextIds(helperTextIds);
        assertEquals(1, passwordEditText.getHelperTexts().size());
        assertEquals(getContext().getText(android.R.string.copyUrl),
                passwordEditText.getHelperTexts().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all helper texts by the ids,
     * which are contained by an array.
     */
    public final void testRemoveAllHelperTextIdsFromArray() {
        int[] helperTextIds = new int[2];
        helperTextIds[0] = android.R.string.cancel;
        helperTextIds[1] = android.R.string.copy;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTextIds(helperTextIds);
        passwordEditText.addHelperTextId(android.R.string.copyUrl);
        passwordEditText.removeAllHelperTextIds(helperTextIds);
        passwordEditText.removeAllHelperTextIds(helperTextIds);
        assertEquals(1, passwordEditText.getHelperTexts().size());
        assertEquals(getContext().getText(android.R.string.copyUrl),
                passwordEditText.getHelperTexts().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all helper texts.
     */
    public final void testRemoveAllHelperTexts() {
        CharSequence helperText1 = "helperText1";
        CharSequence helperText2 = "helperText2";
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addHelperText(helperText1);
        passwordEditText.addHelperText(helperText2);
        passwordEditText.removeAllHelperTexts();
        assertTrue(passwordEditText.getHelperTexts().isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to add a helper text color.
     */
    public final void testAddHelperTextColor() {
        int helperTextColor = Color.BLACK;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addHelperTextColor(helperTextColor);
        passwordEditText.addHelperTextColor(helperTextColor);
        assertEquals(1, passwordEditText.getHelperTextColors().size());
        assertEquals(helperTextColor,
                (int) passwordEditText.getHelperTextColors().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to add a helper text color by its id.
     */
    public final void testAddHelperTextColorId() {
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addHelperTextColorId(android.R.color.black);
        passwordEditText.addHelperTextColorId(android.R.color.black);
        assertEquals(1, passwordEditText.getHelperTextColors().size());
        assertEquals(getContext().getResources().getColor(android.R.color.black),
                (int) passwordEditText.getHelperTextColors().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to add all helper text colors, which are
     * contained by a collection.
     */
    public final void testAddAllHelperTextColorsFromCollection() {
        int helperTextColor1 = Color.BLACK;
        int helperTextColor2 = Color.GRAY;
        Collection<Integer> helperTextColors1 = new LinkedList<>();
        helperTextColors1.add(helperTextColor1);
        helperTextColors1.add(helperTextColor2);
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTextColors(helperTextColors1);
        passwordEditText.addAllHelperTextColors(helperTextColors1);
        Collection<Integer> helperTextColors2 = passwordEditText.getHelperTextColors();
        assertEquals(helperTextColors1.size(), helperTextColors2.size());
        Iterator<Integer> iterator = helperTextColors2.iterator();
        assertEquals(helperTextColor1, (int) iterator.next());
        assertEquals(helperTextColor2, (int) iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to add all helper text colors by the ids,
     * which are contained by a collection.
     */
    public final void testAddAllHelperTextColorIdsFromCollection() {
        Collection<Integer> helperTextColors1 = new LinkedList<>();
        helperTextColors1.add(android.R.color.black);
        helperTextColors1.add(android.R.color.darker_gray);
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTextColorIds(helperTextColors1);
        passwordEditText.addAllHelperTextColorIds(helperTextColors1);
        Collection<Integer> helperTextColors2 = passwordEditText.getHelperTextColors();
        assertEquals(helperTextColors1.size(), helperTextColors2.size());
        Iterator<Integer> iterator = helperTextColors2.iterator();
        assertEquals(getContext().getResources().getColor(android.R.color.black),
                (int) iterator.next());
        assertEquals(getContext().getResources().getColor(android.R.color.darker_gray),
                (int) iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to add all helper text colors, which are
     * contained by an array.
     */
    public final void testAddAllHelperTextColorsFromArray() {
        int helperTextColor1 = Color.BLACK;
        int helperTextColor2 = Color.GRAY;
        int[] helperTextColors1 = new int[2];
        helperTextColors1[0] = helperTextColor1;
        helperTextColors1[1] = helperTextColor2;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTextColors(helperTextColors1);
        passwordEditText.addAllHelperTextColors(helperTextColors1);
        Collection<Integer> helperTextColors2 = passwordEditText.getHelperTextColors();
        assertEquals(helperTextColors1.length, helperTextColors2.size());
        Iterator<Integer> iterator = helperTextColors2.iterator();
        assertEquals(helperTextColor1, (int) iterator.next());
        assertEquals(helperTextColor2, (int) iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to add all helper text colors by the ids,
     * which are contained by an array.
     */
    public final void testAddAllHelperTextColorIdsFromArray() {
        int[] helperTextColors1 = new int[2];
        helperTextColors1[0] = android.R.color.black;
        helperTextColors1[1] = android.R.color.darker_gray;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTextColorIds(helperTextColors1);
        passwordEditText.addAllHelperTextColorIds(helperTextColors1);
        Collection<Integer> helperTextColors2 = passwordEditText.getHelperTextColors();
        assertEquals(helperTextColors1.length, helperTextColors2.size());
        Iterator<Integer> iterator = helperTextColors2.iterator();
        assertEquals(getContext().getResources().getColor(android.R.color.black),
                (int) iterator.next());
        assertEquals(getContext().getResources().getColor(android.R.color.darker_gray),
                (int) iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to remove a helper text color.
     */
    public final void testRemoveHelperTextColor() {
        int helperTextColor1 = Color.BLACK;
        int helperTextColor2 = Color.GRAY;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addHelperTextColor(helperTextColor1);
        passwordEditText.addHelperTextColor(helperTextColor2);
        passwordEditText.removeHelperTextColor(helperTextColor1);
        passwordEditText.removeHelperTextColor(helperTextColor1);
        assertEquals(1, passwordEditText.getHelperTextColors().size());
        assertEquals(helperTextColor2,
                (int) passwordEditText.getHelperTextColors().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove a helper text color id.
     */
    public final void testRemoveHelperTextColorId() {
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addHelperTextColorId(android.R.color.black);
        passwordEditText.addHelperTextColorId(android.R.color.darker_gray);
        passwordEditText.removeHelperTextColorId(android.R.color.black);
        passwordEditText.removeHelperTextColorId(android.R.color.black);
        assertEquals(1, passwordEditText.getHelperTextColors().size());
        assertEquals(getContext().getResources().getColor(android.R.color.darker_gray),
                (int) passwordEditText.getHelperTextColors().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all helper text colors, which
     * are contained by a collection.
     */
    public final void testRemoveHelperTextColorsFromCollection() {
        int helperTextColor1 = Color.BLACK;
        int helperTextColor2 = Color.GRAY;
        int helperTextColor3 = Color.WHITE;
        Collection<Integer> helperTextColors = new LinkedList<>();
        helperTextColors.add(helperTextColor1);
        helperTextColors.add(helperTextColor2);
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTextColors(helperTextColors);
        passwordEditText.addHelperTextColor(helperTextColor3);
        passwordEditText.removeAllHelperTextColors(helperTextColors);
        passwordEditText.removeAllHelperTextColors(helperTextColors);
        assertEquals(1, passwordEditText.getHelperTextColors().size());
        assertEquals(helperTextColor3,
                (int) passwordEditText.getHelperTextColors().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all helper text colors by the
     * ids, which are contained by a collection.
     */
    public final void testRemoveHelperTextColorIdsFromCollection() {
        Collection<Integer> helperTextColors = new LinkedList<>();
        helperTextColors.add(android.R.color.black);
        helperTextColors.add(android.R.color.darker_gray);
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTextColorIds(helperTextColors);
        passwordEditText.addAllHelperTextColorIds(android.R.color.white);
        passwordEditText.removeAllHelperTextColorIds(helperTextColors);
        passwordEditText.removeAllHelperTextColorIds(helperTextColors);
        Collection<Integer> helperTextColors2 = passwordEditText.getHelperTextColors();
        assertEquals(1, helperTextColors2.size());
        assertEquals(getContext().getResources().getColor(android.R.color.white),
                (int) passwordEditText.getHelperTextColors().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all helper text colors, which
     * are contained by an array.
     */
    public final void testRemoveHelperTextColorsFromArray() {
        int helperTextColor1 = Color.BLACK;
        int helperTextColor2 = Color.GRAY;
        int helperTextColor3 = Color.WHITE;
        int[] helperTextColors = new int[2];
        helperTextColors[0] = helperTextColor1;
        helperTextColors[1] = helperTextColor2;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTextColors(helperTextColors);
        passwordEditText.addHelperTextColor(helperTextColor3);
        passwordEditText.removeAllHelperTextColors(helperTextColors);
        passwordEditText.removeAllHelperTextColors(helperTextColors);
        assertEquals(1, passwordEditText.getHelperTextColors().size());
        assertEquals(helperTextColor3,
                (int) passwordEditText.getHelperTextColors().iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all helper text colors by the
     * ids, which are contained by an array.
     */
    public final void testRemoveHelperTextColorIdsFromArray() {
        int[] helperTextColors = new int[2];
        helperTextColors[0] = android.R.color.black;
        helperTextColors[1] = android.R.color.darker_gray;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addAllHelperTextColorIds(helperTextColors);
        passwordEditText.addHelperTextColorId(android.R.color.white);
        passwordEditText.removeAllHelperTextColorIds(helperTextColors);
        passwordEditText.removeAllHelperTextColorIds(helperTextColors);
        assertEquals(1, passwordEditText.getHelperTextColors().size());
        assertEquals(getContext().getResources().getColor(android.R.color.white),
                (int) passwordEditText.getHelperTextColors().iterator().next());
    }

    /**
     * Tests the functionality of the mehtod, which allows to remove all helper text colors.
     */
    public final void testRemoveAllHelperTextColors() {
        int helperTextColor1 = Color.BLACK;
        int helperTextColor2 = Color.GRAY;
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.addHelperTextColor(helperTextColor1);
        passwordEditText.addHelperTextColor(helperTextColor2);
        passwordEditText.removeAllHelperTextColors();
        assertTrue(passwordEditText.getHelperTextColors().isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to set the password verification prefix
     * and expects a string as a parameter.
     */
    public final void testSetPasswordVerificationPrefixWithStringParameter() {
        String prefix = "prefix";
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.setPasswordVerificationPrefix(prefix);
        assertEquals(prefix, passwordEditText.getPasswordVerificationPrefix());
    }

    /**
     * Tests the functionality of the method, which allows to set the password verification prefix
     * and expects a resource id as a parameter.
     */
    public final void testSetPasswordVerificationPrefixWithResourceIdParameter() {
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.setPasswordVerificationPrefix(android.R.string.cancel);
        assertEquals(getContext().getString(android.R.string.cancel),
                passwordEditText.getPasswordVerificationPrefix());
    }

    /**
     * Tests, if the helper text is set correctly, when verifying the password strength.
     */
    public final void testVerifyPasswordStrength() {
        String prefix = "prefix";
        CharSequence helperText1 = "helperText1";
        CharSequence helperText2 = "helperText2";
        PasswordEditText passwordEditText = new PasswordEditText(getContext());
        passwordEditText.setPasswordVerificationPrefix(prefix);
        passwordEditText
                .addAllConstraints(Constraints.containsLetter(), Constraints.containsNumber());
        passwordEditText.addAllHelperTexts(helperText1, helperText2);
        passwordEditText.setText("abc");
        assertEquals(prefix + ": " + helperText1, passwordEditText.getHelperText().toString());
        passwordEditText.setText("abc123");
        assertEquals(prefix + ": " + helperText2, passwordEditText.getHelperText().toString());
    }

}
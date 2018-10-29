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
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import androidx.core.content.ContextCompat;
import android.test.AndroidTestCase;
import android.util.AttributeSet;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import de.mrapp.android.validation.AbstractValidateableView.SavedState;

/**
 * Tests the functionality of the class {@link AbstractValidateableView}.
 *
 * @author Michael Rapp
 */
public class AbstractValidateableViewTest extends AndroidTestCase {

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a context as a
     * parameter.
     */
    public final void testConstructorWithContextParameter() {
        Context context = getContext();
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(context);
        assertEquals(context, abstractValidateableView.getContext());
        assertTrue(abstractValidateableView.getValidators().isEmpty());
        assertNull(abstractValidateableView.getHelperText());
        assertEquals(context.getResources().getColor(R.color.default_error_color),
                abstractValidateableView.getErrorColor());
        assertNull(abstractValidateableView.getError());
        assertTrue(abstractValidateableView.isValidatedOnValueChange());
        assertTrue(abstractValidateableView.isValidatedOnFocusLost());
    }

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a context and an
     * attribute set as parameters.
     */
    public final void testConstructorWithContextAndAttributeSetParameters() {
        Context context = getContext();
        XmlPullParser xmlPullParser =
                context.getResources().getXml(R.xml.abstract_validateable_view_implementation);
        AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(context, attributeSet);
        assertEquals(context, abstractValidateableView.getContext());
        assertTrue(abstractValidateableView.getValidators().isEmpty());
        assertNull(abstractValidateableView.getHelperText());
        assertEquals(context.getResources().getColor(R.color.default_error_color),
                abstractValidateableView.getErrorColor());
        assertNull(abstractValidateableView.getError());
        assertTrue(abstractValidateableView.isValidatedOnValueChange());
        assertTrue(abstractValidateableView.isValidatedOnFocusLost());
    }

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a context, an
     * attribute set and a default style as parameters.
     */
    public final void testConstructorWithContextAttributeSetAndDefaultStyleParameters() {
        Context context = getContext();
        int defaultStyle = 0;
        XmlPullParser xmlPullParser =
                context.getResources().getXml(R.xml.abstract_validateable_view_implementation);
        AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(context, attributeSet, defaultStyle);
        assertEquals(context, abstractValidateableView.getContext());
        assertTrue(abstractValidateableView.getValidators().isEmpty());
        assertNull(abstractValidateableView.getHelperText());
        assertEquals(context.getResources().getColor(R.color.default_error_color),
                abstractValidateableView.getErrorColor());
        assertNull(abstractValidateableView.getError());
        assertTrue(abstractValidateableView.isValidatedOnValueChange());
        assertTrue(abstractValidateableView.isValidatedOnFocusLost());
    }

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a context, an
     * attribute set, a default style and a default style attribute as parameters.
     */
    public final void testConstructorWithContextAttributeSetAndDefaultStyleAndDefaultStyleAttributeParameters() {
        Context context = getContext();
        int defaultStyle = 0;
        int defaultStyleAttribute = 0;
        XmlPullParser xmlPullParser =
                context.getResources().getXml(R.xml.abstract_validateable_view_implementation);
        AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(context, attributeSet, defaultStyle,
                        defaultStyleAttribute);
        assertEquals(context, abstractValidateableView.getContext());
        assertTrue(abstractValidateableView.getValidators().isEmpty());
        assertNull(abstractValidateableView.getHelperText());
        assertEquals(context.getResources().getColor(R.color.default_error_color),
                abstractValidateableView.getErrorColor());
        assertNull(abstractValidateableView.getError());
        assertTrue(abstractValidateableView.isValidatedOnValueChange());
        assertTrue(abstractValidateableView.isValidatedOnFocusLost());
    }

    /**
     * Tests the functionality of the method, which allows to add a validator.
     */
    public final void testAddValidator() {
        Validator<CharSequence> validator = Validators.notEmpty("foo");
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.addValidator(validator);
        abstractValidateableView.addValidator(validator);
        Collection<Validator<CharSequence>> validators = abstractValidateableView.getValidators();
        assertEquals(1, validators.size());
        assertEquals(validator, validators.iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to add all validators, which are
     * contained by a collection.
     */
    public final void testAddAllValidatorsFromCollection() {
        Validator<CharSequence> validator1 = Validators.notEmpty("foo");
        Validator<CharSequence> validator2 = Validators.noWhitespace("bar");
        Collection<Validator<CharSequence>> validators1 = new LinkedList<>();
        validators1.add(validator1);
        validators1.add(validator2);
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.addAllValidators(validators1);
        abstractValidateableView.addAllValidators(validators1);
        Collection<Validator<CharSequence>> validators2 = abstractValidateableView.getValidators();
        assertEquals(validators1.size(), validators2.size());
        Iterator<Validator<CharSequence>> iterator = validators2.iterator();
        assertEquals(validator1, iterator.next());
        assertEquals(validator2, iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to add all validators, which are
     * contained by an array.
     */
    @SuppressWarnings("unchecked")
    public final void testAddAllValidatorsFromArray() {
        Validator<CharSequence> validator1 = Validators.notEmpty("foo");
        Validator<CharSequence> validator2 = Validators.noWhitespace("bar");
        Validator<CharSequence>[] validators1 = new Validator[2];
        validators1[0] = validator1;
        validators1[1] = validator2;
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.addAllValidators(validators1);
        abstractValidateableView.addAllValidators(validators1);
        Collection<Validator<CharSequence>> validators2 = abstractValidateableView.getValidators();
        assertEquals(validators1.length, validators2.size());
        Iterator<Validator<CharSequence>> iterator = validators2.iterator();
        assertEquals(validator1, iterator.next());
        assertEquals(validator2, iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to remove a validator.
     */
    public final void testRemoveValidator() {
        Validator<CharSequence> validator1 = Validators.notEmpty("foo");
        Validator<CharSequence> validator2 = Validators.noWhitespace("bar");
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.addValidator(validator1);
        abstractValidateableView.addValidator(validator2);
        abstractValidateableView.removeValidator(validator1);
        abstractValidateableView.removeValidator(validator1);
        Collection<Validator<CharSequence>> validators = abstractValidateableView.getValidators();
        assertEquals(1, validators.size());
        assertEquals(validator2, validators.iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all validators, which are
     * contained by a collection.
     */
    public final void testRemoveAllValidatorsFromCollection() {
        Validator<CharSequence> validator1 = Validators.notEmpty("foo");
        Validator<CharSequence> validator2 = Validators.noWhitespace("bar");
        Validator<CharSequence> validator3 = Validators.number("foo2");
        Collection<Validator<CharSequence>> validators1 = new LinkedList<>();
        validators1.add(validator1);
        validators1.add(validator2);
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.addAllValidators(validators1);
        abstractValidateableView.addValidator(validator3);
        abstractValidateableView.removeAllValidators(validators1);
        abstractValidateableView.removeAllValidators(validators1);
        Collection<Validator<CharSequence>> validators2 = abstractValidateableView.getValidators();
        assertEquals(1, validators2.size());
        assertEquals(validator3, validators2.iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all validators, which are
     * contained by an array.
     */
    @SuppressWarnings("unchecked")
    public final void testRemoveAllValidatorsFromArray() {
        Validator<CharSequence> validator1 = Validators.notEmpty("foo");
        Validator<CharSequence> validator2 = Validators.noWhitespace("bar");
        Validator<CharSequence> validator3 = Validators.number("foo2");
        Validator<CharSequence>[] validators1 = new Validator[2];
        validators1[0] = validator1;
        validators1[1] = validator2;
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.addAllValidators(validators1);
        abstractValidateableView.addValidator(validator3);
        abstractValidateableView.removeAllValidators(validators1);
        abstractValidateableView.removeAllValidators(validators1);
        Collection<Validator<CharSequence>> validators2 = abstractValidateableView.getValidators();
        assertEquals(1, validators2.size());
        assertEquals(validator3, validators2.iterator().next());
    }

    /**
     * Tests the functionality of the method, which allows to remove all validators.
     */
    public final void testRemoveAllValidators() {
        Validator<CharSequence> validator1 = Validators.notEmpty("foo");
        Validator<CharSequence> validator2 = Validators.noWhitespace("bar");
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.addValidator(validator1);
        abstractValidateableView.addValidator(validator2);
        abstractValidateableView.removeAllValidators();
        assertTrue(abstractValidateableView.getValidators().isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to set the helper text.
     */
    public final void testSetHelperText() {
        CharSequence helperText = "helperText";
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.setHelperText(helperText);
        assertEquals(helperText, abstractValidateableView.getHelperText());
    }

    /**
     * Tests the functionality of the method, which allows to set the error color.
     */
    public final void testSetErrorColor() {
        int errorColor = Color.RED;
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.setErrorColor(errorColor);
        assertEquals(errorColor, abstractValidateableView.getErrorColor());
    }

    /**
     * Tests the functionality of the method, which allows to set the error and expects a char
     * sequence as a parameter.
     */
    public final void testSetErrorWithCharSequenceParameter() {
        CharSequence error = "error";
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.setError(error);
        assertEquals(error, abstractValidateableView.getError());
        assertTrue(abstractValidateableView.isActivated());
        assertTrue(abstractValidateableView.getView().isActivated());
        abstractValidateableView.setError(null);
        assertFalse(abstractValidateableView.isActivated());
        assertFalse(abstractValidateableView.getView().isActivated());
    }

    /**
     * Tests the functionality of the method, which allows to set the error and expects a char
     * sequence and a drawable as parameters.
     */
    public final void testSetErrorWithCharSequenceAndDrawableParameter() {
        CharSequence error = "error";
        Drawable icon = ContextCompat.getDrawable(getContext(), android.R.drawable.ic_dialog_alert);
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.setError(error, icon);
        assertEquals(error, abstractValidateableView.getError());
        assertTrue(abstractValidateableView.isActivated());
        assertTrue(abstractValidateableView.getView().isActivated());
    }

    /**
     * Tests the functionality of the setEnabled-method.
     */
    public final void testSetEnabled() {
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.setError("foo");
        abstractValidateableView.setEnabled(false);
        assertFalse(abstractValidateableView.getView().isEnabled());
        assertNull(abstractValidateableView.getError());
    }

    /**
     * Tests the functionality of the method, which allows to validate the view's value, if the
     * validation succeeds.
     */
    public final void testValidateWhenValidationSucceeds() {
        ValidationListenerImplementation validationListener1 =
                new ValidationListenerImplementation();
        ValidationListenerImplementation validationListener2 =
                new ValidationListenerImplementation();
        CharSequence errorMessage = "errorMessage";
        Validator<CharSequence> validator = Validators.notEmpty(errorMessage);
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.addValidationListener(validationListener1);
        abstractValidateableView.addValidationListener(validationListener1);
        abstractValidateableView.addValidationListener(validationListener2);
        abstractValidateableView.removeValidationListener(validationListener2);
        abstractValidateableView.addValidator(validator);
        abstractValidateableView.getView().setText("text");
        assertTrue(abstractValidateableView.validate());
        assertNull(abstractValidateableView.getError());
        assertTrue(validationListener1.hasOnValidationSuccessBeenCalled());
        assertFalse(validationListener2.hasOnValidationSuccessBeenCalled());
        assertFalse(abstractValidateableView.isActivated());
        assertFalse(abstractValidateableView.getView().isActivated());
    }

    /**
     * Tests the functionality of the method, which allows to validate the view's value, if the
     * validation fails.
     */
    public final void testValidateWhenValidationFails() {
        ValidationListenerImplementation validationListener1 =
                new ValidationListenerImplementation();
        ValidationListenerImplementation validationListener2 =
                new ValidationListenerImplementation();
        CharSequence errorMessage = "errorMessage";
        Validator<CharSequence> validator = Validators.notEmpty(errorMessage);
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.addValidationListener(validationListener1);
        abstractValidateableView.addValidationListener(validationListener1);
        abstractValidateableView.addValidationListener(validationListener2);
        abstractValidateableView.removeValidationListener(validationListener2);
        abstractValidateableView.addValidator(validator);
        abstractValidateableView.getView().setText("");
        assertFalse(abstractValidateableView.validate());
        assertEquals(errorMessage, abstractValidateableView.getError());
        assertTrue(validationListener1.hasOnValidationFailureBeenCalled());
        assertFalse(validationListener2.hasOnValidationFailureBeenCalled());
        assertTrue(abstractValidateableView.isActivated());
        assertTrue(abstractValidateableView.getView().isActivated());
    }

    /**
     * Tests the functionality of the method, which allows to set, whether the view's value should
     * be validated on value changes, or not.
     */
    public final void testValidateOnValueChange() {
        boolean validateOnValueChange = false;
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.validateOnValueChange(validateOnValueChange);
        assertEquals(validateOnValueChange, abstractValidateableView.isValidatedOnValueChange());
    }

    /**
     * Tests the functionality of the method, which allows to set, whether the view's value should
     * be validated when the view loses its focus, or not.
     */
    public final void testValidateOnFocusLost() {
        boolean validateOnFocusLost = false;
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.validateOnFocusLost(validateOnFocusLost);
        assertEquals(validateOnFocusLost, abstractValidateableView.isValidatedOnFocusLost());
    }

    /**
     * Tests the functionality of the onSaveInstanceState-method.
     */
    public final void testOnSaveInstanceState() {
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.addValidator(Validators.notEmpty("foo"));
        abstractValidateableView.validateOnValueChange(false);
        abstractValidateableView.validateOnFocusLost(false);
        abstractValidateableView.validate();
        SavedState savedState = (SavedState) abstractValidateableView.onSaveInstanceState();
        assertNotNull(savedState);
        assertFalse(savedState.validateOnValueChange);
        assertFalse(savedState.validateOnFocusLost);
        assertTrue(savedState.validated);
    }

    /**
     * Tests the functionality of the onRestoreInstanceState-method.
     */
    public final void testOnRestoreInstanceState() {
        CharSequence errorMessage = "errorMessage";
        Validator<CharSequence> validator = Validators.notEmpty(errorMessage);
        AbstractValidateableViewImplementation abstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        abstractValidateableView.addValidator(validator);
        abstractValidateableView.validateOnValueChange(false);
        abstractValidateableView.validateOnFocusLost(false);
        abstractValidateableView.validate();
        Parcelable parcelable = abstractValidateableView.onSaveInstanceState();
        AbstractValidateableViewImplementation restoredAbstractValidateableView =
                new AbstractValidateableViewImplementation(getContext());
        restoredAbstractValidateableView.addValidator(validator);
        restoredAbstractValidateableView.onRestoreInstanceState(parcelable);
        assertFalse(restoredAbstractValidateableView.isValidatedOnValueChange());
        assertFalse(restoredAbstractValidateableView.isValidatedOnFocusLost());
        assertEquals(errorMessage, restoredAbstractValidateableView.getError());
    }

}
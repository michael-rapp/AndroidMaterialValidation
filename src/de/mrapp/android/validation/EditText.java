/*
 * AndroidMaterialValidation Copyright 2015 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.validation;

import static de.mrapp.android.validation.util.Condition.ensureAtLeast;

import java.util.Collection;
import java.util.LinkedList;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * A view, which allows to enter text. The text may be validated according to
 * the pattern, which is suggested by the Material Design guidelines.
 * 
 * @author Michael Rapp
 *
 * @since 1.0.0
 */
public class EditText extends
		AbstractValidateableView<android.widget.EditText, CharSequence>
		implements TextWatcher, ValidationListener<CharSequence> {

	/**
	 * The maximum number of characters, the edit text is allowed to contain.
	 */
	private int maxNumberOfCharacters;

	/**
	 * Initializes the view.
	 * 
	 * @param attributeSet
	 *            The attribute set, the attributes should be obtained from, as
	 *            an instance of the type {@link AttributeSet}
	 */
	private void initialize(final AttributeSet attributeSet) {
		obtainStyledAttributes(attributeSet);
		setEditTextColor(getAccentColor());
		addValidationListener(this);
	}

	/**
	 * Obtains all attributes from a specific attribute set.
	 * 
	 * @param attributeSet
	 *            The attribute set, the attributes should be obtained from, as
	 *            an instance of the type {@link AttributeSet}
	 */
	private void obtainStyledAttributes(final AttributeSet attributeSet) {
		TypedArray typedArray = getContext().obtainStyledAttributes(
				attributeSet, R.styleable.EditText);
		try {
			obtainMaxNumberOfCharacters(typedArray);
		} finally {
			typedArray.recycle();
		}
	}

	/**
	 * Obtains the maximum number of characters, the edit text should be allowed
	 * to contain, from a specific typed array.
	 * 
	 * @param typedArray
	 *            The typed array, the maximum number of characters, the edit
	 *            text should be allowed to contain, should be obtained from, as
	 *            an instance of the class {@link TypedArray}
	 */
	private void obtainMaxNumberOfCharacters(final TypedArray typedArray) {
		setMaxNumberOfCharacters(typedArray.getInt(
				R.styleable.EditText_maxNumberOfCharacters, -1));
	}

	/**
	 * Returns the color of the theme attribute
	 * <code>android.R.attr.colorAccent</code>.
	 * 
	 * @return The color of the theme attribute
	 *         <code>android.R.attr.colorAccent</code>
	 */
	private int getAccentColor() {
		TypedValue typedValue = new TypedValue();
		TypedArray typedArray = getContext().obtainStyledAttributes(
				typedValue.data, new int[] { R.attr.colorAccent });
		int color = typedArray.getColor(0, 0);
		typedArray.recycle();
		return color;
	}

	/**
	 * Sets the color of the edit text, which allows to enter a text.
	 * 
	 * @param color
	 *            The color, which should be set, as an {@link Integer} value
	 */
	private void setEditTextColor(final int color) {
		getView().getBackground().setColorFilter(color,
				PorterDuff.Mode.SRC_ATOP);
	}

	/**
	 * Returns the message, which shows how many characters, in relation to the
	 * maximum number of characters, the edit text is allowed to contain, have
	 * already been entered.
	 * 
	 * @return The message, which shows how many characters, in relation to the
	 *         maximum number of characters, the edit text is allowed to
	 *         contain, have already been typed, as an instance of the type
	 *         {@link CharSequence}
	 */
	private CharSequence getMaxNumberOfCharactersMessage() {
		int maxLength = getMaxNumberOfCharacters();
		int currentLength = getView().length();
		return String.format(
				getResources().getString(
						R.string.edit_text_size_violation_error_message),
				currentLength, maxLength);
	}

	@Override
	protected final Collection<Validator<CharSequence>> onGetRightErrorMessage() {
		CharSequence errorMessage = getMaxNumberOfCharactersMessage();

		if (getMaxNumberOfCharacters() != -1) {
			Validator<CharSequence> validator = Validators.maxLength(
					errorMessage, getMaxNumberOfCharacters());

			if (!validator.validate(getValue())) {
				Collection<Validator<CharSequence>> result = new LinkedList<>();
				result.add(validator);
				return result;
			}
		}

		return null;
	}

	@Override
	protected final android.widget.EditText createView() {
		android.widget.EditText editText = new android.widget.EditText(
				getContext());
		editText.addTextChangedListener(this);
		return editText;
	}

	@Override
	protected final CharSequence getValue() {
		return getView().getText();
	}

	/**
	 * Creates a new view, which allows to enter text.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 */
	public EditText(final Context context) {
		super(context);
		initialize(null);
	}

	/**
	 * Creates a new view, which allows to enter text.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 * @param attributeSet
	 *            The attributes of the XML tag that is inflating the view, as
	 *            an instance of the type {@link AttributeSet}
	 */
	public EditText(final Context context, final AttributeSet attributeSet) {
		super(context, attributeSet);
		initialize(attributeSet);
	}

	/**
	 * Creates a new view, which allows to enter text.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 * @param attributeSet
	 *            The attributes of the XML tag that is inflating the view, as
	 *            an instance of the type {@link AttributeSet}
	 * @param defaultStyle
	 *            The default style to apply to this preference. If 0, no style
	 *            will be applied (beyond what is included in the theme). This
	 *            may either be an attribute resource, whose value will be
	 *            retrieved from the current theme, or an explicit style
	 *            resource
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public EditText(final Context context, final AttributeSet attributeSet,
			final int defaultStyle) {
		super(context, attributeSet, defaultStyle);
		initialize(attributeSet);
	}

	/**
	 * Creates a new view, which allows to enter text.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 * @param attributeSet
	 *            The attributes of the XML tag that is inflating the view, as
	 *            an instance of the type {@link AttributeSet}
	 * @param defaultStyle
	 *            The default style to apply to this preference. If 0, no style
	 *            will be applied (beyond what is included in the theme). This
	 *            may either be an attribute resource, whose value will be
	 *            retrieved from the current theme, or an explicit style
	 *            resource
	 * @param defaultStyleResource
	 *            A resource identifier of a style resource that supplies
	 *            default values for the preference, used only if the default
	 *            style is 0 or can not be found in the theme. Can be 0 to not
	 *            look for defaults
	 */
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public EditText(final Context context, final AttributeSet attributeSet,
			final int defaultStyle, final int defaultStyleResource) {
		super(context, attributeSet, defaultStyle, defaultStyleResource);
		initialize(attributeSet);
	}

	/**
	 * Returns the maximum number of characters, the edit text is allowed to
	 * contain.
	 * 
	 * @return The maximum number of characters, the edit text is allowed to
	 *         contain, as an {@link Integer} value or -1, if the number of
	 *         characters is not restricted
	 */
	public final int getMaxNumberOfCharacters() {
		return maxNumberOfCharacters;
	}

	/**
	 * Sets the maximum number of characters, the edit text should be allowed to
	 * contain.
	 * 
	 * @param maxNumberOfCharacters
	 *            The maximum number of characters, which should be set, as an
	 *            {@link Integer} value. The maximum number of characters must
	 *            be at least 1 or -1, if the number of characters should not be
	 *            restricted
	 */
	public final void setMaxNumberOfCharacters(final int maxNumberOfCharacters) {
		if (maxNumberOfCharacters != -1) {
			ensureAtLeast(maxNumberOfCharacters, 1,
					"The maximum number of characters must be at least 1");
			setRightMessage(getMaxNumberOfCharactersMessage(), getView()
					.length() > maxNumberOfCharacters);
		} else {
			setRightMessage(null);
		}

		this.maxNumberOfCharacters = maxNumberOfCharacters;
	}

	@Override
	public final void beforeTextChanged(final CharSequence s, final int start,
			final int count, final int after) {
		return;
	}

	@Override
	public final void onTextChanged(final CharSequence s, final int start,
			final int before, final int count) {
		return;
	}

	@Override
	public final void afterTextChanged(final Editable s) {
		if (isValidatedOnValueChange()) {
			validate();
			setRightMessage(getMaxNumberOfCharactersMessage(), getView()
					.length() > getMaxNumberOfCharacters());
		}

	}

	@Override
	public final void onValidationSuccess(final Validateable<CharSequence> view) {
		setEditTextColor(getAccentColor());
	}

	@Override
	public final void onValidationFailure(
			final Validateable<CharSequence> view,
			final Validator<CharSequence> validator) {
		setEditTextColor(getResources().getColor(R.color.error_message_color));
	}

}
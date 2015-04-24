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

import static de.mrapp.android.validation.util.Condition.ensureNotNull;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * An abstract base class for all views, whose value should be able to be
 * validated according to the pattern, which is suggested by the Material Design
 * guidelines.
 * 
 * @param <ViewType>
 *            The type of the view, whose value should be able to be validated
 * @param <ValueType>
 *            The type of the values, which should be validated
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractValidateableView<ViewType extends View, ValueType>
		extends LinearLayout implements Validateable<ValueType>,
		OnFocusChangeListener {

	/**
	 * The view, whose value should be able to be validated.
	 */
	private ViewType view;

	/**
	 * The text view, which may be used to show messages at the left edge of the
	 * view.
	 */
	private TextView leftMessage;

	/**
	 * The text view, which may be used to show messages at the right edge of
	 * the view.
	 */
	private TextView rightMessage;

	/**
	 * The default color of a text view.
	 */
	private int defaultColor;

	/**
	 * The helper text, which is shown, when no validation errors are currently
	 * shown at the left edge of the view.
	 */
	private CharSequence helperText;

	/**
	 * A set, which contains the validators, which should be used for
	 * validation.
	 */
	private Set<Validator<ValueType>> validators;

	/**
	 * True, if the view's value is automatically validated, when its value has
	 * been changed, false otherwise.
	 */
	private boolean validateOnValueChange;

	/**
	 * True, if the view's value is automatically validated, when the view loses
	 * its focus, false otherwise.
	 */
	private boolean validateOnFocusLost;

	/**
	 * A set, which contains the listeners, which should be notified, when the
	 * view has been validated.
	 */
	private Set<ValidationListener<ValueType>> listeners;

	/**
	 * Initializes the view.
	 * 
	 * @param attributeSet
	 *            The attribute set, the attributes should be obtained from, as
	 *            an instance of the type {@link AttributeSet}
	 */
	private void initialize(final AttributeSet attributeSet) {
		validators = new LinkedHashSet<Validator<ValueType>>();
		listeners = new LinkedHashSet<ValidationListener<ValueType>>();
		setOrientation(VERTICAL);
		inflateView();
		inflateErrorMessageTextViews();
		obtainStyledAttributes(attributeSet);
		setLeftMessage(null);
		setRightMessage(null);
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
				attributeSet, R.styleable.AbstractValidateableView);
		try {
			obtainHelperText(typedArray);
			obtainValidateOnValueChange(typedArray);
			obtainValidateOnFocusLost(typedArray);
		} finally {
			typedArray.recycle();
		}
	}

	/**
	 * Obtains the helper text from a specific typed array.
	 * 
	 * @param typedArray
	 *            The typed array, the helper text should be obtained from, as
	 *            an instance of the class {@link TypedArray}
	 */
	private void obtainHelperText(final TypedArray typedArray) {
		setHelperText(typedArray
				.getString(R.styleable.AbstractValidateableView_helperText));
	}

	/**
	 * Obtains, whether the value of the view should be validated, when its
	 * value has been changed, or not, from a specific typed array.
	 * 
	 * @param typedArray
	 *            The typed array, it should be obtained from, whether the value
	 *            of the view should be validated, when its value has been
	 *            changed, or not, as an instance of the class
	 *            {@link TypedArray}
	 */
	private void obtainValidateOnValueChange(final TypedArray typedArray) {
		validateOnValueChange(typedArray.getBoolean(
				R.styleable.AbstractValidateableView_validateOnValueChange,
				true));
	}

	/**
	 * Obtains, whether the value of the view should be validated, when the view
	 * loses its focus, or not, from a specific typed array.
	 * 
	 * @param typedArray
	 *            The typed array, it should be obtained from, whether the value
	 *            of the view should be validated, when the view loses its
	 *            focus, or not, as an instance of the class {@link TypedArray}
	 */
	private void obtainValidateOnFocusLost(final TypedArray typedArray) {
		validateOnFocusLost(typedArray
				.getBoolean(
						R.styleable.AbstractValidateableView_validateOnFocusLost,
						false));
	}

	/**
	 * Inflates the view, whose value should be able to be validated.
	 */
	private void inflateView() {
		view = createView();
		view.setOnFocusChangeListener(this);
		addView(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	}

	/**
	 * Inflates the text views, which are used to show validation errors.
	 */
	private void inflateErrorMessageTextViews() {
		View parent = View.inflate(getContext(), R.layout.error_messages, null);
		addView(parent, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		leftMessage = (TextView) parent.findViewById(R.id.left_error_message);
		rightMessage = (TextView) parent.findViewById(R.id.right_error_message);
		defaultColor = leftMessage.getTextColors().getDefaultColor();

	}

	/**
	 * Notifies all registered listeners, that a validation succeeded.
	 */
	private void notifyOnValidationSuccess() {
		for (ValidationListener<ValueType> listener : listeners) {
			listener.onValidationSuccess(this);
		}
	}

	/**
	 * Notifies all registered listeners, that a validation failed.
	 * 
	 * @param validator
	 *            The validation, which failed, as an instance of the type
	 *            {@link Validator}
	 */
	private void notifyOnValidationFailure(final Validator<ValueType> validator) {
		for (ValidationListener<ValueType> listener : listeners) {
			listener.onValidationFailure(this, validator);
		}
	}

	/**
	 * Validates the current value of the view and returns the error message,
	 * which should be shown at the left edge of the view, if a validation
	 * fails.
	 * 
	 * @return The error message of the validation, which failed or null, if the
	 *         validation succeeded
	 */
	private CharSequence getLeftErrorMessage() {
		CharSequence result = null;
		Collection<Validator<ValueType>> subValidators = onGetLeftErrorMessage();

		if (subValidators != null) {
			for (Validator<ValueType> validator : subValidators) {
				notifyOnValidationFailure(validator);

				if (result == null) {
					result = validator.getErrorMessage();
				}
			}
		}

		for (Validator<ValueType> validator : validators) {
			if (!validator.validate(getValue())) {
				notifyOnValidationFailure(validator);

				if (result == null) {
					result = validator.getErrorMessage();
				}
			}
		}

		return result;
	}

	/**
	 * Validates the current value of the view and returns the error message,
	 * which should be shown at the right edge of the view, if a validation
	 * fails.
	 * 
	 * @return The error message of the validation, which failed or null, if the
	 *         validation succeeded
	 */
	private CharSequence getRightErrorMessage() {
		CharSequence result = null;
		Collection<Validator<ValueType>> subValidators = onGetRightErrorMessage();

		if (subValidators != null) {
			for (Validator<ValueType> validator : subValidators) {
				notifyOnValidationFailure(validator);

				if (result == null) {
					result = validator.getErrorMessage();
				}
			}
		}

		return result;
	}

	/**
	 * Returns the view, whose value should be able to be validated.
	 * 
	 * @return The view, whose value should be able to be validated, as an
	 *         instance of the generic type ViewType
	 */
	protected final ViewType getView() {
		return view;
	}

	/**
	 * Shows a specific message, which is marked as an error, at the left edge
	 * of the view.
	 * 
	 * @param message
	 *            The message, which should be shown, as an instance of the type
	 *            {@link CharSequence} or null, if no message should be shown
	 */
	protected final void setLeftMessage(final CharSequence message) {
		setLeftMessage(message, true);
	}

	/**
	 * Shows a specific message at the left edge of the view.
	 * 
	 * @param message
	 *            The message, which should be shown, as an instance of the type
	 *            {@link CharSequence} or null, if no message should be shown
	 * @param error
	 *            True, if the message should be highlighted as an error, false
	 *            otherwise
	 */
	protected final void setLeftMessage(final CharSequence message,
			final boolean error) {
		if (message != null) {
			leftMessage.setText(message);
			leftMessage.setTextColor(error ? getResources().getColor(
					R.color.error_message_color) : defaultColor);
			leftMessage.setVisibility(View.VISIBLE);
		} else if (getHelperText() != null) {
			setLeftMessage(getHelperText(), false);
		} else {
			leftMessage.setVisibility(View.GONE);
		}
	}

	/**
	 * Shows a specific message, which is highlighted as an error, at the right
	 * edge of the view.
	 * 
	 * @param message
	 *            The message, which should be shown, as an instance of the type
	 *            {@link CharSequence} or null, if no message should be shown
	 */
	protected final void setRightMessage(final CharSequence message) {
		setRightMessage(message, true);
	}

	/**
	 * Shows a specific message at the right edge of the view.
	 * 
	 * @param message
	 *            The message, which should be shown, as an instance of the type
	 *            {@link CharSequence} or null, if no message should be shown
	 * @param error
	 *            True, if the message should be highlighted as an error, false
	 *            otherwise
	 */
	protected final void setRightMessage(final CharSequence message,
			final boolean error) {
		if (message != null) {
			rightMessage.setVisibility(View.VISIBLE);
			rightMessage.setText(message);
			rightMessage.setTextColor(error ? getResources().getColor(
					R.color.error_message_color) : defaultColor);
		} else {
			rightMessage.setVisibility(View.GONE);
		}
	}

	/**
	 * The method, which is invoked in order to validate the current value of
	 * the view and to retrieve the error message, which should be shown at the
	 * left edge of the view, if a validation fails. This method may be
	 * overridden by subclasses in order to perform internal validations.
	 * 
	 * @return A collection, which contains the validators, which failed or
	 *         null, if the validation succeeded, as an instance of the type
	 *         {@link Collection}
	 */
	protected Collection<Validator<ValueType>> onGetLeftErrorMessage() {
		return null;
	}

	/**
	 * The method, which is invoked in order to validate the current value of
	 * the view and to retrieve the error message, which should be shown at the
	 * right edge of the view, if a validation fails. This method may be
	 * overridden by subclasses in order to perform internal validations.
	 * 
	 * @return TA collection, which contains the validators, which failed or
	 *         null, if the validation succeeded, as an instance of the type
	 *         {@link Collection}
	 */
	protected Collection<Validator<ValueType>> onGetRightErrorMessage() {
		return null;
	}

	/**
	 * The method, which is invoked in order to create the view, whose value
	 * should be able to be validated.
	 * 
	 * @return The view, which has been created, as an instance of the generic
	 *         type ViewType
	 */
	protected abstract ViewType createView();

	/**
	 * The method, which is invoked in order to retrieve the current value of
	 * the view.
	 * 
	 * @return The current value of the view as an instance of the generic type
	 *         Type
	 */
	protected abstract ValueType getValue();

	/**
	 * Creates a new view, which allows to enter text.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 */
	public AbstractValidateableView(final Context context) {
		super(context);
		initialize(null);
	}

	/**
	 * Creates a new view, whose value should be able to be validated according
	 * to the pattern, which is suggested by the Material Design guidelines.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 * @param attributeSet
	 *            The attributes of the XML tag that is inflating the view, as
	 *            an instance of the type {@link AttributeSet}
	 */
	public AbstractValidateableView(final Context context,
			final AttributeSet attributeSet) {
		super(context, attributeSet);
		initialize(attributeSet);
	}

	/**
	 * Creates a new view, whose value should be able to be validated according
	 * to the pattern, which is suggested by the Material Design guidelines.
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
	public AbstractValidateableView(final Context context,
			final AttributeSet attributeSet, final int defaultStyle) {
		super(context, attributeSet, defaultStyle);
		initialize(attributeSet);
	}

	/**
	 * Creates a new view, whose value should be able to be validated according
	 * to the pattern, which is suggested by the Material Design guidelines.
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
	public AbstractValidateableView(final Context context,
			final AttributeSet attributeSet, final int defaultStyle,
			final int defaultStyleResource) {
		super(context, attributeSet, defaultStyle, defaultStyleResource);
		initialize(attributeSet);
	}

	@Override
	public final void addValidator(final Validator<ValueType> validator) {
		ensureNotNull(validator, "The validator may not be null");
		validators.add(validator);
	}

	/**
	 * Returns the helper text, which is shown, when no validation error is
	 * currently shown.
	 * 
	 * @return The helper text, which is shown, when no validation error is
	 *         currently shown, as an instance of the type {@link CharSequence}
	 *         or null, if no helper text is shown
	 */
	public final CharSequence getHelperText() {
		return helperText;
	}

	/**
	 * Sets the helper text, which should be shown, when no validation error is
	 * currently shown.
	 * 
	 * @param helperText
	 *            The helper text, which should be set, as an instance of the
	 *            type {@link CharSequence} or null, if no helper text should be
	 *            shown
	 */
	public final void setHelperText(final CharSequence helperText) {
		this.helperText = helperText;
	}

	/**
	 * Sets the helper text, which should be shown, when no validation error is
	 * currently shown.
	 * 
	 * @param resourceId
	 *            The resource ID of the string resource, which contains the
	 *            helper text, which should be set, as an {@link Integer} value.
	 *            The resource ID must correspond to a valid string resource
	 */
	public final void setHelperText(final int resourceId) {
		setHelperText(getContext().getText(resourceId));
	}

	@Override
	public final void removeValidator(final Validator<ValueType> validator) {
		ensureNotNull(validator, "The validator may not be null");
		validators.remove(validator);
	}

	@Override
	public final boolean validate() {
		CharSequence leftErrorMessage = getLeftErrorMessage();
		CharSequence rightErrorMessage = getRightErrorMessage();
		setLeftMessage(leftErrorMessage);
		setRightMessage(rightErrorMessage);

		if (leftErrorMessage == null && rightErrorMessage == null) {
			notifyOnValidationSuccess();
			return true;
		}

		return false;
	}

	@Override
	public final boolean isValidatedOnValueChange() {
		return validateOnValueChange;
	}

	@Override
	public final void validateOnValueChange(final boolean validateOnValueChange) {
		this.validateOnValueChange = validateOnValueChange;
	}

	@Override
	public final boolean isValidatedOnFocusLost() {
		return validateOnFocusLost;
	}

	@Override
	public final void validateOnFocusLost(final boolean validateOnFocusLost) {
		this.validateOnFocusLost = validateOnFocusLost;
	}

	@Override
	public final void addValidationListener(
			final ValidationListener<ValueType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		listeners.add(listener);
	}

	@Override
	public final void removeValidationListener(
			final ValidationListener<ValueType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		listeners.remove(listener);
	}

	@Override
	public final void onFocusChange(final View view, final boolean hasFocus) {
		if (!hasFocus && isValidatedOnFocusLost()) {
			validate();
		}
	}

}
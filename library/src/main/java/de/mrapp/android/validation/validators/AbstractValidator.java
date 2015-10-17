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
package de.mrapp.android.validation.validators;

import static de.mrapp.android.validation.util.Condition.ensureNotNull;
import static de.mrapp.android.validation.util.Condition.ensureNotEmpty;
import android.content.Context;
import android.graphics.drawable.Drawable;
import de.mrapp.android.validation.Validator;

/**
 * An abstract base class for all validators, which should be able to validate
 * values of a specific type.
 * 
 * @param <Type>
 *            The type of the values, which should be validated
 * 
 * @author Michael Rapp
 *
 * @since 1.0.0
 */
public abstract class AbstractValidator<Type> implements Validator<Type> {

	/**
	 * The error message, which should be shown, if the validation fails.
	 */
	private CharSequence errorMessage;

	/**
	 * The icon, which should be shown, if the validation fails.
	 */
	private Drawable icon;

	/**
	 * Creates a new validator, which should be able to validate values of a
	 * specific type.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 */
	public AbstractValidator(final CharSequence errorMessage) {
		setErrorMessage(errorMessage);
	}

	/**
	 * Creates a new validator, which should be able to validate values of a
	 * specific type.
	 * 
	 * @param context
	 *            The context, which should be used to retrieve the error
	 *            message, as an instance of the class {@link Context}. The
	 *            context may not be null
	 * @param resourceId
	 *            The resource ID of the string resource, which contains the
	 *            error message, which should be set, as an {@link Integer}
	 *            value. The resource ID must correspond to a valid string
	 *            resource
	 */
	public AbstractValidator(final Context context, final int resourceId) {
		setErrorMessage(context, resourceId);
	}

	/**
	 * Sets the error message, which should be shown, if the validation fails.
	 * 
	 * @param errorMessage
	 *            The error message, which should be set, as an instance of the
	 *            type {@link CharSequence}. The error message may not be null
	 */
	public final void setErrorMessage(final CharSequence errorMessage) {
		ensureNotNull(errorMessage, "The error message may not be null");
		ensureNotEmpty(errorMessage, "The error message may not be empty");
		this.errorMessage = errorMessage;
	}

	/**
	 * Sets the error message, which should be shown, if the validation fails.
	 * 
	 * @param context
	 *            The context, which should be used to retrieve the error
	 *            message, as an instance of the class {@link Context}. The
	 *            context may not be null
	 * @param resourceId
	 *            The resource ID of the string resource, which contains the
	 *            error message, which should be set, as an {@link Integer}
	 *            value. The resource ID must correspond to a valid string
	 *            resource
	 */
	public final void setErrorMessage(final Context context,
			final int resourceId) {
		ensureNotNull(context, "The context may not be null");
		this.errorMessage = context.getText(resourceId);
	}

	/**
	 * Sets the icon, which should be shown, if the validation fails.
	 * 
	 * @param icon
	 *            The icon, which should be set, as an instance of the class
	 *            {@link Drawable} or null, if no icon should be shown
	 */
	public final void setIcon(final Drawable icon) {
		this.icon = icon;
	}

	/**
	 * Sets the icon, which should be shown, if the validation fails.
	 * 
	 * @param context
	 *            The context, which should be used to retrieve the icon, as an
	 *            instance of the class {@link Context}. The context may not be
	 *            null
	 * @param resourceId
	 *            The resource ID of the drawable resource, which contains the
	 *            icon, which should be set, as an {@link Integer} value. The
	 *            resource ID must correspond to a valid drawable resource
	 */
	@SuppressWarnings("deprecation")
	public final void setIcon(final Context context, final int resourceId) {
		ensureNotNull(context, "The context may not be null");
		this.icon = context.getResources().getDrawable(resourceId);
	}

	@Override
	public final CharSequence getErrorMessage() {
		return errorMessage;
	}

	@Override
	public final Drawable getIcon() {
		return icon;
	}

}
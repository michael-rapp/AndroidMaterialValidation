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
package de.mrapp.android.validation.validators.text;

import static de.mrapp.android.validation.util.Condition.ensureAtLeast;
import android.content.Context;
import de.mrapp.android.validation.validators.AbstractValidator;

/**
 * A validator, which allows to validate texts to ensure, that they are not
 * longer than a specific length.
 * 
 * @author Michael Rapp
 *
 * @since 1.0.0
 */
public class MaxLengthValidator extends AbstractValidator<CharSequence> {

	/**
	 * The maximum length a text may have.
	 */
	private int maxLength;

	/**
	 * Creates a new validator, which allows to validate texts to ensure, that
	 * they are not longer than a specific length.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @param maxLength
	 *            The maximum length a text may have as an {@link Integer}
	 *            value. The maximum length must be at least 1
	 */
	public MaxLengthValidator(final CharSequence errorMessage,
			final int maxLength) {
		super(errorMessage);
		setMaxLength(maxLength);
	}

	/**
	 * Creates a new validator, which allows to validate texts to ensure, that
	 * they are not longer than a specific length.
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
	 * @param maxLength
	 *            The maximum length a text may have as an {@link Integer}
	 *            value. The maximum length must be at least 1
	 */
	public MaxLengthValidator(final Context context, final int resourceId,
			final int maxLength) {
		super(context, resourceId);
		setMaxLength(maxLength);
	}

	/**
	 * Returns the maximum length a text may have.
	 * 
	 * @return The maximum length a text may have as an {@link Integer} value
	 */
	public final int getMaxLength() {
		return maxLength;
	}

	/**
	 * Sets the maximum length a text may have.
	 * 
	 * @param maxLength
	 *            The maximum length, which should be set, as an {@link Integer}
	 *            value. The maximum length must be at least 1
	 */
	public final void setMaxLength(final int maxLength) {
		ensureAtLeast(maxLength, 1, "The maximum length must be at least 1");
		this.maxLength = maxLength;
	}

	@Override
	public final boolean validate(final CharSequence value) {
		return value.length() <= getMaxLength();
	}

}
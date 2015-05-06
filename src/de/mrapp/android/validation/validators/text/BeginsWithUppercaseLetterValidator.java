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

import android.content.Context;
import android.text.TextUtils;
import de.mrapp.android.validation.validators.AbstractValidator;

/**
 * A validator, which allows to validate text to ensure, that they begin with an
 * uppercase letter.
 * 
 * @author Michael Rapp
 */
public class BeginsWithUppercaseLetterValidator extends
		AbstractValidator<CharSequence> {

	/**
	 * Creates a new validator, which allows to validate texts to ensure, that
	 * they begin with an uppercase letter.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 */
	public BeginsWithUppercaseLetterValidator(final CharSequence errorMessage) {
		super(errorMessage);
	}

	/**
	 * Creates a new validator, which allows to validate texts to ensure, that
	 * they begin with an uppercase letter.
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
	public BeginsWithUppercaseLetterValidator(final Context context,
			final int resourceId) {
		super(context, resourceId);
	}

	@Override
	public final boolean validate(final CharSequence value) {
		return TextUtils.isEmpty(value) ? null : Character.isUpperCase(value
				.charAt(0));
	}

} 
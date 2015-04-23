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

import static de.mrapp.android.validation.util.Condition.ensureNotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import de.mrapp.android.validation.validators.AbstractValidator;

/**
 * A validator, which allows to validate texts to ensure, that they only contain
 * characters.
 * 
 * @author Michael Rapp
 *
 * @since 1.0.0
 */
public class CharacterValidator extends AbstractValidator<CharSequence> {

	/**
	 * Contains all cases, the validator may consider.
	 */
	public enum Case {

		/**
		 * If only uppercase characters should be allowed.
		 */
		UPPERCASE("[A-Z]+"),

		/**
		 * If only lowercase characters should be allowed.
		 */
		LOWERCASE("[a-z]+"),

		/**
		 * If uppercase and lowercase characters should be allowed.
		 */
		CASE_INSENSITIVE("[a-zA-Z]+");

		/**
		 * The regular expression, which has to be used for matching characters
		 * using the respective case sensitivity.
		 */
		private String regex;

		/**
		 * Creates a new case.
		 * 
		 * @param regex
		 *            The regular expression, which has to be used for matching
		 *            characters using the respective case sensitivity, as a
		 *            {@link String}. The regular expression may not be null
		 */
		private Case(final String regex) {
			ensureNotNull(regex, "The regular expression may not be null");
			this.regex = regex;
		}

		/**
		 * Returns the regular expression, which has to be used for matching
		 * characters using the respective case sensitivity.
		 * 
		 * @return The regular expression, which has to be used for matching
		 *         characters using the respective case sensitivity, as a
		 *         {@link String}. The regular expression may not be null
		 */
		public String getRegex() {
			return regex;
		}

	};

	/**
	 * The case sensitivity, which is used by the validator.
	 */
	private Case caseSensitivity;

	/**
	 * Creates a new validator, which allows to validate texts to ensure, that
	 * they only contain characters.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 */
	public CharacterValidator(final CharSequence errorMessage) {
		super(errorMessage);
		setCaseSensitivity(Case.CASE_INSENSITIVE);
	}

	/**
	 * Creates a new validator, which allows to validate texts to ensure, that
	 * they only contain characters.
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
	public CharacterValidator(final Context context, final int resourceId) {
		super(context, resourceId);
		setCaseSensitivity(Case.CASE_INSENSITIVE);
	}

	/**
	 * Sets the case sensitivity, which should be used by the validator.
	 * 
	 * @param caseSensitivty
	 *            The case senstivitiy, which should be set, as a value of the
	 *            enum {@link Case}. The value may either be
	 *            <code>UPPERCASE</code>, <code>LOWERCASE</code> or
	 *            <code>CASE_INSENSITIVE</code>
	 */
	public final void setCaseSensitivity(final Case caseSensitivty) {
		ensureNotNull(caseSensitivty, "The case sensitivity may not be null");
		this.caseSensitivity = caseSensitivty;
	}

	/**
	 * Returns the case sensitivity, which is used by the validator.
	 * 
	 * @return The case sensitivity, which is used by the validator, as a value
	 *         of the enum {@link Case}. The value may either be
	 *         <code>UPPERCASE</code>, <code>LOWERCASE</code> or
	 *         <code>CASE_INSENSITIVE</code>
	 */
	public final Case getCaseSensitivity() {
		return caseSensitivity;
	}

	@Override
	public final boolean validate(final CharSequence value) {
		Pattern pattern = Pattern.compile(getCaseSensitivity().getRegex());
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

}
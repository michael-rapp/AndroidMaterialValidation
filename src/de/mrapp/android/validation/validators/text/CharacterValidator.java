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
	 * The regular expression, which is used, when only uppercase characters
	 * should be allowed.
	 */
	private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]+");

	/**
	 * The regular expression, which is used, when only lowercase characters
	 * should be allowed.
	 */
	private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]+");

	/**
	 * The regular expression, which is used, when all characters, regardless of
	 * their case, should be allowed.
	 */
	private static final Pattern CASE_INSENSITIVE_PATTERN = Pattern
			.compile("[a-zA-Z]+");

	/**
	 * The case sensitivity, which is used by the validator.
	 */
	private Case caseSensitivity;

	/**
	 * True, if spaces should be allowed, false otherwise.
	 */
	private boolean allowSpaces;

	/**
	 * An array, which contains allowed chars.
	 */
	private char[] allowedChars;

	/**
	 * Creates a new validator, which allows to validate texts to ensure, that
	 * they only contain characters.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @param caseSensitivity
	 *            The case senstivitiy, which should be used by the validator,
	 *            as a value of the enum {@link Case}. The value may either be
	 *            <code>UPPERCASE</code>, <code>LOWERCASE</code> or
	 *            <code>CASE_INSENSITIVE</code>
	 * @param allowSpaces
	 *            True, if spaces should be allowed, false otherwise
	 * @param allowedChars
	 *            The allowed chars as an array of the type <code>char</code>.
	 *            The array may not be null
	 */
	public CharacterValidator(final CharSequence errorMessage,
			final Case caseSensitivity, final boolean allowSpaces,
			final char... allowedChars) {
		super(errorMessage);
		setCaseSensitivity(caseSensitivity);
		allowSpaces(allowSpaces);
		setAllowedChars(allowedChars);
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
	 * @param caseSensitivity
	 *            The case senstivitiy, which should be used by the validator,
	 *            as a value of the enum {@link Case}. The value may either be
	 *            <code>UPPERCASE</code>, <code>LOWERCASE</code> or
	 *            <code>CASE_INSENSITIVE</code>
	 * @param allowSpaces
	 *            True, if spaces should be allowed, false otherwise
	 * @param allowedChars
	 *            The allowed chars as an array of the type <code>char</code>.
	 *            The array may not be null
	 */
	public CharacterValidator(final Context context, final int resourceId,
			final Case caseSensitivity, final boolean allowSpaces,
			final char... allowedChars) {
		super(context, resourceId);
		setCaseSensitivity(caseSensitivity);
		allowSpaces(allowSpaces);
		setAllowedChars(allowedChars);
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
	 * Returns, whether spaces are allowed, or not.
	 * 
	 * @return True, if spaces are allowed, false otherwise
	 */
	public final boolean areSpacesAllowed() {
		return allowSpaces;
	}

	/**
	 * Sets, whether spaces should be allowed, or not.
	 * 
	 * @param allowSpaces
	 *            True, if spaces should be allowed, false otherwise
	 */
	public final void allowSpaces(final boolean allowSpaces) {
		this.allowSpaces = allowSpaces;
	}

	/**
	 * Returns the allowed chars.
	 * 
	 * @return An array, which contains the allowed chars, as an array of the
	 *         type <code>char</code>
	 */
	public final char[] getAllowedChars() {
		return allowedChars;
	}

	/**
	 * Sets the allowed chars.
	 * 
	 * @param allowedChars
	 *            The allowed chars, which should be set, as an array of the
	 *            type <code>char</code>. The array may not be null
	 */
	public final void setAllowedChars(final char[] allowedChars) {
		ensureNotNull(allowedChars, "The array may not be null");
		this.allowedChars = allowedChars;
	}

	@Override
	public final boolean validate(final CharSequence value) {
		String text = value.toString();
		Pattern regex = CASE_INSENSITIVE_PATTERN;

		if (areSpacesAllowed()) {
			text = text.replaceAll("\\s+", "");
		}

		for (char character : getAllowedChars()) {
			text = text.replaceAll(String.valueOf(character), "");
		}

		if (getCaseSensitivity() == Case.UPPERCASE) {
			regex = UPPERCASE_PATTERN;
		} else if (getCaseSensitivity() == Case.LOWERCASE) {
			regex = LOWERCASE_PATTERN;
		}

		Matcher matcher = regex.matcher(text);
		return matcher.matches();
	}

}
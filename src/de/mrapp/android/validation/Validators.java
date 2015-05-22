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

import java.util.regex.Pattern;

import android.content.Context;
import de.mrapp.android.validation.validators.ConjunctiveValidator;
import de.mrapp.android.validation.validators.DisjunctiveValidator;
import de.mrapp.android.validation.validators.NegateValidator;
import de.mrapp.android.validation.validators.NotNullValidator;
import de.mrapp.android.validation.validators.misc.DomainNameValidator;
import de.mrapp.android.validation.validators.misc.EmailAddressValidator;
import de.mrapp.android.validation.validators.misc.IPv4AddressValidator;
import de.mrapp.android.validation.validators.misc.IPv6AddressValidator;
import de.mrapp.android.validation.validators.misc.IRIValidator;
import de.mrapp.android.validation.validators.misc.PhoneNumberValidator;
import de.mrapp.android.validation.validators.text.BeginsWithUppercaseLetterValidator;
import de.mrapp.android.validation.validators.text.Case;
import de.mrapp.android.validation.validators.text.CharacterOrNumberValidator;
import de.mrapp.android.validation.validators.text.CharacterValidator;
import de.mrapp.android.validation.validators.text.EqualValidator;
import de.mrapp.android.validation.validators.text.MaxLengthValidator;
import de.mrapp.android.validation.validators.text.MinLengthValidator;
import de.mrapp.android.validation.validators.text.NoWhitespaceValidator;
import de.mrapp.android.validation.validators.text.NotEmptyValidator;
import de.mrapp.android.validation.validators.text.NumberValidator;
import de.mrapp.android.validation.validators.text.RegexValidator;

/**
 * An utility class, which provides factory methods, which allow to create
 * various validators.
 * 
 * @author Michael Rapp
 *
 * @since 1.0.0
 */
public final class Validators {

	/**
	 * Creates a new utility class, which provides factory methods, which allow
	 * to create various validators.
	 */
	private Validators() {

	}

	/**
	 * Creates and returns a validator, which allows to negate the result of an
	 * other validator.
	 * 
	 * @param <Type>
	 *            The type of the values, which should be validated
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @param validator
	 *            The validator, whose result should be negated, as an instance
	 *            of the type {@link Validator}. The validator may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static <Type> Validator<Type> negate(
			final CharSequence errorMessage, final Validator<Type> validator) {
		return NegateValidator.create(errorMessage, validator);
	}

	/**
	 * Creates and returns a validator, which allows to negate the result of an
	 * other validator.
	 * 
	 * @param <Type>
	 *            The type of the values, which should be validated
	 * @param context
	 *            The context, which should be used to retrieve the error
	 *            message, as an instance of the class {@link Context}. The
	 *            context may not be null
	 * @param resourceId
	 *            The resource ID of the string resource, which contains the
	 *            error message, which should be set, as an {@link Integer}
	 *            value. The resource ID must correspond to a valid string
	 *            resource
	 * @param validator
	 *            The validator, whose result should be negated, as an instance
	 *            of the type {@link Validator}. The validator may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static <Type> Validator<Type> negate(final Context context,
			final int resourceId, final Validator<Type> validator) {
		return NegateValidator.create(context, resourceId, validator);
	}

	/**
	 * Creates and returns a validator, which allows to combine multiple
	 * validators in a conjunctive manner. Only if all single validators
	 * succeed, the resulting validator will also succeed.
	 * 
	 * 
	 * @param <Type>
	 *            The type of the values, which should be validated
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @param validators
	 *            The single validators, the validator should consist of, as an
	 *            array of the type {@link Validator}. The validators may
	 *            neither be null, nor empty
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	@SafeVarargs
	public static <Type> Validator<Type> conjunctive(
			final CharSequence errorMessage,
			final Validator<Type>... validators) {
		return ConjunctiveValidator.create(errorMessage, validators);
	}

	/**
	 * Creates and returns a validator, which allows to combine multiple
	 * validators in a conjunctive manner. If all single validators succeed, the
	 * resulting validator will also succeed.
	 * 
	 * 
	 * @param <Type>
	 *            The type of the values, which should be validated
	 * @param context
	 *            The context, which should be used to retrieve the error
	 *            message, as an instance of the class {@link Context}. The
	 *            context may not be null
	 * @param resourceId
	 *            The resource ID of the string resource, which contains the
	 *            error message, which should be set, as an {@link Integer}
	 *            value. The resource ID must correspond to a valid string
	 *            resource
	 * @param validators
	 *            The single validators, the validator should consist of, as an
	 *            array of the type {@link Validator}. The validators may
	 *            neither be null, nor empty
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	@SafeVarargs
	public static <Type> Validator<Type> conjunctive(final Context context,
			final int resourceId, final Validator<Type>... validators) {
		return ConjunctiveValidator.create(context, resourceId, validators);
	}

	/**
	 * Creates and returns a validator, which allows to combine multiple
	 * validators in a disjunctive manner. If at least one validator succeeds,
	 * the resulting validator will also succeed.
	 * 
	 * 
	 * @param <Type>
	 *            The type of the values, which should be validated
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @param validators
	 *            The single validators, the validator should consist of, as an
	 *            array of the type {@link Validator}. The validators may
	 *            neither be null, nor empty
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	@SafeVarargs
	public static <Type> Validator<Type> disjunctive(
			final CharSequence errorMessage,
			final Validator<Type>... validators) {
		return DisjunctiveValidator.create(errorMessage, validators);
	}

	/**
	 * Creates and returns a validator, which allows to combine multiple
	 * validators in a disjunctive manner. If at least one validator succeeds,
	 * the resulting validator will also succeed.
	 * 
	 * 
	 * @param <Type>
	 *            The type of the values, which should be validated
	 * @param context
	 *            The context, which should be used to retrieve the error
	 *            message, as an instance of the class {@link Context}. The
	 *            context may not be null
	 * @param resourceId
	 *            The resource ID of the string resource, which contains the
	 *            error message, which should be set, as an {@link Integer}
	 *            value. The resource ID must correspond to a valid string
	 *            resource
	 * @param validators
	 *            The single validators, the validator should consist of, as an
	 *            array of the type {@link Validator}. The validators may
	 *            neither be null, nor empty
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	@SafeVarargs
	public static <Type> Validator<Type> disjunctive(final Context context,
			final int resourceId, final Validator<Type>... validators) {
		return DisjunctiveValidator.create(context, resourceId, validators);
	}

	/**
	 * Creates and returns a validator, which allows to ensure, that values are
	 * not null.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<Object> notNull(final CharSequence errorMessage) {
		return new NotNullValidator(errorMessage);
	}

	/**
	 * Creates and returns a validator, which allows to ensure, that values are
	 * not null.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<Object> notNull(final Context context,
			final int resourceId) {
		return new NotNullValidator(context, resourceId);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they match a certain regular expression.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @param regex
	 *            The regular expression, which should be used to validate the
	 *            texts, as an instance of the class {@link Pattern}. The
	 *            regular expression may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> regex(
			final CharSequence errorMessage, final Pattern regex) {
		return new RegexValidator(errorMessage, regex);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they match certain regular expressions.
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
	 * @param regex
	 *            The regular expression, which should be used to validate the
	 *            texts, as an instance of the class {@link Pattern}. The
	 *            regular expression may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> regex(final Context context,
			final int resourceId, final Pattern regex) {
		return new RegexValidator(context, resourceId, regex);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they are not empty.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> notEmpty(
			final CharSequence errorMessage) {
		return new NotEmptyValidator(errorMessage);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they are not empty.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> notEmpty(final Context context,
			final int resourceId) {
		return new NotEmptyValidator(context, resourceId);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they have at least a specific length.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @param minLength
	 *            The minimum length a text must have as an {@link Integer}
	 *            value. The minimum length must be at least 1
	 * @return @return The validator, which has been created, as an instance of
	 *         the type {@link Validator}
	 */
	public static Validator<CharSequence> minLength(
			final CharSequence errorMessage, final int minLength) {
		return new MinLengthValidator(errorMessage, minLength);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they have at least a specific length.
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
	 * @param minLength
	 *            The minimum length a text must have as an {@link Integer}
	 *            value. The minimum length must be at least 1
	 * @return @return The validator, which has been created, as an instance of
	 *         the type {@link Validator}
	 */
	public static Validator<CharSequence> minLength(final Context context,
			final int resourceId, final int minLength) {
		return new MinLengthValidator(context, resourceId, minLength);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they are not longer than a specific length.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @param maxLength
	 *            The maximum length a text may have as an {@link Integer}
	 *            value. The maximum length must be at least 1
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> maxLength(
			final CharSequence errorMessage, final int maxLength) {
		return new MaxLengthValidator(errorMessage, maxLength);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they are not longer than a specific length.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> maxLength(final Context context,
			final int resourceId, final int maxLength) {
		return new MaxLengthValidator(context, resourceId, maxLength);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they contain no whitespace. Empty texts are also accepted.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> noWhitespace(
			final CharSequence errorMessage) {
		return new NoWhitespaceValidator(errorMessage);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they contain no whitespace. Empty texts are also accepted.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> noWhitespace(final Context context,
			final int resourceId) {
		return new NoWhitespaceValidator(context, resourceId);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they only contain numbers. Empty texts are also accepted.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> number(final CharSequence errorMessage) {
		return new NumberValidator(errorMessage);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they only contain numbers. Empty texts are also accepted.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> number(final Context context,
			final int resourceId) {
		return new NumberValidator(context, resourceId);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they only contain characters. Empty texts are also accepted.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> character(
			final CharSequence errorMessage, final Case caseSensitivity,
			final boolean allowSpaces, final char... allowedChars) {
		return new CharacterValidator(errorMessage, caseSensitivity,
				allowSpaces, allowedChars);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they only contain characters. Empty texts are also accepted.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> character(final Context context,
			final int resourceId, final Case caseSensitivity,
			final boolean allowSpaces, final char... allowedChars) {
		return new CharacterValidator(context, resourceId, caseSensitivity,
				allowSpaces, allowedChars);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they only contain characters or numbers. Empty texts are
	 * also accepted.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> characterOrNumber(
			final CharSequence errorMessage, final Case caseSensitivity,
			final boolean allowSpaces, final char... allowedChars) {
		return new CharacterOrNumberValidator(errorMessage, caseSensitivity,
				allowSpaces, allowedChars);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they only contain characters or numbers. Empty texts are
	 * also accepted.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> characterOrNumber(
			final Context context, final int resourceId,
			final Case caseSensitivity, final boolean allowSpaces,
			final char... allowedChars) {
		return new CharacterOrNumberValidator(context, resourceId,
				caseSensitivity, allowSpaces, allowedChars);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they begin with an uppercase letter. Empty texts are also
	 * accepted.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> beginsWithUppercaseLetter(
			final CharSequence errorMessage) {
		return new BeginsWithUppercaseLetterValidator(errorMessage);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they begin with an uppercase letter. Empty texts are also
	 * accepted.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> beginsWithUppercaseLetter(
			final Context context, final int resourceId) {
		return new BeginsWithUppercaseLetterValidator(context, resourceId);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they are equal to the text, which is contained by an
	 * {@link EditText} widget.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @param editText
	 *            The edit text widget, which contains the content, the texts
	 *            should be equal to, as an instance of the class
	 *            {@link EditText}. The widget may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> equal(
			final CharSequence errorMessage, final EditText editText) {
		return new EqualValidator(errorMessage, editText);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they are equal to the text, which is contained by an
	 * {@link EditText} widget.
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
	 * @param editText
	 *            The edit text widget, which contains the content, the texts
	 *            should be equal to, as an instance of the class
	 *            {@link EditText}. The widget may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> equal(final Context context,
			final int resourceId, final EditText editText) {
		return new EqualValidator(context, resourceId, editText);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they represent valid IPv4 addresses. Empty texts are also
	 * accepted.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> iPv4Address(
			final CharSequence errorMessage) {
		return new IPv4AddressValidator(errorMessage);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they represent valid IPv4 addresses. Empty texts are also
	 * accepted.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> iPv4Address(final Context context,
			final int resourceId) {
		return new IPv4AddressValidator(context, resourceId);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they represent valid IPv6 addresses. Empty texts are also
	 * accepted.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> iPv6Address(
			final CharSequence errorMessage) {
		return new IPv6AddressValidator(errorMessage);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they represent valid IPv6 addresses. Empty texts are also
	 * accepted.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> iPv6Address(final Context context,
			final int resourceId) {
		return new IPv6AddressValidator(context, resourceId);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they represent valid domain names. Empty texts are also
	 * accepted.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> domainName(
			final CharSequence errorMessage) {
		return new DomainNameValidator(errorMessage);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they represent valid domain names. Empty texts are also
	 * accepted.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> domainName(final Context context,
			final int resourceId) {
		return new DomainNameValidator(context, resourceId);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they represent valid email addresses. Empty texts are also
	 * accepted.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> emailAddress(
			final CharSequence errorMessage) {
		return new EmailAddressValidator(errorMessage);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they represent valid email addresses. Empty texts are also
	 * accepted.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> emailAddress(final Context context,
			final int resourceId) {
		return new EmailAddressValidator(context, resourceId);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they represent valid IRIs. IRIs are internationalized URLs
	 * according to RFC 3987, which are for example used as internet addresses..
	 * Empty texts are also accepted.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> iri(final CharSequence errorMessage) {
		return new IRIValidator(errorMessage);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they represent valid IRIs. IRIs are internationalized URLs
	 * according to RFC 3987, which are for example used as internet addresses..
	 * Empty texts are also accepted.
	 * 
	 * @param context
	 *            The context, which should be used to retrieve the error
	 *            message, as an instance of the class {@link Context}. fhe
	 *            context may not be null
	 * @param resourceId
	 *            The resource ID of the string resource, which contains the
	 *            error message, which should be set, as an {@link Integer}
	 *            value. The resource ID must correspond to a valid string
	 *            resource
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> iri(final Context context,
			final int resourceId) {
		return new IRIValidator(context, resourceId);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they represent valid phone numbers. Phone numbers, which are
	 * only consisting of numbers are allowed as well as international phone
	 * numbers, e.g. +49 1624812382. Empty texts are also accepted.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> phoneNumber(
			final CharSequence errorMessage) {
		return new PhoneNumberValidator(errorMessage);
	}

	/**
	 * Creates and returns a validator, which allows to validate texts to
	 * ensure, that they represent valid phone numbers. Phone numbers, which are
	 * only consisting of numbers are allowed as well as international phone
	 * numbers, e.g. +49 1624812382. Empty texts are also accepted.
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
	 * @return The validator, which has been created, as an instance of the type
	 *         {@link Validator}
	 */
	public static Validator<CharSequence> phoneNumber(final Context context,
			final int resourceId) {
		return new PhoneNumberValidator(context, resourceId);
	}

}
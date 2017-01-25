/*
 * Copyright 2015 - 2017 Michael Rapp
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
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.regex.Pattern;

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
import de.mrapp.android.validation.validators.text.EqualValidator;
import de.mrapp.android.validation.validators.text.LetterOrNumberValidator;
import de.mrapp.android.validation.validators.text.LetterValidator;
import de.mrapp.android.validation.validators.text.MaxLengthValidator;
import de.mrapp.android.validation.validators.text.MinLengthValidator;
import de.mrapp.android.validation.validators.text.NoWhitespaceValidator;
import de.mrapp.android.validation.validators.text.NotEmptyValidator;
import de.mrapp.android.validation.validators.text.NumberValidator;
import de.mrapp.android.validation.validators.text.RegexValidator;

/**
 * An utility class, which provides factory methods, which allow to create various validators.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public final class Validators {

    /**
     * Creates a new utility class, which provides factory methods, which allow to create various
     * validators.
     */
    private Validators() {

    }

    /**
     * Creates and returns a validator, which allows to negate the result of an other validator.
     *
     * @param <Type>
     *         The type of the values, which should be validated
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param validator
     *         The validator, whose result should be negated, as an instance of the type {@link
     *         Validator}. The validator may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static <Type> Validator<Type> negate(@NonNull final CharSequence errorMessage,
                                                @NonNull final Validator<Type> validator) {
        return NegateValidator.create(errorMessage, validator);
    }

    /**
     * Creates and returns a validator, which allows to negate the result of an other validator.
     *
     * @param <Type>
     *         The type of the values, which should be validated
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param validator
     *         The validator, whose result should be negated, as an instance of the type {@link
     *         Validator}. The validator may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static <Type> Validator<Type> negate(@NonNull final Context context,
                                                @StringRes final int resourceId,
                                                @NonNull final Validator<Type> validator) {
        return NegateValidator.create(context, resourceId, validator);
    }

    /**
     * Creates and returns a validator, which allows to negate the result of an other validator.
     *
     * @param <Type>
     *         The type of the values, which should be validated
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param validator
     *         The validator, whose result should be negated, as an instance of the type {@link
     *         Validator}. The validator may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static <Type> Validator<Type> negate(@NonNull final Context context,
                                                @NonNull final Validator<Type> validator) {
        return NegateValidator.create(context, R.string.default_error_message, validator);
    }

    /**
     * Creates and returns a validator, which allows to combine multiple validators in a conjunctive
     * manner. Only if all single validators succeed, the resulting validator will also succeed.
     *
     * @param <Type>
     *         The type of the values, which should be validated
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param validators
     *         The single validators, the validator should consist of, as an array of the type
     *         {@link Validator}. The validators may neither be null, nor empty
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    @SafeVarargs
    public static <Type> Validator<Type> conjunctive(@NonNull final CharSequence errorMessage,
                                                     @NonNull final Validator<Type>... validators) {
        return ConjunctiveValidator.create(errorMessage, validators);
    }

    /**
     * Creates and returns a validator, which allows to combine multiple validators in a conjunctive
     * manner. If all single validators succeed, the resulting validator will also succeed.
     *
     * @param <Type>
     *         The type of the values, which should be validated
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param validators
     *         The single validators, the validator should consist of, as an array of the type
     *         {@link Validator}. The validators may neither be null, nor empty
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    @SafeVarargs
    public static <Type> Validator<Type> conjunctive(@NonNull final Context context,
                                                     @StringRes final int resourceId,
                                                     @NonNull final Validator<Type>... validators) {
        return ConjunctiveValidator.create(context, resourceId, validators);
    }

    /**
     * Creates and returns a validator, which allows to combine multiple validators in a conjunctive
     * manner. If all single validators succeed, the resulting validator will also succeed.
     *
     * @param <Type>
     *         The type of the values, which should be validated
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param validators
     *         The single validators, the validator should consist of, as an array of the type
     *         {@link Validator}. The validators may neither be null, nor empty
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    @SafeVarargs
    public static <Type> Validator<Type> conjunctive(@NonNull final Context context,
                                                     @NonNull final Validator<Type>... validators) {
        return ConjunctiveValidator.create(context, R.string.default_error_message, validators);
    }

    /**
     * Creates and returns a validator, which allows to combine multiple validators in a disjunctive
     * manner. If at least one validator succeeds, the resulting validator will also succeed.
     *
     * @param <Type>
     *         The type of the values, which should be validated
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param validators
     *         The single validators, the validator should consist of, as an array of the type
     *         {@link Validator}. The validators may neither be null, nor empty
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    @SafeVarargs
    public static <Type> Validator<Type> disjunctive(@NonNull final CharSequence errorMessage,
                                                     @NonNull final Validator<Type>... validators) {
        return DisjunctiveValidator.create(errorMessage, validators);
    }

    /**
     * Creates and returns a validator, which allows to combine multiple validators in a disjunctive
     * manner. If at least one validator succeeds, the resulting validator will also succeed.
     *
     * @param <Type>
     *         The type of the values, which should be validated
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param validators
     *         The single validators, the validator should consist of, as an array of the type
     *         {@link Validator}. The validators may neither be null, nor empty
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    @SafeVarargs
    public static <Type> Validator<Type> disjunctive(@NonNull final Context context,
                                                     @StringRes final int resourceId,
                                                     @NonNull final Validator<Type>... validators) {
        return DisjunctiveValidator.create(context, resourceId, validators);
    }

    /**
     * Creates and returns a validator, which allows to combine multiple validators in a disjunctive
     * manner. If at least one validator succeeds, the resulting validator will also succeed.
     *
     * @param <Type>
     *         The type of the values, which should be validated
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param validators
     *         The single validators, the validator should consist of, as an array of the type
     *         {@link Validator}. The validators may neither be null, nor empty
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    @SafeVarargs
    public static <Type> Validator<Type> disjunctive(@NonNull final Context context,
                                                     @NonNull final Validator<Type>... validators) {
        return DisjunctiveValidator.create(context, R.string.default_error_message, validators);
    }

    /**
     * Creates and returns a validator, which allows to ensure, that values are not null.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<Object> notNull(@NonNull final CharSequence errorMessage) {
        return new NotNullValidator(errorMessage);
    }

    /**
     * Creates and returns a validator, which allows to ensure, that values are not null.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<Object> notNull(@NonNull final Context context,
                                            @StringRes final int resourceId) {
        return new NotNullValidator(context, resourceId);
    }

    /**
     * Creates and returns a validator, which allows to ensure, that values are not null.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<Object> notNull(@NonNull final Context context) {
        return new NotNullValidator(context, R.string.default_error_message);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they match a
     * certain regular expression.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param regex
     *         The regular expression, which should be used to validate the texts, as an instance of
     *         the class {@link Pattern}. The regular expression may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> regex(@NonNull final CharSequence errorMessage,
                                                @NonNull final Pattern regex) {
        return new RegexValidator(errorMessage, regex);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they match
     * certain regular expressions.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param regex
     *         The regular expression, which should be used to validate the texts, as an instance of
     *         the class {@link Pattern}. The regular expression may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> regex(@NonNull final Context context,
                                                @StringRes final int resourceId,
                                                @NonNull final Pattern regex) {
        return new RegexValidator(context, resourceId, regex);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they match
     * certain regular expressions.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param regex
     *         The regular expression, which should be used to validate the texts, as an instance of
     *         the class {@link Pattern}. The regular expression may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> regex(@NonNull final Context context,
                                                @NonNull final Pattern regex) {
        return new RegexValidator(context, R.string.default_error_message, regex);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they are not
     * empty.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> notEmpty(@NonNull final CharSequence errorMessage) {
        return new NotEmptyValidator(errorMessage);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they are not
     * empty.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> notEmpty(@NonNull final Context context,
                                                   @StringRes final int resourceId) {
        return new NotEmptyValidator(context, resourceId);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they are not
     * empty.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> notEmpty(@NonNull final Context context) {
        return new NotEmptyValidator(context, R.string.default_error_message);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they have at
     * least a specific length.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param minLength
     *         The minimum length a text must have as an {@link Integer} value. The minimum length
     *         must be at least 1
     * @return @return The validator, which has been created, as an instance of the type {@link
     * Validator}
     */
    public static Validator<CharSequence> minLength(@NonNull final CharSequence errorMessage,
                                                    final int minLength) {
        return new MinLengthValidator(errorMessage, minLength);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they have at
     * least a specific length.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param minLength
     *         The minimum length a text must have as an {@link Integer} value. The minimum length
     *         must be at least 1
     * @return @return The validator, which has been created, as an instance of the type {@link
     * Validator}
     */
    public static Validator<CharSequence> minLength(@NonNull final Context context,
                                                    @StringRes final int resourceId,
                                                    final int minLength) {
        return new MinLengthValidator(context, resourceId, minLength);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they have at
     * least a specific length.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param minLength
     *         The minimum length a text must have as an {@link Integer} value. The minimum length
     *         must be at least 1
     * @return @return The validator, which has been created, as an instance of the type {@link
     * Validator}
     */
    public static Validator<CharSequence> minLength(@NonNull final Context context,
                                                    final int minLength) {
        return new MinLengthValidator(context, R.string.default_error_message, minLength);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they are not
     * longer than a specific length.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param maxLength
     *         The maximum length a text may have as an {@link Integer} value. The maximum length
     *         must be at least 1
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> maxLength(@NonNull final CharSequence errorMessage,
                                                    final int maxLength) {
        return new MaxLengthValidator(errorMessage, maxLength);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they are not
     * longer than a specific length.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param maxLength
     *         The maximum length a text may have as an {@link Integer} value. The maximum length
     *         must be at least 1
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> maxLength(@NonNull final Context context,
                                                    @StringRes final int resourceId,
                                                    final int maxLength) {
        return new MaxLengthValidator(context, resourceId, maxLength);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they are not
     * longer than a specific length.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param maxLength
     *         The maximum length a text may have as an {@link Integer} value. The maximum length
     *         must be at least 1
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> maxLength(@NonNull final Context context,
                                                    final int maxLength) {
        return new MaxLengthValidator(context, R.string.default_error_message, maxLength);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they contain
     * no whitespace. Empty texts are also accepted.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> noWhitespace(@NonNull final CharSequence errorMessage) {
        return new NoWhitespaceValidator(errorMessage);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they contain
     * no whitespace. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> noWhitespace(@NonNull final Context context,
                                                       @StringRes final int resourceId) {
        return new NoWhitespaceValidator(context, resourceId);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they contain
     * no whitespace. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> noWhitespace(@NonNull final Context context) {
        return new NoWhitespaceValidator(context, R.string.default_error_message);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they only
     * contain numbers. Empty texts are also accepted.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> number(@NonNull final CharSequence errorMessage) {
        return new NumberValidator(errorMessage);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they only
     * contain numbers. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> number(@NonNull final Context context,
                                                 @StringRes final int resourceId) {
        return new NumberValidator(context, resourceId);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they only
     * contain numbers. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> number(@NonNull final Context context) {
        return new NumberValidator(context, R.string.default_error_message);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they only
     * contain letters. Letters are considered to be all alphabetical characters from A to B. It is
     * possible to specify, whether only uppercase or lowercase letters should be accepted, or if
     * they should be threatened case-insensitive. Additionally, it is possible to specify whether
     * spaces should be allowed and to add special characters, which should also be accepted. Empty
     * texts are also accepted.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param caseSensitivity
     *         The case senstivitiy, which should be used by the validator, as a value of the enum
     *         {@link Case}. The value may either be <code>UPPERCASE</code>, <code>LOWERCASE</code>
     *         or <code>CASE_INSENSITIVE</code>
     * @param allowSpaces
     *         True, if spaces should be allowed, false otherwise
     * @param allowedCharacters
     *         The allowed special characters as an array of the type <code>char</code>. The array
     *         may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> letter(@NonNull final CharSequence errorMessage,
                                                 @NonNull final Case caseSensitivity,
                                                 final boolean allowSpaces,
                                                 @NonNull final char... allowedCharacters) {
        return new LetterValidator(errorMessage, caseSensitivity, allowSpaces, allowedCharacters);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they only
     * contain letters. Letters are considered to be all alphabetical characters from A to B. It is
     * possible to specify, whether only uppercase or lowercase letters should be accepted, or if
     * they should be threatened case-insensitive. Additionally, it is possible to specify whether
     * spaces should be allowed and to add special characters, which should also be accepted. Empty
     * texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param caseSensitivity
     *         The case senstivitiy, which should be used by the validator, as a value of the enum
     *         {@link Case}. The value may either be <code>UPPERCASE</code>, <code>LOWERCASE</code>
     *         or <code>CASE_INSENSITIVE</code>
     * @param allowSpaces
     *         True, if spaces should be allowed, false otherwise
     * @param allowedCharacters
     *         The allowed special characters as an array of the type <code>char</code>. The array
     *         may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> letter(@NonNull final Context context,
                                                 @StringRes final int resourceId,
                                                 @NonNull final Case caseSensitivity,
                                                 final boolean allowSpaces,
                                                 @NonNull final char... allowedCharacters) {
        return new LetterValidator(context, resourceId, caseSensitivity, allowSpaces,
                allowedCharacters);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they only
     * contain letters. Letters are considered to be all alphabetical characters from A to B. It is
     * possible to specify, whether only uppercase or lowercase letters should be accepted, or if
     * they should be threatened case-insensitive. Additionally, it is possible to specify whether
     * spaces should be allowed and to add special characters, which should also be accepted. Empty
     * texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param caseSensitivity
     *         The case senstivitiy, which should be used by the validator, as a value of the enum
     *         {@link Case}. The value may either be <code>UPPERCASE</code>, <code>LOWERCASE</code>
     *         or <code>CASE_INSENSITIVE</code>
     * @param allowSpaces
     *         True, if spaces should be allowed, false otherwise
     * @param allowedCharacters
     *         The allowed special characters as an array of the type <code>char</code>. The array
     *         may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> letter(@NonNull final Context context,
                                                 @NonNull final Case caseSensitivity,
                                                 final boolean allowSpaces,
                                                 @NonNull final char... allowedCharacters) {
        return new LetterValidator(context, R.string.default_error_message, caseSensitivity,
                allowSpaces, allowedCharacters);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they only
     * contain letters or numbers. It is possible to specify, whether only uppercase or lowercase
     * letters should be accepted, or if they should be threatened case-insensitive. Letters are
     * considered to be all alphabetical characters from A to B. Additionally, it is possible to
     * specify whether spaces should be allowed and to add special characters, which should also be
     * accepted. Empty texts are also accepted.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param caseSensitivity
     *         The case senstivitiy, which should be used by the validator, as a value of the enum
     *         {@link Case}. The value may either be <code>UPPERCASE</code>, <code>LOWERCASE</code>
     *         or <code>CASE_INSENSITIVE</code>
     * @param allowSpaces
     *         True, if spaces should be allowed, false otherwise
     * @param allowedCharacters
     *         The allowed special characters as an array of the type <code>char</code>. The array
     *         may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> letterOrNumber(@NonNull final CharSequence errorMessage,
                                                         @NonNull final Case caseSensitivity,
                                                         final boolean allowSpaces,
                                                         @NonNull final char... allowedCharacters) {
        return new LetterOrNumberValidator(errorMessage, caseSensitivity, allowSpaces,
                allowedCharacters);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they only
     * contain letters or numbers. It is possible to specify, whether only uppercase or lowercase
     * letters should be accepted, or if they should be threatened case-insensitive. Letters are
     * considered to be all alphabetical characters from A to B. Additionally, it is possible to
     * specify whether spaces should be allowed and to add special characters, which should also be
     * accepted. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param caseSensitivity
     *         The case senstivitiy, which should be used by the validator, as a value of the enum
     *         {@link Case}. The value may either be <code>UPPERCASE</code>, <code>LOWERCASE</code>
     *         or <code>CASE_INSENSITIVE</code>
     * @param allowSpaces
     *         True, if spaces should be allowed, false otherwise
     * @param allowedCharacters
     *         The allowed special characters as an array of the type <code>char</code>. The array
     *         may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> letterOrNumber(@NonNull final Context context,
                                                         @StringRes final int resourceId,
                                                         @NonNull final Case caseSensitivity,
                                                         final boolean allowSpaces,
                                                         @NonNull final char... allowedCharacters) {
        return new LetterOrNumberValidator(context, resourceId, caseSensitivity, allowSpaces,
                allowedCharacters);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they only
     * contain letters or numbers. It is possible to specify, whether only uppercase or lowercase
     * letters should be accepted, or if they should be threatened case-insensitive. Letters are
     * considered to be all alphabetical characters from A to B. Additionally, it is possible to
     * specify whether spaces should be allowed and to add special characters, which should also be
     * accepted. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param caseSensitivity
     *         The case senstivitiy, which should be used by the validator, as a value of the enum
     *         {@link Case}. The value may either be <code>UPPERCASE</code>, <code>LOWERCASE</code>
     *         or <code>CASE_INSENSITIVE</code>
     * @param allowSpaces
     *         True, if spaces should be allowed, false otherwise
     * @param allowedCharacters
     *         The allowed special characters as an array of the type <code>char</code>. The array
     *         may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> letterOrNumber(@NonNull final Context context,
                                                         @NonNull final Case caseSensitivity,
                                                         final boolean allowSpaces,
                                                         @NonNull final char... allowedCharacters) {
        return new LetterOrNumberValidator(context, R.string.default_error_message, caseSensitivity,
                allowSpaces, allowedCharacters);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they begin
     * with an uppercase letter. Empty texts are also accepted.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> beginsWithUppercaseLetter(
            @NonNull final CharSequence errorMessage) {
        return new BeginsWithUppercaseLetterValidator(errorMessage);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they begin
     * with an uppercase letter. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> beginsWithUppercaseLetter(@NonNull final Context context,
                                                                    @StringRes final int resourceId) {
        return new BeginsWithUppercaseLetterValidator(context, resourceId);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they begin
     * with an uppercase letter. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> beginsWithUppercaseLetter(
            @NonNull final Context context) {
        return new BeginsWithUppercaseLetterValidator(context, R.string.default_error_message);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they are
     * equal to the text, which is contained by an {@link EditText} widget.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @param editText
     *         The edit text widget, which contains the content, the texts should be equal to, as an
     *         instance of the class {@link EditText}. The widget may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> equal(@NonNull final CharSequence errorMessage,
                                                @NonNull final EditText editText) {
        return new EqualValidator(errorMessage, editText);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they are
     * equal to the text, which is contained by an {@link EditText} widget.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @param editText
     *         The edit text widget, which contains the content, the texts should be equal to, as an
     *         instance of the class {@link EditText}. The widget may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> equal(@NonNull final Context context,
                                                @StringRes final int resourceId,
                                                @NonNull final EditText editText) {
        return new EqualValidator(context, resourceId, editText);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they are
     * equal to the text, which is contained by an {@link EditText} widget.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param editText
     *         The edit text widget, which contains the content, the texts should be equal to, as an
     *         instance of the class {@link EditText}. The widget may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> equal(@NonNull final Context context,
                                                @NonNull final EditText editText) {
        return new EqualValidator(context, R.string.default_error_message, editText);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid IPv4 addresses. Empty texts are also accepted.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> iPv4Address(@NonNull final CharSequence errorMessage) {
        return new IPv4AddressValidator(errorMessage);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid IPv4 addresses. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> iPv4Address(@NonNull final Context context,
                                                      @StringRes final int resourceId) {
        return new IPv4AddressValidator(context, resourceId);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid IPv4 addresses. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> iPv4Address(@NonNull final Context context) {
        return new IPv4AddressValidator(context, R.string.default_error_message);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid IPv6 addresses. Empty texts are also accepted.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> iPv6Address(@NonNull final CharSequence errorMessage) {
        return new IPv6AddressValidator(errorMessage);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid IPv6 addresses. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> iPv6Address(@NonNull final Context context,
                                                      @StringRes final int resourceId) {
        return new IPv6AddressValidator(context, resourceId);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid IPv6 addresses. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> iPv6Address(@NonNull final Context context) {
        return new IPv6AddressValidator(context, R.string.default_error_message);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid domain names. Empty texts are also accepted.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> domainName(@NonNull final CharSequence errorMessage) {
        return new DomainNameValidator(errorMessage);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid domain names. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> domainName(@NonNull final Context context,
                                                     @StringRes final int resourceId) {
        return new DomainNameValidator(context, resourceId);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid domain names. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> domainName(@NonNull final Context context) {
        return new DomainNameValidator(context, R.string.default_error_message);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid email addresses. Empty texts are also accepted.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> emailAddress(@NonNull final CharSequence errorMessage) {
        return new EmailAddressValidator(errorMessage);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid email addresses. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> emailAddress(@NonNull final Context context,
                                                       @StringRes final int resourceId) {
        return new EmailAddressValidator(context, resourceId);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid email addresses. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> emailAddress(@NonNull final Context context) {
        return new EmailAddressValidator(context, R.string.default_error_message);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid IRIs. IRIs are internationalized URLs according to RFC 3987, which are for
     * example used as internet addresses. Empty texts are also accepted.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> iri(@NonNull final CharSequence errorMessage) {
        return new IRIValidator(errorMessage);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid IRIs. IRIs are internationalized URLs according to RFC 3987, which are for
     * example used as internet addresses. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. fhe context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> iri(@NonNull final Context context,
                                              @StringRes final int resourceId) {
        return new IRIValidator(context, resourceId);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid IRIs. IRIs are internationalized URLs according to RFC 3987, which are for
     * example used as internet addresses. Empty texts are also accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. fhe context may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> iri(@NonNull final Context context) {
        return new IRIValidator(context, R.string.default_error_message);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid phone numbers. Phone numbers, which are only consisting of numbers are
     * allowed as well as international phone numbers, e.g. +49 1624812382. Empty texts are also
     * accepted.
     *
     * @param errorMessage
     *         The error message, which should be shown, if the validation fails, as an instance of
     *         the type {@link CharSequence}. The error message may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> phoneNumber(@NonNull final CharSequence errorMessage) {
        return new PhoneNumberValidator(errorMessage);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid phone numbers. Phone numbers, which are only consisting of numbers are
     * allowed as well as international phone numbers, e.g. +49 1624812382. Empty texts are also
     * accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @param resourceId
     *         The resource ID of the string resource, which contains the error message, which
     *         should be set, as an {@link Integer} value. The resource ID must correspond to a
     *         valid string resource
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> phoneNumber(@NonNull final Context context,
                                                      @StringRes final int resourceId) {
        return new PhoneNumberValidator(context, resourceId);
    }

    /**
     * Creates and returns a validator, which allows to validate texts to ensure, that they
     * represent valid phone numbers. Phone numbers, which are only consisting of numbers are
     * allowed as well as international phone numbers, e.g. +49 1624812382. Empty texts are also
     * accepted.
     *
     * @param context
     *         The context, which should be used to retrieve the error message, as an instance of
     *         the class {@link Context}. The context may not be null
     * @return The validator, which has been created, as an instance of the type {@link Validator}
     */
    public static Validator<CharSequence> phoneNumber(@NonNull final Context context) {
        return new PhoneNumberValidator(context, R.string.default_error_message);
    }

}
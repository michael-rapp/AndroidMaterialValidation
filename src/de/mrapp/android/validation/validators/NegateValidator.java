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

import android.content.Context;
import de.mrapp.android.validation.Validator;
import static de.mrapp.android.validation.util.Condition.ensureNotNull;

/**
 * A validator, which allows to negate the result of an other validator.
 * 
 * @param <Type>
 *            The type of the values, which should be validated
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class NegateValidator<Type> extends AbstractValidator<Type> {

	/**
	 * The validator, whose result is negated.
	 */
	private Validator<Type> validator;

	/**
	 * Creates a new validator, which allows to negate the result of an other
	 * validator.
	 * 
	 * @param errorMessage
	 *            The error message, which should be shown, if the validation
	 *            fails, as an instance of the type {@link CharSequence}. The
	 *            error message may not be null
	 * @param validator
	 *            The validator, whose result should be negated, as an instance
	 *            of the type {@link Validator}. The validator may not be null
	 */
	public NegateValidator(final CharSequence errorMessage,
			final Validator<Type> validator) {
		super(errorMessage);
		setValidator(validator);
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
	 * @return The validator, which has been created, as an instance of the
	 *         class {@link DisjunctiveValidator}
	 */
	public static <Type> Validator<Type> create(
			final CharSequence errorMessage, final Validator<Type> validator) {
		return new NegateValidator<>(errorMessage, validator);
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
	 * @return The validator, which has been created, as an instance of the
	 *         class {@link DisjunctiveValidator}
	 */
	public static <Type> Validator<Type> create(final Context context,
			final int resourceId, final Validator<Type> validator) {
		return new NegateValidator<>(context, resourceId, validator);
	}

	/**
	 * Creates a new validator, which allows to negate the result of an other
	 * validator.
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
	 * @param validator
	 *            The validator, whose result should be negated, as an instance
	 *            of the type {@link Validator}. The validator may not be null
	 */
	public NegateValidator(final Context context, final int resourceId,
			final Validator<Type> validator) {
		super(context, resourceId);
		setValidator(validator);
	}

	/**
	 * Returns the validator, whose result is negated.
	 * 
	 * @return The validator, whose result is negated, as an instance of the
	 *         type {@link Validator}
	 */
	public final Validator<Type> getValidator() {
		return validator;
	}

	/**
	 * Sets the validator, whose result should be negated.
	 * 
	 * @param validator
	 *            The validator, which should be set, as an instance of the type
	 *            {@link Validator}. The validator may not be null
	 */
	public final void setValidator(final Validator<Type> validator) {
		ensureNotNull(validator, "The validator may not be null");
		this.validator = validator;
	}

	@Override
	public final boolean validate(final Type value) {
		return !getValidator().validate(value);
	}

}
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

/**
 * Defines the interface, a class, which should be able to validate values of a
 * specific type, must implement.
 * 
 * @param <Type>
 *            The type of the values, which should be validated
 * 
 * @author Michael Rapp
 *
 * @since 1.0.0
 */
public interface Validator<Type> {

	/**
	 * Validates a specific value.
	 * 
	 * @param value
	 *            The value, which should be validated, as an instance of the
	 *            generic type Type
	 * @return True, if the validation succeeded, false otherwise
	 */
	boolean validate(Type value);

	/**
	 * Returns the error message, which should be shown, if the validation
	 * fails.
	 * 
	 * @return The error message, which should be shown, if the validation
	 *         fails, as an instance of the type {@link CharSequence}. The error
	 *         message may not be null
	 */
	CharSequence getErrorMessage();

}
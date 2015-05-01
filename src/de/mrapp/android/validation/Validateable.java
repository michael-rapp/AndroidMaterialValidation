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

import java.util.Collection;

/**
 * Defines the interface, a view, whose value should be able to be validated,
 * must implement.
 * 
 * @param <Type>
 *            The type of the values, which should be validated
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface Validateable<Type> {

	/**
	 * Adds a new validator, which should be used to validate the view's value.
	 * The validators are applied in the order, they have been added to the
	 * view.
	 * 
	 * @param validator
	 *            The validator, which should be added, as an instance of the
	 *            type {@link Validator}. The validator may not be null
	 */
	void addValidator(Validator<Type> validator);

	/**
	 * Adds all validators, which are contained by a specific collection. The
	 * validators are applied in the given order.
	 * 
	 * @param validators
	 *            A collection, which contains the validators, which should be
	 *            added, as an instance of the type {@link Collection} or an
	 *            empty collection, if no validators should be added
	 */
	void addAllValidators(Collection<Validator<Type>> validators);

	/**
	 * Adds all validators, which are contained by a specific array. The
	 * validators are applied in the given order.
	 * 
	 * @param validators
	 *            An array, which contains the validators, which should be
	 *            added, as an array of the type {@link Validator} or an empty
	 *            array, if no validators should be added
	 */
	@SuppressWarnings("unchecked")
	void addAllValidators(Validator<Type>... validators);

	/**
	 * Removes a specific validator, which should not be used to validate the
	 * view's value, anymore.
	 * 
	 * @param validator
	 *            The validator, which should be removed, as an instance of the
	 *            type {@link Validator}. The validator may not be null
	 */
	void removeValidator(Validator<Type> validator);

	/**
	 * Removes all validators, which are contained by a specific collection.
	 * 
	 * @param validators
	 *            A collection, which contains the validators, which should be
	 *            removed, as an instance of the type {@link Collection} or an
	 *            empty collection, if no validators should be removed
	 */
	void removeAllValidators(Collection<Validator<Type>> validators);

	/**
	 * Removes all validators, which are contained by a specific array.
	 * 
	 * @param validators
	 *            An array, which contains the validators, which should be
	 *            removed, as an array of the type {@link Validator} or an empty
	 *            array, if no validators should be removed
	 */
	@SuppressWarnings("unchecked")
	void removeAllValidators(Validator<Type>... validators);

	/**
	 * Validates the current value of the view.
	 * 
	 * @return True, if the current value is valid, false otherwise
	 */
	boolean validate();

	/**
	 * Returns, whether the value of the view is automatically validated, when
	 * its value has been changed, or not.
	 * 
	 * @return True, if the value of the view is automatically validated, when
	 *         its value has been changed, false otherwise
	 */
	boolean isValidatedOnValueChange();

	/**
	 * Sets, whether the value of the view should automatically be validated,
	 * when its value has been changed, or not.
	 * 
	 * @param validateOnValueChange
	 *            True, if the value of the view should automatically be
	 *            validated, when its value has been changed, false otherwise
	 */
	void validateOnValueChange(boolean validateOnValueChange);

	/**
	 * Returns, whether the value of the view is automatically validated, when
	 * the view loses its focus, or not.
	 * 
	 * @return True, if the value of the view is automatically validated, when
	 *         the view loses its focus, false otherwise
	 */
	boolean isValidatedOnFocusLost();

	/**
	 * Sets, whether the value of the view should automatically be validated,
	 * when the view loses its focus, or not.
	 * 
	 * @param validateOnFocusLost
	 *            True, if the value of the view should automatically be
	 *            validated, when the view loses its focus, false otherwise
	 */
	void validateOnFocusLost(boolean validateOnFocusLost);

	/**
	 * Adds a new listener, which should be notified, when the view has been
	 * validated.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            type {@link ValidationListener}. The listener may not be null
	 */
	void addValidationListener(ValidationListener<Type> listener);

	/**
	 * Removes a specific listener, which should not be notified, when the view
	 * has been validated, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            type {@link ValidationListener}. The listener may not be null
	 */
	void removeValidationListener(ValidationListener<Type> listener);

}
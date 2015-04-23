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
 * Defines the interface, a class, which should be notified, when a view has
 * been validated, must implement.
 * 
 * @param <Type>
 *            The type of the values, which should be validated
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ValidationListener<Type> {

	/**
	 * The method, which is invoked, when a validation succeeded.
	 * 
	 * @param view
	 *            The view, whose value has been validated, as an instance of
	 *            the type {@link Validateable}
	 */
	void onValidationSuccess(Validateable<Type> view);

	/**
	 * The method, which is invoked, when a validation failed.
	 * 
	 * @param view
	 *            The view, whose value has been validated, as an instance of
	 *            the type {@link Validateable}
	 * @param validator
	 *            The validator, which has been failed, as an instance of the
	 *            type {@link Validator}
	 */
	void onValidationFailure(Validateable<Type> view, Validator<Type> validator);

}
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
 * Defines the interface, a class, which should be able to verify, whether a
 * specific type satisfies a specific constraint, must implement.
 * 
 * @param <Type>
 *            The type of the values, which should be verified
 * 
 * @author Michael Rapp
 *
 * @since 1.0.0
 */
public interface Constraint<Type> {

	/**
	 * Returns, whether a specific value satisfies the constraint, or not.
	 * 
	 * @param value
	 *            The value, which should be verified, as an instance of the
	 *            generic type Type
	 * @return True, if the given value satisfies the constraint, false
	 *         otherwise
	 */
	boolean isSatified(Type value);

}
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
package de.mrapp.android.validation.constraints.text;

import java.util.regex.Pattern;

/**
 * A constraint, which allows to verify texts in order to check, if they contain
 * at least one letter.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class ContainsLetterConstraint extends RegexConstraint {

	/**
	 * The regular expression, which is used by the constraint.
	 */
	private static final Pattern REGEX = Pattern.compile("(.)*([a-zA-Z])(.)*");

	/**
	 * Creates a new constraint, which allows to verify texts in order to check,
	 * if they contain at least one letter.
	 */
	public ContainsLetterConstraint() {
		super(REGEX);
	}

}
/*
 * AndroidMaterialValidation Copyright 2015 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package de.mrapp.android.validation;

/**
 * An implementation of the interface {@link ValidationListener}, which is needed for test
 * purposes.
 *
 * @author Michael Rapp
 */
public class ValidationListenerImplementation implements ValidationListener<CharSequence> {

    /**
     * True, if the onValidationSuccess-method has been called, false otherwise.
     */
    private boolean onValidationSuccess;

    /**
     * True, if the onValidationFailure-method has been called, false otherwise.
     */
    private boolean onValidationFailure;

    /**
     * Returns, whether the onValidationSuccess-method has been called, or not.
     *
     * @return True, if the onValidationSuccess-method has been called, false otherwise
     */
    public final boolean hasOnValidationSuccessBeenCalled() {
        return onValidationSuccess;
    }

    /**
     * Returns, whether the onValidationFailure-method has been called, or not.
     *
     * @return True, if the onValidationFailure-method has been called, false otherwise
     */
    public final boolean hasOnValidationFailureBeenCalled() {
        return onValidationFailure;
    }

    /**
     * Rests the listener.
     */
    public final void reset() {
        onValidationSuccess = false;
        onValidationFailure = false;
    }

    @Override
    public final void onValidationSuccess(final Validateable<CharSequence> view) {
        onValidationSuccess = true;
    }

    @Override
    public final void onValidationFailure(final Validateable<CharSequence> view,
                                          final Validator<CharSequence> validator) {
        onValidationFailure = true;
    }

}
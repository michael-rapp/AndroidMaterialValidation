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
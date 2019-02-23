/*
 * Copyright 2015 - 2019 Michael Rapp
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

import androidx.annotation.NonNull;

import java.util.Collection;

/**
 * Defines the interface, a view, whose value should be able to be validated, must implement.
 *
 * @param <Type>
 *         The type of the values, which should be validated
 * @author Michael Rapp
 * @since 1.0.0
 */
public interface Validateable<Type> {

    /**
     * Returns a collection, which contains all validators, which are used to validate the view's
     * value. The validators are applied in the given order.
     *
     * @return A collection, which contains the validators, which are used to validate the view's
     * value, as an instance of the type {@link Collection} or an empty collection, if no views are
     * used to validate the view's value
     */
    Collection<Validator<Type>> getValidators();

    /**
     * Adds a new validator, which should be used to validate the view's value. The validators are
     * applied in the order, they have been added to the view.
     *
     * @param validator
     *         The validator, which should be added, as an instance of the type {@link Validator}.
     *         The validator may not be null
     */
    void addValidator(@NonNull Validator<Type> validator);

    /**
     * Adds all validators, which are contained by a specific collection. The validators are applied
     * in the given order.
     *
     * @param validators
     *         A collection, which contains the validators, which should be added, as an instance of
     *         the type {@link Collection} or an empty collection, if no validators should be added
     */
    void addAllValidators(@NonNull Collection<Validator<Type>> validators);

    /**
     * Adds all validators, which are contained by a specific array. The validators are applied in
     * the given order.
     *
     * @param validators
     *         An array, which contains the validators, which should be added, as an array of the
     *         type {@link Validator} or an empty array, if no validators should be added
     */
    @SuppressWarnings("unchecked")
    void addAllValidators(@NonNull Validator<Type>... validators);

    /**
     * Removes a specific validator, which should not be used to validate the view's value,
     * anymore.
     *
     * @param validator
     *         The validator, which should be removed, as an instance of the type {@link Validator}.
     *         The validator may not be null
     */
    void removeValidator(@NonNull Validator<Type> validator);

    /**
     * Removes all validators, which are contained by a specific collection.
     *
     * @param validators
     *         A collection, which contains the validators, which should be removed, as an instance
     *         of the type {@link Collection} or an empty collection, if no validators should be
     *         removed
     */
    void removeAllValidators(@NonNull Collection<Validator<Type>> validators);

    /**
     * Removes all validators, which are contained by a specific array.
     *
     * @param validators
     *         An array, which contains the validators, which should be removed, as an array of the
     *         type {@link Validator} or an empty array, if no validators should be removed
     */
    @SuppressWarnings("unchecked")
    void removeAllValidators(@NonNull Validator<Type>... validators);

    /**
     * Removes all validators.
     */
    void removeAllValidators();

    /**
     * Validates the current value of the view.
     *
     * @return True, if the current value is valid, false otherwise
     */
    boolean validate();

    /**
     * Returns, whether the value of the view is automatically validated, when its value has been
     * changed, or not.
     *
     * @return True, if the value of the view is automatically validated, when its value has been
     * changed, false otherwise
     */
    boolean isValidatedOnValueChange();

    /**
     * Sets, whether the value of the view should automatically be validated, when its value has
     * been changed, or not.
     *
     * @param validateOnValueChange
     *         True, if the value of the view should automatically be validated, when its value has
     *         been changed, false otherwise
     */
    void validateOnValueChange(boolean validateOnValueChange);

    /**
     * Returns, whether the value of the view is automatically validated, when the view loses its
     * focus, or not.
     *
     * @return True, if the value of the view is automatically validated, when the view loses its
     * focus, false otherwise
     */
    boolean isValidatedOnFocusLost();

    /**
     * Sets, whether the value of the view should automatically be validated, when the view loses
     * its focus, or not.
     *
     * @param validateOnFocusLost
     *         True, if the value of the view should automatically be validated, when the view loses
     *         its focus, false otherwise
     */
    void validateOnFocusLost(boolean validateOnFocusLost);

    /**
     * Adds a new listener, which should be notified, when the view has been validated.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the type {@link
     *         ValidationListener}. The listener may not be null
     */
    void addValidationListener(@NonNull ValidationListener<Type> listener);

    /**
     * Removes a specific listener, which should not be notified, when the view has been validated,
     * anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the type {@link
     *         ValidationListener}. The listener may not be null
     */
    void removeValidationListener(@NonNull ValidationListener<Type> listener);

}
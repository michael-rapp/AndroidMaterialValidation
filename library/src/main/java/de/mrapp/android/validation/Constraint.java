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
 * Defines the interface, a class, which should be able to verify, whether a specific type satisfies
 * a constraint, must implement.
 *
 * @param <Type>
 *         The type of the values, which should be verified
 * @author Michael Rapp
 * @since 1.0.0
 */
public interface Constraint<Type> {

    /**
     * Returns, whether a specific value satisfies the constraint, or not.
     *
     * @param value
     *         The value, which should be verified, as an instance of the generic type Type
     * @return True, if the given value satisfies the constraint, false otherwise
     */
    boolean isSatisfied(Type value);

}
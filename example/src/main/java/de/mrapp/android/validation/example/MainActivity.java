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
package de.mrapp.android.validation.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import de.mrapp.android.validation.Constraints;
import de.mrapp.android.validation.EditText;
import de.mrapp.android.validation.PasswordEditText;
import de.mrapp.android.validation.Spinner;
import de.mrapp.android.validation.Validators;
import de.mrapp.android.validation.validators.text.Case;

/**
 * The example app's main activity.
 *
 * @author Michael Rapp
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The maximum number of characters of an edit text.
     */
    private static final int MAX_CHARACTERS = 32;

    /**
     * The minimum length of a password.
     */
    private static final int MIN_PASSWORD_LENGTH = 4;

    /**
     * The minimum length of a first or last name.
     */
    private static final int MIN_NAME_LENGTH = 2;

    /**
     * The suggested length of a password.
     */
    private static final int SUGGESTED_PASSWORD_LENGTH = 10;

    /**
     * The edit text, which allows to enter an username.
     */
    private EditText usernameEditText;

    /**
     * The edit text, which allows to enter a password.
     */
    private PasswordEditText passwordEditText;

    /**
     * The edit text, which allows to enter a password repetition.
     */
    private EditText passwordRepetitionEditText;

    /**
     * The spinner, which allows to choose a gender.
     */
    private Spinner genderSpinner;

    /**
     * The edit text, which allows to enter a first name.
     */
    private EditText firstNameEditText;

    /**
     * The edit text, which allows to enter a last name.
     */
    private EditText lastNameEditText;

    /**
     * The edit text, which allows to enter additional information.
     */
    private EditText additionalInformationEditText;

    /**
     * Initializes the edit text, which allows to enter an username.
     */
    private void initializeUsernameEditText() {
        usernameEditText = (EditText) findViewById(R.id.username_edit_text);
        usernameEditText.addValidator(
                Validators.notEmpty(this, R.string.not_empty_validator_error_message));
        usernameEditText.addValidator(Validators
                .maxLength(this, R.string.max_length_validator_error_messsage, MAX_CHARACTERS));
        usernameEditText.addValidator(Validators
                .letterOrNumber(this, R.string.letter_or_number_validator_error_message,
                        Case.CASE_INSENSITIVE, false, new char[]{'-', '_'}));
    }

    /**
     * Initializes the edit text, which allows to enter a password.
     */
    private void initializePasswordEditText() {
        passwordEditText = (PasswordEditText) findViewById(R.id.password_edit_text);
        passwordEditText.addValidator(Validators
                .minLength(this, R.string.password_min_length_validator_error_message,
                        MIN_PASSWORD_LENGTH));
        passwordEditText.addValidator(Validators
                .maxLength(this, R.string.max_length_validator_error_messsage, MAX_CHARACTERS));
        passwordEditText.addValidator(
                Validators.noWhitespace(this, R.string.no_whitespace_validator_error_message));
        passwordEditText.addAllConstraints(Constraints.minLength(SUGGESTED_PASSWORD_LENGTH),
                Constraints.containsLetter(), Constraints.containsNumber(),
                Constraints.containsSymbol());
        passwordEditText.addAllHelperTextIds(R.string.password_edit_text_helper_text0,
                R.string.password_edit_text_helper_text1, R.string.password_edit_text_helper_text2,
                R.string.password_edit_text_helper_text3, R.string.password_edit_text_helper_text4);
        passwordEditText.addAllHelperTextColorIds(R.color.password_edit_text_helper_text_color0,
                R.color.password_edit_text_helper_text_color1,
                R.color.password_edit_text_helper_text_color2,
                R.color.password_edit_text_helper_text_color3,
                R.color.password_edit_text_helper_text_color4);
    }

    /**
     * Initializes the edit text, which allows to enter a password repetition.
     */
    private void initializePasswordRepetitionEditText() {
        passwordRepetitionEditText = (EditText) findViewById(R.id.password_repetition_edit_text);
        passwordRepetitionEditText.addValidator(
                Validators.equal(this, R.string.equal_validator_error_message, passwordEditText));
    }

    /**
     * Initializes the spinner, which allows to choose a gender.
     */
    private void initializeGenderSpinner() {
        genderSpinner = (Spinner) findViewById(R.id.gender_spinner);
        genderSpinner
                .addValidator(Validators.notNull(this, R.string.not_null_validator_error_message));
    }

    /**
     * Initializes the edit text, which allows to enter a first name.
     */
    private void initializeFirstNameEditText() {
        firstNameEditText = (EditText) findViewById(R.id.first_name_edit_text);
        firstNameEditText.addValidator(Validators
                .minLength(this, R.string.min_length_validator_error_message, MIN_NAME_LENGTH));
        firstNameEditText.addValidator(Validators.beginsWithUppercaseLetter(this,
                R.string.begins_with_uppercase_letter_validator_error_message));
        firstNameEditText.addValidator(Validators
                .letter(this, R.string.letter_validator_error_message, Case.CASE_INSENSITIVE, false,
                        new char[]{'-'}));
    }

    /**
     * Initializes the edit text, which allows to enter a last name.
     */
    private void initializeLastNameEditText() {
        lastNameEditText = (EditText) findViewById(R.id.last_name_edit_text);
        lastNameEditText.addValidator(Validators
                .minLength(this, R.string.min_length_validator_error_message, MIN_NAME_LENGTH));
        lastNameEditText.addValidator(Validators.beginsWithUppercaseLetter(this,
                R.string.begins_with_uppercase_letter_validator_error_message));
        lastNameEditText.addValidator(Validators
                .letter(this, R.string.letter_validator_error_message, Case.CASE_INSENSITIVE, false,
                        new char[]{'-'}));
    }

    /**
     * Initializes the edit text, which allows to enter additional information.
     */
    private void initializeAdditionalInformationEditText() {
        additionalInformationEditText =
                (EditText) findViewById(R.id.additional_information_edit_text);
    }

    /**
     * Initializes the button, which allows to validate the values of all views.
     */
    private void initializeValidateButton() {
        Button button = (Button) findViewById(R.id.validate_button);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                usernameEditText.validate();
                passwordEditText.validate();
                passwordRepetitionEditText.validate();
                genderSpinner.validate();
                firstNameEditText.validate();
                lastNameEditText.validate();
                additionalInformationEditText.validate();
            }

        });
    }

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUsernameEditText();
        initializePasswordEditText();
        initializePasswordRepetitionEditText();
        initializeGenderSpinner();
        initializeFirstNameEditText();
        initializeLastNameEditText();
        initializeAdditionalInformationEditText();
        initializeValidateButton();
    }

}
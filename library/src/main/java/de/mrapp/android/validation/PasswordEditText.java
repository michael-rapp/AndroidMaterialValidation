/*
 * Copyright 2015 - 2018 Michael Rapp
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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static de.mrapp.android.util.Condition.ensureNotEmpty;
import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * A view, which allows to enter a password. The text may be validated according to the pattern,
 * which is suggested by the Material Design guidelines.
 *
 * Additionally, the password strength can be automatically verified, according to customizable
 * constraints, while typing and a text, which indicates the password strength can be shown as the
 * edit text's helper text.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public class PasswordEditText extends EditText {

    /**
     * A list, which contains the constraints, which are used to verify the password strength.
     */
    private List<Constraint<CharSequence>> constraints;

    /**
     * A list, which contains the helper texts, which are shown depending on the password strength.
     */
    private List<CharSequence> helperTexts;

    /**
     * A list, which contains the colors, which are used to highlight the helper texts, which are
     * shown depending on the password strength.
     */
    private List<Integer> helperTextColors;

    /**
     * The prefix of the helper texts, which are shown depending on the password strength.
     */
    private String passwordVerificationPrefix;

    /**
     * The helper text, which is shown, when the password strength is not verified.
     */
    private CharSequence regularHelperText;

    /**
     * The color of the helper text, which is used, when the password strength is not verified.
     */
    private int regularHelperTextColor;

    /**
     * Initializes the view.
     *
     * @param attributeSet
     *         The attribute set, the attributes should be obtained from, as an instance of the type
     *         {@link AttributeSet} or null, if no attributes should be obtained
     */
    private void initialize(@Nullable final AttributeSet attributeSet) {
        constraints = new ArrayList<>();
        helperTexts = new ArrayList<>();
        helperTextColors = new ArrayList<>();
        regularHelperText = getHelperText();
        regularHelperTextColor = getHelperTextColor();
        obtainStyledAttributes(attributeSet);
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        getView().addTextChangedListener(createTextChangeListener());
    }

    /**
     * Obtains all attributes from a specific attribute set.
     *
     * @param attributeSet
     *         The attribute set, the attributes should be obtained from, as an instance of the type
     *         {@link AttributeSet} or null, if no attributes should be obtained
     */
    private void obtainStyledAttributes(@Nullable final AttributeSet attributeSet) {
        TypedArray typedArray =
                getContext().obtainStyledAttributes(attributeSet, R.styleable.PasswordEditText);
        try {
            obtainPasswordVerificationPrefix(typedArray);
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * Obtains the prefix of helper texts, which are shown depending on the password strength, from
     * a specific typed array.
     *
     * @param typedArray
     *         The typed array, the prefix should be obtained from, as an instance of the class
     *         {@link TypedArray}. The typed array may not be null
     */
    private void obtainPasswordVerificationPrefix(@NonNull final TypedArray typedArray) {
        String format =
                typedArray.getString(R.styleable.PasswordEditText_passwordVerificationPrefix);

        if (format == null) {
            format = getResources().getString(R.string.password_verification_prefix);
        }

        setPasswordVerificationPrefix(format);
    }

    /**
     * Creates and returns a listener, which allows to verify the password strength, when the
     * password has been changed.
     *
     * @return The listener, which has been created, as an instance of the type {@link TextWatcher}
     */
    private TextWatcher createTextChangeListener() {
        return new TextWatcher() {

            @Override
            public final void beforeTextChanged(final CharSequence s, final int start,
                                                final int count, final int after) {

            }

            @Override
            public final void onTextChanged(final CharSequence s, final int start, final int before,
                                            final int count) {

            }

            @Override
            public final void afterTextChanged(final Editable s) {
                verifyPasswordStrength();
            }

        };
    }

    /**
     * Verifies the strength of the current password, depending on the constraints, which have been
     * added and adapts the appearance of the view accordingly.
     */
    private void verifyPasswordStrength() {
        if (isEnabled() && !constraints.isEmpty() && !TextUtils.isEmpty(getText())) {
            float score = getPasswordStrength();
            adaptHelperText(score);
        } else {
            setHelperText(regularHelperText);
        }
    }

    /**
     * Returns the strength of the current password, depending on the constraints, which have been
     * added.
     *
     * @return The fraction of constraints, which are satisfied, as a {@link Float} value between
     * 0.0 and 1.0
     */
    private float getPasswordStrength() {
        int absoluteScore = 0;
        CharSequence password = getView().getText();

        for (Constraint<CharSequence> constraint : constraints) {
            if (constraint.isSatisfied(password)) {
                absoluteScore++;
            }
        }

        return ((float) absoluteScore / (float) constraints.size());
    }

    /**
     * Adapts the helper text, depending on a specific password strength.
     *
     * @param score
     *         The password strength as a {@link Float} value between 0.0 and 1.0, which represents
     *         the fraction of constraints, which are satisfied
     */
    private void adaptHelperText(final float score) {
        if (!helperTexts.isEmpty()) {
            CharSequence helperText = getHelperText(score);

            if (helperText != null) {
                int color = getHelperTextColor(score);
                helperText = "<font color=\"" + color + "\">" + helperText + "</font>";
                String prefix = getPasswordVerificationPrefix();

                if (prefix != null) {
                    prefix = "<font color=\"" + regularHelperTextColor + "\">" + prefix +
                            ": </font>";
                } else {
                    prefix = "";
                }

                setHelperText(Html.fromHtml(prefix + helperText));
            } else {
                setHelperText(regularHelperText);
            }
        } else {
            setHelperText(regularHelperText);
        }
    }

    /**
     * Returns the helper text, which corresponds to a specific password strength.
     *
     * @param score
     *         The password strength as a {@link Float} value between 0.0 and 1.0, which represents
     *         the fraction of constraints, which are satisfied
     * @return The helper text as an instance of the type {@link CharSequence} or null, if no helper
     * text for the given password strength is available
     */
    private CharSequence getHelperText(final float score) {
        if (!helperTexts.isEmpty()) {
            float interval = 1.0f / helperTexts.size();
            int index = (int) Math.floor(score / interval) - 1;
            index = Math.max(index, 0);
            index = Math.min(index, helperTexts.size() - 1);
            return helperTexts.get(index);
        }

        return null;
    }

    /**
     * Returns the color of the helper text, which corresponds to a specific password strength.
     *
     * @param score
     *         The password strength as a {@link Float} value between 0.0 and 1.0, which represents
     *         the fraction of constraints, which are satisfied
     * @return The color of the helper text as an {@link Integer} value
     */
    private int getHelperTextColor(final float score) {
        if (!helperTextColors.isEmpty()) {
            float interval = 1.0f / helperTextColors.size();
            int index = (int) Math.floor(score / interval) - 1;
            index = Math.max(index, 0);
            index = Math.min(index, helperTextColors.size() - 1);
            return helperTextColors.get(index);
        }

        return regularHelperTextColor;
    }

    /**
     * Creates a new view, which allows to enter a password.
     *
     * @param context
     *         The context, which should be used by the view, as an instance of the class {@link
     *         Context}. The context may not be null
     */
    public PasswordEditText(@NonNull final Context context) {
        super(context);
        initialize(null);
    }

    /**
     * Creates a new view, which allows to enter a password.
     *
     * @param context
     *         The context, which should be used by the view, as an instance of the class {@link
     *         Context}. The context may not be null
     * @param attributeSet
     *         The attributes of the XML tag that is inflating the view, as an instance of the type
     *         {@link AttributeSet} or null, if no attributes are available
     */
    public PasswordEditText(@NonNull final Context context,
                            @Nullable final AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(attributeSet);
    }

    /**
     * Creates a new view, which allows to enter a password.
     *
     * @param context
     *         The context, which should be used by the view, as an instance of the class {@link
     *         Context}. The context may not be null
     * @param attributeSet
     *         The attributes of the XML tag that is inflating the view, as an instance of the type
     *         {@link AttributeSet} or null, if no attributes are available
     * @param defaultStyle
     *         The default style to apply to this preference. If 0, no style will be applied (beyond
     *         what is included in the theme). This may either be an attribute resource, whose value
     *         will be retrieved from the current theme, or an explicit style resource
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public PasswordEditText(@NonNull final Context context,
                            @Nullable final AttributeSet attributeSet, final int defaultStyle) {
        super(context, attributeSet, defaultStyle);
        initialize(attributeSet);
    }

    /**
     * Creates a new view, which allows to enter a password.
     *
     * @param context
     *         The context, which should be used by the view, as an instance of the class {@link
     *         Context}. The context may not be null
     * @param attributeSet
     *         The attributes of the XML tag that is inflating the view, as an instance of the type
     *         {@link AttributeSet} or null, if no attributes are available
     * @param defaultStyle
     *         The default style to apply to this preference. If 0, no style will be applied (beyond
     *         what is included in the theme). This may either be an attribute resource, whose value
     *         will be retrieved from the current theme, or an explicit style resource
     * @param defaultStyleResource
     *         A resource identifier of a style resource that supplies default values for the
     *         preference, used only if the default style is 0 or can not be found in the theme. Can
     *         be 0 to not look for defaults
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PasswordEditText(@NonNull final Context context,
                            @Nullable final AttributeSet attributeSet, final int defaultStyle,
                            final int defaultStyleResource) {
        super(context, attributeSet, defaultStyle, defaultStyleResource);
        initialize(attributeSet);
    }

    /**
     * Returns a collection, which contains the constraints, which are used to verify the password
     * strength.
     *
     * @return A collection, which contains the constraints, which are used to verify the password
     * strength, as an instance of the type {@link Collection} or an empty collection, if no
     * constraints are used to verify the password strength
     */
    public final Collection<Constraint<CharSequence>> getConstraints() {
        return constraints;
    }

    /**
     * Adds a new constraint, which should be used to verify the password strength.
     *
     * @param constraint
     *         The constraint, which should be added, as an instance of the type {@link Constraint}.
     *         The constraint may not be null
     */
    public final void addConstraint(@NonNull final Constraint<CharSequence> constraint) {
        ensureNotNull(constraint, "The constraint may not be null");

        if (!constraints.contains(constraint)) {
            constraints.add(constraint);
            verifyPasswordStrength();
        }
    }

    /**
     * Adds all constraints, which are contained by a specific collection.
     *
     * @param constraints
     *         A collection, which contains the constraints, which should be added, as an instance
     *         of the type {@link Collection} or an empty collection, if no constraints should be
     *         added
     */
    public final void addAllConstraints(
            @NonNull final Collection<Constraint<CharSequence>> constraints) {
        ensureNotNull(constraints, "The collection may not be null");

        for (Constraint<CharSequence> constraint : constraints) {
            addConstraint(constraint);
        }
    }

    /**
     * Adds all constraints, which are contained by a specific array.
     *
     * @param constraints
     *         An array, which contains the constraints, which should be added, as an array of the
     *         type {@link Constraint}, or an empty array, if no constraint should be added
     */
    @SafeVarargs
    public final void addAllConstraints(@NonNull final Constraint<CharSequence>... constraints) {
        ensureNotNull(constraints, "The array may not be null");
        addAllConstraints(Arrays.asList(constraints));
    }

    /**
     * Removes a specific constraint, which should not be used to verify the password strength,
     * anymore.
     *
     * @param constraint
     *         The constraint, which should be removed, as an instance of the type {@link
     *         Constraint}. The constraint may not be null
     */
    public final void removeConstraint(@NonNull final Constraint<CharSequence> constraint) {
        ensureNotNull(constraint, "The constraint may not be null");
        constraints.remove(constraint);
        verifyPasswordStrength();
    }

    /**
     * Removes all constraints, which are contained by a specific collection.
     *
     * @param constraints
     *         A collection, which contains the constraints, which should be removed, as an instance
     *         of the type {@link Collection} or an empty collection, if no constraints should be
     *         removed
     */
    public final void removeAllConstraints(
            @NonNull final Collection<Constraint<CharSequence>> constraints) {
        ensureNotNull(constraints, "The collection may not be null");

        for (Constraint<CharSequence> constraint : constraints) {
            removeConstraint(constraint);
        }
    }

    /**
     * Removes all constraints, which are contained by a specific array.
     *
     * @param constraints
     *         An array, which contains the constraints, which should be removed, as an array of the
     *         type {@link Constraint}, or an empty array, if no constraints should be removed
     */
    @SafeVarargs
    public final void removeAllConstraints(@NonNull final Constraint<CharSequence>... constraints) {
        ensureNotNull(constraints, "The array may not be null");
        removeAllConstraints(Arrays.asList(constraints));
    }

    /**
     * Removes all constraints.
     */
    public final void removeAllConstraints() {
        constraints.clear();
    }

    /**
     * Returns a collection, which contains the helper texts, which are shown, depending on the
     * password strength. Helper texts at higher indices are supposed to indicate a higher password
     * strength.
     *
     * @return A collection, which contains the helper texts, which are shown, depending on the
     * password strength, as an instance of the type {@link Collection} or an empty collection, if
     * no helper texts are shown depending on the password strength
     */
    public final Collection<CharSequence> getHelperTexts() {
        return helperTexts;
    }

    /**
     * Adds a new helper text, which should be shown, depending on the password strength. Helper
     * texts, which have been added later than others, are supposed to indicate a higher password
     * strength.
     *
     * @param helperText
     *         The helper text, which should be added, as an instance of the type {@link
     *         CharSequence}. The helper text may neither be null, nor empty
     */
    public final void addHelperText(@NonNull final CharSequence helperText) {
        ensureNotNull(helperText, "The helper text may not be null");
        ensureNotEmpty(helperText, "The helper text may not be empty");

        if (!helperTexts.contains(helperText)) {
            helperTexts.add(helperText);
            verifyPasswordStrength();
        }
    }

    /**
     * Adds a new helper text, which should be shown, depending on the password strength. Helper
     * texts, which have been added later than others, are supposed to indicate a higher password
     * strength.
     *
     * @param resourceId
     *         The resource ID of the helper text, which should be added, as an {@link Integer}
     *         value. The resource ID must correspond to a valid string resource
     */
    public final void addHelperTextId(@StringRes final int resourceId) {
        addHelperText(getResources().getText(resourceId));
    }

    /**
     * Adds all helper texts, which are contained by a specific collection. The helper texts are
     * added in the given order.
     *
     * @param helperTexts
     *         A collection, which contains the helper texts, which should be added, as an instance
     *         of the type {@link Collection} or an empty collection, if no helper texts should be
     *         added
     */
    public final void addAllHelperTexts(@NonNull final Collection<CharSequence> helperTexts) {
        ensureNotNull(helperTexts, "The collection may not be null");

        for (CharSequence helperText : helperTexts) {
            addHelperText(helperText);
        }
    }

    /**
     * Adds all helper texts, which are contained by a specific collection. The helper texts are
     * added in the given order.
     *
     * @param resourceIds
     *         A collection, which contains the resource IDs of the helper texts, which should be
     *         added, as an instance of the type {@link Collection} or an empty collection, if no
     *         helper texts should be added
     */
    public final void addAllHelperTextIds(@NonNull final Collection<Integer> resourceIds) {
        ensureNotNull(resourceIds, "The collection may not be null");

        for (int resourceId : resourceIds) {
            addHelperTextId(resourceId);
        }
    }

    /**
     * Adds all helper texts, which are contained by a specific array. The helper texts are added in
     * the given order.
     *
     * @param helperTexts
     *         An array, which contains the helper texts, which should be added, as an array of the
     *         type {@link CharSequence}, or an empty array, if no helper texts should be added
     */
    public final void addAllHelperTexts(@NonNull final CharSequence... helperTexts) {
        ensureNotNull(helperTexts, "The array may not be null");
        addAllHelperTexts(Arrays.asList(helperTexts));
    }

    /**
     * Adds all helper texts, which are contained by a specific array. The helper texts are added in
     * the given order.
     *
     * @param resourceIds
     *         An array, which contains the resource IDs of the helper texts, which should be added,
     *         as an array of the type {@link CharSequence}, or an empty array, if no helper texts
     *         should be added
     */
    public final void addAllHelperTextIds(@NonNull final int... resourceIds) {
        ensureNotNull(resourceIds, "The array may not be null");

        for (int resourceId : resourceIds) {
            addHelperTextId(resourceId);
        }
    }

    /**
     * Removes a specific helper text, which should not be shown, depending on the password
     * strength, anymore.
     *
     * @param helperText
     *         The helper text, which should be removed, as an instance of the type {@link
     *         CharSequence}. The helper text may neither be null, nor empty
     */
    public final void removeHelperText(@NonNull final CharSequence helperText) {
        ensureNotNull(helperText, "The helper text may not be null");
        ensureNotEmpty(helperText, "The helper text may not be empty");
        helperTexts.remove(helperText);
        verifyPasswordStrength();
    }

    /**
     * Removes a specific helper text, which should not be shown, depending on the password
     * strength, anymore.
     *
     * @param resourceId
     *         The resource ID of the helper text, which should be removed, as an {@link Integer}
     *         value. The resource ID must correspond to a valid string resource
     */
    public final void removeHelperTextId(@StringRes final int resourceId) {
        removeHelperText(getResources().getText(resourceId));
    }

    /**
     * Removes all helper texts, which are contained by a specific collection.
     *
     * @param helperTexts
     *         A collection, which contains the helper texts, which should be removed, as an
     *         instance of the type {@link Collection} or an empty collection, if no helper texts
     *         should be removed
     */
    public final void removeAllHelperTexts(@NonNull final Collection<CharSequence> helperTexts) {
        ensureNotNull(helperTexts, "The collection may not be null");

        for (CharSequence helperText : helperTexts) {
            removeHelperText(helperText);
        }
    }

    /**
     * Removes all helper texts, which are contained by a specific collection.
     *
     * @param resourceIds
     *         A collection, which contains the resource IDs of the helper texts, which should be
     *         removed, as an instance of the type {@link Collection} or an empty collection, if no
     *         helper texts should be removed
     */
    public final void removeAllHelperTextIds(@NonNull final Collection<Integer> resourceIds) {
        ensureNotNull(resourceIds, "The collection may not be null");

        for (int resourceId : resourceIds) {
            removeHelperTextId(resourceId);
        }
    }

    /**
     * Removes all helper texts, which are contained by a specific array.
     *
     * @param helperTexts
     *         An array, which contains the helper texts, which should be removed, as an array of
     *         the type {@link CharSequence}, or an empty array, if no helper texts should be
     *         removed
     */
    public final void removeAllHelperTexts(@NonNull final CharSequence... helperTexts) {
        ensureNotNull(helperTexts, "The array may not be null");
        removeAllHelperTexts(Arrays.asList(helperTexts));
    }

    /**
     * Removes all helper texts, which are contained by a specific array.
     *
     * @param resourceIds
     *         An array, which contains the resource IDs of the helper texts, which should be
     *         removed, as an array of the type {@link CharSequence}, or an empty array, if no
     *         helper texts should be removed
     */
    public final void removeAllHelperTextIds(@NonNull final int... resourceIds) {
        ensureNotNull(resourceIds, "The array may not be null");

        for (int resourceId : resourceIds) {
            removeHelperTextId(resourceId);
        }
    }

    /**
     * Removes all helper texts, which are shown, depending on the password strength.
     */
    public final void removeAllHelperTexts() {
        helperTexts.clear();
    }

    /**
     * Returns a collection, which contains all text colors, which are used to highlight the helper
     * text, which indicates the password strength.
     *
     * @return A collection, which contains all text colors, which are used to highlight the helper
     * text, which indicates the password strength, as an instance of the type {@link Collection} or
     * an empty collection, if no text colors are used to highlight the helper text
     */
    public final Collection<Integer> getHelperTextColors() {
        return helperTextColors;
    }

    /**
     * Adds a new helper text color, which should be used to highlight the helper text, which
     * indicates the password strength.
     *
     * @param color
     *         The color, which should be added, as an {@link Integer} value
     */
    public final void addHelperTextColor(@ColorInt final int color) {
        if (!helperTextColors.contains(color)) {
            helperTextColors.add(color);
            verifyPasswordStrength();
        }
    }

    /**
     * Adds a new helper text color, which should be used to highlight the helper text, which
     * indicates the password strength.
     *
     * @param resourceId
     *         The resource ID of the color, which should be added, as an {@link Integer} value. The
     *         resource ID must correspond to a valid color resource
     */
    public final void addHelperTextColorId(@ColorRes final int resourceId) {
        addHelperTextColor(ContextCompat.getColor(getContext(), resourceId));
    }

    /**
     * Adds all helper text colors, which are contained by a specific collection.
     *
     * @param colors
     *         A collection, which contains the colors, which should be added, as an instance of the
     *         type {@link Collection} or an empty collection, if no colors should be added
     */
    public final void addAllHelperTextColors(@NonNull final Collection<Integer> colors) {
        ensureNotNull(colors, "The collection may not be null");

        for (int color : colors) {
            addHelperTextColor(color);
        }
    }

    /**
     * Adds all helper text colors, which are contained by a specific collection.
     *
     * @param resourceIds
     *         A collection, which contains the resource IDs of the colors, which should be added,
     *         as an instance of the type {@link Collection} or an empty collection, if no colors
     *         should be added
     */
    public final void addAllHelperTextColorIds(@NonNull final Collection<Integer> resourceIds) {
        ensureNotNull(resourceIds, "The collection may not be null");

        for (int resourceId : resourceIds) {
            addHelperTextColorId(resourceId);
        }
    }

    /**
     * Adds all helper text colors, which are contained by a specific array.
     *
     * @param colors
     *         An array, which contains the helper text colors, which should be added, as an {@link
     *         Integer} array or an empty array, if no colors should be added
     */
    public final void addAllHelperTextColors(@NonNull final int... colors) {
        ensureNotNull(colors, "The array may not be null");

        for (int color : colors) {
            addHelperTextColor(color);
        }
    }

    /**
     * Adds all helper text colors, which are contained by a specific array.
     *
     * @param resourceIds
     *         An array, which contains the resource IDs of the helper text colors, which should be
     *         added, as an {@link Integer} array or an empty array, if no colors should be added
     */
    public final void addAllHelperTextColorIds(@NonNull final int... resourceIds) {
        ensureNotNull(resourceIds, "The array may not be null");

        for (int resourceId : resourceIds) {
            addHelperTextColorId(resourceId);
        }
    }

    /**
     * Removes a specific helper text color, which should not be used to highlight the helper text,
     * which indicates the password strength, anymore.
     *
     * @param color
     *         The color, which should be removed, as an {@link Integer} value
     */
    public final void removeHelperTextColor(@ColorInt final int color) {
        int index = helperTextColors.indexOf(color);

        if (index != -1) {
            helperTextColors.remove(index);
            verifyPasswordStrength();
        }
    }

    /**
     * Removes a specific helper text color, which should not be used to highlight the helper text,
     * which indicates the password strength, anymore.
     *
     * @param resourceId
     *         The resource ID of the color, which should be removed, as an {@link Integer} value.
     *         The resource ID must correspond to a valid color resource
     */
    public final void removeHelperTextColorId(@ColorRes final int resourceId) {
        removeHelperTextColor(ContextCompat.getColor(getContext(), resourceId));
    }

    /**
     * Removes all helper text colors, which are contained by a specific collection.
     *
     * @param colors
     *         A collection, which contains the colors, which should be removed, as an instance of
     *         the type {@link Collection} or an empty collection, if no colors should be removed
     */
    public final void removeAllHelperTextColors(@NonNull final Collection<Integer> colors) {
        ensureNotNull(colors, "The collection may not be null");

        for (int color : colors) {
            removeHelperTextColor(color);
        }
    }

    /**
     * Removes all helper text colors, which are contained by a specific collection.
     *
     * @param resourceIds
     *         A collection, which contains the resource IDs of the colors, which should be removed,
     *         as an instance of the type {@link Collection} or an empty collection, if no colors
     *         should be removed
     */
    public final void removeAllHelperTextColorIds(@NonNull final Collection<Integer> resourceIds) {
        ensureNotNull(resourceIds, "The collection may not be null");

        for (int resourceId : resourceIds) {
            removeHelperTextColorId(resourceId);
        }
    }

    /**
     * Removes all helper text colors, which are contained by a specific array.
     *
     * @param colors
     *         An array, which contains the colors, which should be removed, as an {@link Integer}
     *         array or an empty array, if no colors should be removed
     */
    public final void removeAllHelperTextColors(@NonNull final int... colors) {
        ensureNotNull(colors, "The array may not be null");

        for (int color : colors) {
            removeHelperTextColor(color);
        }
    }

    /**
     * Removes all helper text colors, which are contained by a specific array.
     *
     * @param resourceIds
     *         An array, which contains the resourceIDs of the colors, which should be removed, as
     *         an {@link Integer} array or an empty array, if no colors should be removed
     */
    public final void removeAllHelperTextColorIds(@NonNull final int... resourceIds) {
        ensureNotNull(resourceIds, "The array may not be null");

        for (int resourceId : resourceIds) {
            removeHelperTextColorId(resourceId);
        }
    }

    /**
     * Removes all text colors, which are used to highlight the helper text, which indicates the
     * password strength.
     */
    public final void removeAllHelperTextColors() {
        helperTextColors.clear();
    }

    /**
     * Returns the prefix of the helper texts, which are shown depending on the password strength.
     *
     * @return The prefix of the helper texts, which are shown depending on the password strength,
     * as a {@link String}
     */
    public final String getPasswordVerificationPrefix() {
        return passwordVerificationPrefix;
    }

    /**
     * Sets the prefix of the helper texts, which are shown depending on the password strength.
     *
     * @param prefix
     *         The prefix, which should be set, as a {@link String} or null, if no prefix should be
     *         set
     */
    public final void setPasswordVerificationPrefix(@Nullable final String prefix) {
        this.passwordVerificationPrefix = prefix;
        verifyPasswordStrength();
    }

    /**
     * Sets the format of the helper texts, which are shown depending on the password strength.
     *
     * @param resourceId
     *         The resourceID of the format, which should be set, as an {@link Integer} value. The
     *         resource ID must correspond to a valid string resource
     */
    public final void setPasswordVerificationPrefix(@StringRes final int resourceId) {
        setPasswordVerificationPrefix(getResources().getString(resourceId));
    }

    @Override
    public final void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        verifyPasswordStrength();
    }

}
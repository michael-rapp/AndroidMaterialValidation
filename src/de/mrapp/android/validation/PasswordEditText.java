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

import static de.mrapp.android.validation.util.Condition.ensureNotEmpty;
import static de.mrapp.android.validation.util.Condition.ensureNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * A view, which allows to enter a password. The text may be validated according
 * to the pattern, which is suggested by the Material Design guidelines.
 * 
 * Additionally, the password safety can be automatically verified, according to
 * customizable constraints, while typing and a text, which indicates the
 * password safety can be shown as the edit text's helper text.
 * 
 * @author Michael Rapp
 *
 * @since 1.0.0
 */
public class PasswordEditText extends EditText {

	/**
	 * A list, which contains the constraints, which are used to verify the
	 * password safety.
	 */
	private List<Constraint<CharSequence>> constraints;

	/**
	 * A list, which contains the helper texts, which are shown depending on the
	 * password safety.
	 */
	private List<CharSequence> helperTexts;

	/**
	 * A list, which contains the colors, which are used to highlight the helper
	 * texts, which are shown depending on the password safety.
	 */
	private List<Integer> helperTextColors;

	/**
	 * The prefix of the helper texts, which are shown depending on the password
	 * safety.
	 */
	private String passwordVerificationPrefix;

	/**
	 * The helper text, which is shown, when the password safety is not
	 * verified.
	 */
	private CharSequence regularHelperText;

	/**
	 * The color of the helper text, which is used, when the password safety is
	 * not verified.
	 */
	private int regularHelperTextColor;

	/**
	 * Initializes the view.
	 * 
	 * @param attributeSet
	 *            The attribute set, the attributes should be obtained from, as
	 *            an instance of the type {@link AttributeSet}
	 */
	private void initialize(final AttributeSet attributeSet) {
		constraints = new ArrayList<>();
		helperTexts = new ArrayList<>();
		helperTextColors = new ArrayList<>();
		regularHelperText = getHelperText();
		regularHelperTextColor = getHelperTextColor();
		setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		getView().addTextChangedListener(createTextChangeListener());
		obtainStyledAttributes(attributeSet);
	}

	/**
	 * Obtains all attributes from a specific attribute set.
	 * 
	 * @param attributeSet
	 *            The attribute set, the attributes should be obtained from, as
	 *            an instance of the type {@link AttributeSet}
	 */
	private void obtainStyledAttributes(final AttributeSet attributeSet) {
		TypedArray typedArray = getContext().obtainStyledAttributes(
				attributeSet, R.styleable.PasswordEditText);
		try {
			obtainPasswordVerificationPrefix(typedArray);
		} finally {
			typedArray.recycle();
		}
	}

	/**
	 * Obtains the prefix of helper texts, which are shown depending on the
	 * password safety, from a specific typed array.
	 * 
	 * @param typedArray
	 *            The typed array, the prefix should be obtained from, as an
	 *            instance of the class {@link TypedArray}
	 */
	private void obtainPasswordVerificationPrefix(final TypedArray typedArray) {
		String format = typedArray
				.getString(R.styleable.PasswordEditText_passwordVerificationPrefix);

		if (format == null) {
			format = getResources().getString(
					R.string.password_verification_prefix);
		}

		setPasswordVerificationPrefix(format);
	}

	/**
	 * Creates and returns a listener, which allows to verify the password
	 * safety, when the password has been changed.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link TextWatcher}
	 */
	private TextWatcher createTextChangeListener() {
		return new TextWatcher() {

			@Override
			public final void beforeTextChanged(final CharSequence s,
					final int start, final int count, final int after) {
				return;
			}

			@Override
			public final void onTextChanged(final CharSequence s,
					final int start, final int before, final int count) {
				return;
			}

			@Override
			public final void afterTextChanged(final Editable s) {
				verifyPasswordSafety();
			}

		};
	}

	/**
	 * Verifies the safety of the current password, depending on the
	 * constraints, which have been added and adapts the appearance of the view
	 * accordingly.
	 */
	private void verifyPasswordSafety() {
		if (isEnabled() && !constraints.isEmpty()
				&& !TextUtils.isEmpty(getText())) {
			float score = getPasswordSafety();
			adaptHelperText(score);
		} else {
			setHelperText(regularHelperText);
		}
	}

	/**
	 * Returns the safety of the current password, depending on the constraints,
	 * which have been added.
	 * 
	 * @return The fraction of constraints, which are satisfied, as a
	 *         {@link Float} value between 0.0 and 1.0
	 */
	private float getPasswordSafety() {
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
	 * Adapts the helper text, depending on a specific password safety.
	 * 
	 * @param score
	 *            The password safety as a {@link Float} value between 0.0 and
	 *            1.0, which represents the fraction of constraints, which are
	 *            satisfied
	 */
	private void adaptHelperText(final float score) {
		if (!helperTexts.isEmpty()) {
			String helperText = getHelperText(score).toString();
			int color = getHelperTextColor(score);
			helperText = "<font color=\"" + color + "\">" + helperText
					+ "</font>";
			String prefix = getPasswordVerificationPrefix();

			if (prefix != null) {
				prefix = "<font color=\"" + regularHelperTextColor + "\">"
						+ prefix + ": </font>";
			} else {
				prefix = "";
			}

			setHelperText(Html.fromHtml(prefix + helperText));
		} else {
			setHelperText(regularHelperText);
		}
	}

	/**
	 * Returns the helper text, which corresponds to a specific password safety.
	 * 
	 * @param score
	 *            The password safety as a {@link Float} value between 0.0 and
	 *            1.0, which represents the fraction of constraints, which are
	 *            satisfied
	 * @return The helper text as an instance of the type {@link CharSequence}
	 */
	private CharSequence getHelperText(final float score) {
		if (!helperTexts.isEmpty()) {
			float interval = 1.0f / helperTexts.size();
			int index = (int) Math.floor(score / interval);
			index = Math.min(index, helperTexts.size() - 1);
			return helperTexts.get(index);
		}

		return null;
	}

	/**
	 * Returns the color of the helper text, which corresponds to a specific
	 * password safety.
	 * 
	 * @param score
	 *            The password safety as a {@link Float} value between 0.0 and
	 *            1.0, which represents the fraction of constraints, which are
	 *            satisfied
	 * @return The color of the helper text as an {@link Integer} value
	 */
	private int getHelperTextColor(final float score) {
		if (!helperTextColors.isEmpty()) {
			float interval = 1.0f / helperTextColors.size();
			int index = (int) Math.floor(score / interval);
			index = Math.min(index, helperTextColors.size() - 1);
			return helperTextColors.get(index);
		}

		return regularHelperTextColor;
	}

	/**
	 * Creates a new view, which allows to enter a password.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 */
	public PasswordEditText(final Context context) {
		super(context);
		initialize(null);
	}

	/**
	 * Creates a new view, which allows to enter a password.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 * @param attributeSet
	 *            The attributes of the XML tag that is inflating the view, as
	 *            an instance of the type {@link AttributeSet}
	 */
	public PasswordEditText(final Context context,
			final AttributeSet attributeSet) {
		super(context, attributeSet);
		initialize(attributeSet);
	}

	/**
	 * Creates a new view, which allows to enter a password.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 * @param attributeSet
	 *            The attributes of the XML tag that is inflating the view, as
	 *            an instance of the type {@link AttributeSet}
	 * @param defaultStyle
	 *            The default style to apply to this preference. If 0, no style
	 *            will be applied (beyond what is included in the theme). This
	 *            may either be an attribute resource, whose value will be
	 *            retrieved from the current theme, or an explicit style
	 *            resource
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public PasswordEditText(final Context context,
			final AttributeSet attributeSet, final int defaultStyle) {
		super(context, attributeSet, defaultStyle);
		initialize(attributeSet);
	}

	/**
	 * Creates a new view, which allows to enter a password.
	 * 
	 * @param context
	 *            The context, which should be used by the view, as an instance
	 *            of the class {@link Context}
	 * @param attributeSet
	 *            The attributes of the XML tag that is inflating the view, as
	 *            an instance of the type {@link AttributeSet}
	 * @param defaultStyle
	 *            The default style to apply to this preference. If 0, no style
	 *            will be applied (beyond what is included in the theme). This
	 *            may either be an attribute resource, whose value will be
	 *            retrieved from the current theme, or an explicit style
	 *            resource
	 * @param defaultStyleResource
	 *            A resource identifier of a style resource that supplies
	 *            default values for the preference, used only if the default
	 *            style is 0 or can not be found in the theme. Can be 0 to not
	 *            look for defaults
	 */
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public PasswordEditText(final Context context,
			final AttributeSet attributeSet, final int defaultStyle,
			final int defaultStyleResource) {
		super(context, attributeSet, defaultStyle, defaultStyleResource);
		initialize(attributeSet);
	}

	/**
	 * Returns a collection, which contains the constraints, which are used to
	 * verify the password safety.
	 * 
	 * @return A collection, which contains the constraints, which are used to
	 *         verify the password safety, as an instance of the type
	 *         {@link Collection} or an empty collection, if no constraints are
	 *         used to verify the password safety
	 */
	public final Collection<Constraint<CharSequence>> getConstraints() {
		return constraints;
	}

	/**
	 * Adds a new constraint, which should be used to verify the password
	 * safety.
	 * 
	 * @param constraint
	 *            The constraint, which should be added, as an instance of the
	 *            type {@link Constraint}. The constraint may not be null
	 */
	public final void addConstraint(final Constraint<CharSequence> constraint) {
		ensureNotNull(constraint, "The constraint may not be null");
		constraints.add(constraint);
		verifyPasswordSafety();
	}

	/**
	 * Adds all constraints, which are contained by a specific collection.
	 * 
	 * @param constraints
	 *            A collection, which contains the constraints, which should be
	 *            added, as an instance of the type {@link Collection} or an
	 *            empty collection, if no constraints should be added
	 */
	public final void addAllConstraints(
			final Collection<Constraint<CharSequence>> constraints) {
		ensureNotNull(constraints, "The collection may not be null");

		for (Constraint<CharSequence> constraint : constraints) {
			addConstraint(constraint);
		}
	}

	/**
	 * Adds all constraints, which are contained by a specific array.
	 * 
	 * @param constraints
	 *            An array, which contains the constraints, which should be
	 *            added, as an array of the type {@link Constraint}, or an empty
	 *            array, if no constraint should be added
	 */
	@SafeVarargs
	public final void addAllConstraints(
			final Constraint<CharSequence>... constraints) {
		ensureNotNull(constraints, "The array may not be null");
		addAllConstraints(Arrays.asList(constraints));
	}

	/**
	 * Removes a specific constraint, which should not be used to verify the
	 * password safety, anymore.
	 * 
	 * @param constraint
	 *            The constraint, which should be removed, as an instance of the
	 *            type {@link Constraint}. The constraint may not be null
	 */
	public final void removeConstraint(final Constraint<CharSequence> constraint) {
		ensureNotNull(constraint, "The constraint may not be null");
		constraints.remove(constraint);
		verifyPasswordSafety();
	}

	/**
	 * Removes all constraints, which are contained by a specific collection.
	 * 
	 * @param constraints
	 *            A collection, which contains the constraints, which should be
	 *            removed, as an instance of the type {@link Collection} or an
	 *            empty collection, if no constraints should be removed
	 */
	public final void removeAllConstraints(
			final Collection<Constraint<CharSequence>> constraints) {
		ensureNotNull(constraints, "The collection may not be null");

		for (Constraint<CharSequence> constraint : constraints) {
			removeConstraint(constraint);
		}
	}

	/**
	 * Removes all constraints, which are contained by a specific array.
	 * 
	 * @param constraints
	 *            An array, which contains the constraints, which should be
	 *            removed, as an array of the type {@link Constraint}, or an
	 *            empty array, if no constraints should be removed
	 */
	@SafeVarargs
	public final void removeAllConstraints(
			final Constraint<CharSequence>... constraints) {
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
	 * Returns a collection, which contains the helper texts, which are shown,
	 * depending on the password safety. Helper texts at higher indices are
	 * supposed to indicate a higher password safety.
	 * 
	 * @return A collection, which contains the helper texts, which are shown,
	 *         depending on the password safety, as an instance of the type
	 *         {@link Collection} or an empty collection, if no helper texts are
	 *         shown depending on the password safety
	 */
	public final Collection<CharSequence> getHelperTexts() {
		return helperTexts;
	}

	/**
	 * Adds a new helper text, which should be shown, depending on the password
	 * safety. Helper texts, which have been added later than others, are
	 * supposed to indicate a higher password safety.
	 * 
	 * @param helperText
	 *            The helper text, which should be added, as an instance of the
	 *            type {@link CharSequence}. The helper text may neither be
	 *            null, nor empty
	 */
	public final void addHelperText(final CharSequence helperText) {
		ensureNotNull(helperText, "The helper text may not be null");
		ensureNotEmpty(helperText, "The helper text may not be empty");
		helperTexts.add(helperText);
		verifyPasswordSafety();
	}

	/**
	 * Adds a new helper text, which should be shown, depending on the password
	 * safety. Helper texts, which have been added later than others, are
	 * supposed to indicate a higher password safety.
	 * 
	 * @param resourceId
	 *            The resource ID of the helper text, which should be added, as
	 *            an {@link Integer} value. The resource ID must correspond to a
	 *            valid string resource
	 */
	public final void addHelperTextId(final int resourceId) {
		addHelperText(getResources().getText(resourceId));
	}

	/**
	 * Adds all helper texts, which are contained by a specific collection. The
	 * helper texts are added in the given order.
	 * 
	 * @param helperTexts
	 *            A collection, which contains the helper texts, which should be
	 *            added, as an instance of the type {@link Collection} or an
	 *            empty collection, if no helper texts should be added
	 */
	public final void addAllHelperTexts(
			final Collection<CharSequence> helperTexts) {
		ensureNotNull(helperTexts, "The collection may not be null");

		for (CharSequence helperText : helperTexts) {
			addHelperText(helperText);
		}
	}

	/**
	 * Adds all helper texts, which are contained by a specific collection. The
	 * helper texts are added in the given order.
	 * 
	 * @param resourceIds
	 *            A collection, which contains the resource IDs of the helper
	 *            texts, which should be added, as an instance of the type
	 *            {@link Collection} or an empty collection, if no helper texts
	 *            should be added
	 */
	public final void addAllHelperTextIds(final Collection<Integer> resourceIds) {
		ensureNotNull(resourceIds, "The collection may not be null");

		for (int resourceId : resourceIds) {
			addHelperTextId(resourceId);
		}
	}

	/**
	 * Adds all helper texts, which are contained by a specific array. The
	 * helper texts are added in the given order.
	 * 
	 * @param helperTexts
	 *            An array, which contains the helper texts, which should be
	 *            added, as an array of the type {@link CharSequence}, or an
	 *            empty array, if no helper texts should be added
	 */
	public final void addAllHelperTexts(final CharSequence... helperTexts) {
		ensureNotNull(helperTexts, "The array may not be null");
		addAllHelperTexts(Arrays.asList(helperTexts));
	}

	/**
	 * Adds all helper texts, which are contained by a specific array. The
	 * helper texts are added in the given order.
	 * 
	 * @param resourceIds
	 *            An array, which contains the resource IDs of the helper texts,
	 *            which should be added, as an array of the type
	 *            {@link CharSequence}, or an empty array, if no helper texts
	 *            should be added
	 */
	public final void addAllHelperTextIds(final int... resourceIds) {
		ensureNotNull(resourceIds, "The array may not be null");

		for (int resourceId : resourceIds) {
			addHelperTextId(resourceId);
		}
	}

	/**
	 * Removes a specific helper text, which should not be shown, depending on
	 * the password safety, anymore.
	 * 
	 * @param helperText
	 *            The helper text, which should be removed, as an instance of
	 *            the type {@link CharSequence}. The helper text may neither be
	 *            null, nor empty
	 */
	public final void removeHelperText(final CharSequence helperText) {
		ensureNotNull(helperText, "The helper text may not be null");
		ensureNotEmpty(helperText, "The helper text may not be empty");
		helperTexts.remove(helperText);
		verifyPasswordSafety();
	}

	/**
	 * Removes a specific helper text, which should not be shown, depending on
	 * the password safety, anymore.
	 * 
	 * @param resourceId
	 *            The resource ID of the helper text, which should be removed,
	 *            as an {@link Integer} value. The resource ID must correspond
	 *            to a valid string resource
	 */
	public final void removeHelperTextId(final int resourceId) {
		removeHelperText(getResources().getText(resourceId));
	}

	/**
	 * Removes all helper texts, which are contained by a specific collection.
	 * 
	 * @param helperTexts
	 *            A collection, which contains the helper texts, which should be
	 *            removed, as an instance of the type {@link Collection} or an
	 *            empty collection, if no helper texts should be removed
	 */
	public final void removeAllHelperTexts(
			final Collection<CharSequence> helperTexts) {
		ensureNotNull(helperTexts, "The collection may not be null");

		for (CharSequence helperText : helperTexts) {
			removeHelperText(helperText);
		}
	}

	/**
	 * Removes all helper texts, which are contained by a specific collection.
	 * 
	 * @param resourceIds
	 *            A collection, which contains the resource IDs of the helper
	 *            texts, which should be removed, as an instance of the type
	 *            {@link Collection} or an empty collection, if no helper texts
	 *            should be removed
	 */
	public final void removeAllHelperTextIds(
			final Collection<Integer> resourceIds) {
		ensureNotNull(resourceIds, "The collection may not be null");

		for (int resourceId : resourceIds) {
			removeHelperTextId(resourceId);
		}
	}

	/**
	 * Removes all helper texts, which are contained by a specific array.
	 * 
	 * @param helperTexts
	 *            An array, which contains the helper texts, which should be
	 *            removed, as an array of the type {@link CharSequence}, or an
	 *            empty array, if no helper texts should be removed
	 */
	public final void removeAllHelperTexts(final CharSequence... helperTexts) {
		ensureNotNull(helperTexts, "The array may not be null");
		removeAllHelperTexts(Arrays.asList(helperTexts));
	}

	/**
	 * Removes all helper texts, which are contained by a specific array.
	 * 
	 * @param resourceIds
	 *            An array, which contains the resource IDs of the helper texts,
	 *            which should be removed, as an array of the type
	 *            {@link CharSequence}, or an empty array, if no helper texts
	 *            should be removed
	 */
	public final void removeAllHelperTextIds(final int... resourceIds) {
		ensureNotNull(resourceIds, "The array may not be null");

		for (int resourceId : resourceIds) {
			removeHelperTextId(resourceId);
		}
	}

	/**
	 * Removes all helper texts, which are shown, depending on the password
	 * safety.
	 */
	public final void removeAllHelperTexts() {
		helperTexts.clear();
	}

	/**
	 * Returns a collection, which contains all text colors, which are used to
	 * highlight the helper text, which indicates the password safety.
	 * 
	 * @return A collection, which contains all text colors, which are used to
	 *         highlight the helper text, which indicates the password safety,
	 *         as an instance of the type {@link Collection} or an empty
	 *         collection, if no text colors are used to highlight the helper
	 *         text
	 */
	public final Collection<Integer> getHelperTextColors() {
		return helperTextColors;
	}

	/**
	 * Adds a new helper text color, which should be used to highlight the
	 * helper text, which indicates the password safety.
	 * 
	 * @param color
	 *            The color, which should be added, as an {@link Integer} value
	 */
	public final void addHelperTextColor(final int color) {
		helperTextColors.add(color);
		verifyPasswordSafety();
	}

	/**
	 * Adds a new helper text color, which should be used to highlight the
	 * helper text, which indicates the password safety.
	 * 
	 * @param resourceId
	 *            The resource ID of the color, which should be added, as an
	 *            {@link Integer} value. The resource ID must correspond to a
	 *            valid color resource
	 */
	public final void addHelperTextColorId(final int resourceId) {
		addHelperTextColor(getResources().getColor(resourceId));
	}

	/**
	 * Adds all helper text colors, which are contained by a specific
	 * collection.
	 * 
	 * @param colors
	 *            A collection, which contains the colors, which should be
	 *            added, as an instance of the type {@link Collection} or an
	 *            empty collection, if no colors should be added
	 */
	public final void addAllHelperTextColors(final Collection<Integer> colors) {
		ensureNotNull(colors, "The collection may not be null");

		for (int color : colors) {
			addHelperTextColor(color);
		}
	}

	/**
	 * Adds all helper text colors, which are contained by a specific
	 * collection.
	 * 
	 * @param resourceIds
	 *            A collection, which contains the resource IDs of the colors,
	 *            which should be added, as an instance of the type
	 *            {@link Collection} or an empty collection, if no colors should
	 *            be added
	 */
	public final void addAllHelperTextColorIds(
			final Collection<Integer> resourceIds) {
		ensureNotNull(resourceIds, "The collection may not be null");

		for (int resourceId : resourceIds) {
			addHelperTextColorId(resourceId);
		}
	}

	/**
	 * Adds all helper text colors, which are contained by a specific array.
	 * 
	 * @param colors
	 *            An array, which contains the helper text colors, which should
	 *            be added, as an {@link Integer} array or an empty array, if no
	 *            colors should be added
	 */
	public final void addAllHelperTextColors(final int... colors) {
		ensureNotNull(colors, "The array may not be null");

		for (int color : colors) {
			addHelperTextColor(color);
		}
	}

	/**
	 * Adds all helper text colors, which are contained by a specific array.
	 * 
	 * @param resourceIds
	 *            An array, which contains the resource IDs of the helper text
	 *            colors, which should be added, as an {@link Integer} array or
	 *            an empty array, if no colors should be added
	 */
	public final void addAllHelperTextColorIds(final int... resourceIds) {
		ensureNotNull(resourceIds, "The array may not be null");

		for (int resourceId : resourceIds) {
			addHelperTextColorId(resourceId);
		}
	}

	/**
	 * Removes a specific helper text color, which should not be used to
	 * highlight the helper text, which indicates the password safety, anymore.
	 * 
	 * @param color
	 *            The color, which should be removed, as an {@link Integer}
	 *            value
	 */
	public final void removeHelperTextColor(final int color) {
		helperTextColors.remove(color);
		verifyPasswordSafety();
	}

	/**
	 * Removes a specific helper text color, which should not be used to
	 * highlight the helper text, which indicates the password safety, anymore.
	 * 
	 * @param resourceId
	 *            The resource ID of the color, which should be removed, as an
	 *            {@link Integer} value. The resource ID must correspond to a
	 *            valid color resource
	 */
	public final void removeHelperTextColorId(final int resourceId) {
		removeHelperTextColor(getResources().getColor(resourceId));
	}

	/**
	 * Removes all helper text colors, which are contained by a specific
	 * collection.
	 * 
	 * @param colors
	 *            A collection, which contains the colors, which should be
	 *            removed, as an instance of the type {@link Collection} or an
	 *            empty collection, if no colors should be removed
	 */
	public final void removeAllHelperTextColors(final Collection<Integer> colors) {
		ensureNotNull(colors, "The collection may not be null");

		for (int color : colors) {
			removeHelperTextColor(color);
		}
	}

	/**
	 * Removes all helper text colors, which are contained by a specific
	 * collection.
	 * 
	 * @param resourceIds
	 *            A collection, which contains the resource IDs of the colors,
	 *            which should be removed, as an instance of the type
	 *            {@link Collection} or an empty collection, if no colors should
	 *            be removed
	 */
	public final void removeAllHelperTextColorIds(
			final Collection<Integer> resourceIds) {
		ensureNotNull(resourceIds, "The collection may not be null");

		for (int resourceId : resourceIds) {
			removeHelperTextColorId(resourceId);
		}
	}

	/**
	 * Removes all helper text colors, which are contained by a specific array.
	 * 
	 * @param colors
	 *            An array, which contains the colors, which should be removed,
	 *            as an {@link Integer} array or an empty array, if no colors
	 *            should be removed
	 */
	public final void removeAllHelperTextColors(final int... colors) {
		ensureNotNull(colors, "The array may not be null");

		for (int color : colors) {
			removeHelperTextColor(color);
		}
	}

	/**
	 * Removes all helper text colors, which are contained by a specific array.
	 * 
	 * @param resourceIds
	 *            An array, which contains the resourceIDs of the colors, which
	 *            should be removed, as an {@link Integer} array or an empty
	 *            array, if no colors should be removed
	 */
	public final void removeAllHelperTextColorIds(final int... resourceIds) {
		ensureNotNull(resourceIds, "The array may not be null");

		for (int resourceId : resourceIds) {
			removeHelperTextColorId(resourceId);
		}
	}

	/**
	 * Removes all text colors, which are used to highlight the helper text,
	 * which indicates the password safety.
	 */
	public final void removeAllHelperTextColors() {
		helperTextColors.clear();
	}

	/**
	 * Returns the prefix of the helper texts, which are shown depending on the
	 * password safety.
	 * 
	 * @return The prefix of the helper texts, which are shown depending on the
	 *         password safety, as a {@link String}
	 */
	public final String getPasswordVerificationPrefix() {
		return passwordVerificationPrefix;
	}

	/**
	 * Sets the prefix of the helper texts, which are shown depending on the
	 * password safety.
	 * 
	 * @param format
	 *            The prefix, which should be set, as a {@link String} or null,
	 *            if no prefix should be set
	 */
	public final void setPasswordVerificationPrefix(final String format) {
		this.passwordVerificationPrefix = format;
		verifyPasswordSafety();
	}

	/**
	 * Sets the format of the helper texts, which are shown depending on the
	 * password safety.
	 * 
	 * @param resourceId
	 *            The resourceID of the format, which should be set, as an
	 *            {@link Integer} value. The resource ID must correspond to a
	 *            valid string resource
	 */
	public final void setPasswordVerificationPrefix(final int resourceId) {
		setPasswordVerificationPrefix(getResources().getString(resourceId));
	}

	@Override
	public final void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		verifyPasswordSafety();
	}

}
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
package de.mrapp.android.validation.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * A spinner adapter, which acts as a proxy for an other adapter in order to initially show a hint
 * instead of the adapter's first item.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public class ProxySpinnerAdapter implements SpinnerAdapter, ListAdapter {

    /**
     * The context, which is used by the adapter.
     */
    private final Context context;

    /**
     * The adapter, which contains the actual items.
     */
    private final SpinnerAdapter adapter;

    /**
     * The resource id if the layout, which should be used to display the hint.
     */
    private final int hintViewId;

    /**
     * The hint, which is displayed initially.
     */
    private final CharSequence hint;

    /**
     * The color of the hint, which is displayed initially.
     */
    private final ColorStateList hintColor;

    /**
     * Inflates and returns the view, which is used to display the hint.
     *
     * @param parent
     *         The parent view of the view, which should be inflated, as an instance of the class
     *         {@link ViewGroup} or null, if no parent view is available
     * @return The view, which has been inflated, as an instance of the class {@link View}
     */
    private View inflateHintView(@Nullable final ViewGroup parent) {
        TextView view = (TextView) LayoutInflater.from(context).inflate(hintViewId, parent, false);
        view.setText(hint);

        if (hintColor != null) {
            view.setTextColor(hintColor);
        }

        return view;
    }

    /**
     * Creates a new spinner adapter, which acts as a proxy for an other adapter in order to
     * initially show a hint instead of the adapter's first item.
     *
     * @param context
     *         The context, which should be used by the adapter, as an instance of the class {@link
     *         Context}. The context may not be null
     * @param adapter
     *         The adapter, which contains the actual items, as an instance of the type {@link
     *         SpinnerAdapter}. The adapter may not be null
     * @param hintViewId
     *         The resource id of the layout, which should be used to display the hint, as an {@link
     *         Integer} value. The resource id must corresponds to a valid layout resource
     * @param hint
     *         The hint, which should be displayed initially, as an instance of the type {@link
     *         CharSequence} or null, if no hint should be displayed
     * @param hintColor
     *         The color of the hint, which should be displayed initially, as an instance of the
     *         class {@link ColorStateList} or null, if the default color should be used
     */
    public ProxySpinnerAdapter(@NonNull final Context context,
                               @NonNull final SpinnerAdapter adapter,
                               @LayoutRes final int hintViewId, @Nullable final CharSequence hint,
                               @Nullable final ColorStateList hintColor) {
        ensureNotNull(context, "The context may not be null");
        ensureNotNull(adapter, "The adapter may not be null");
        this.context = context;
        this.adapter = adapter;
        this.hintViewId = hintViewId;
        this.hint = hint;
        this.hintColor = hintColor;
    }

    /**
     * Returns the adapter, which contains the actual items.
     *
     * @return The adapter, which contains the actual items, as an instance of the type {@link
     * SpinnerAdapter}
     */
    public final SpinnerAdapter getAdapter() {
        return adapter;
    }

    @Override
    public final View getView(final int position, final View convertView, final ViewGroup parent) {
        if (position == 0) {
            return inflateHintView(parent);
        }

        return adapter.getView(position - 1, null, parent);
    }

    @Override
    public final View getDropDownView(final int position, final View convertView,
                                      final ViewGroup parent) {
        if (position == 0) {
            return new View(context);
        }

        return adapter.getDropDownView(position - 1, null, parent);
    }

    @Override
    public final int getCount() {
        return adapter.getCount() == 0 ? 0 : adapter.getCount() + 1;
    }

    @Override
    public final Object getItem(final int position) {
        return position == 0 ? null : adapter.getItem(position - 1);
    }

    @Override
    public final int getItemViewType(final int position) {
        return 0;
    }

    @Override
    public final int getViewTypeCount() {
        return 1;
    }

    @Override
    public final long getItemId(final int position) {
        return position > 0 ? adapter.getItemId(position - 1) : -1;
    }

    @Override
    public final boolean hasStableIds() {
        return adapter.hasStableIds();
    }

    @Override
    public final boolean isEmpty() {
        return adapter.isEmpty();
    }

    @Override
    public final void registerDataSetObserver(final DataSetObserver observer) {
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public final void unregisterDataSetObserver(final DataSetObserver observer) {
        adapter.unregisterDataSetObserver(observer);
    }

    @Override
    public final boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public final boolean isEnabled(final int position) {
        return position > 0;
    }

}
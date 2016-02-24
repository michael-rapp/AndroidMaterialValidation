/*
 * Copyright 2015 - 2016 Michael Rapp
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
import android.graphics.Color;
import android.test.AndroidTestCase;
import android.widget.ArrayAdapter;

import junit.framework.Assert;

/**
 * Tests the functionality of the class {@link ProxySpinnerAdapter}.
 *
 * @author Michael Rapp
 */
public class ProxySpinnerAdapterTest extends AndroidTestCase {

    /**
     * Creates and returns a {@link ProxySpinnerAdapter}, which may be used for test purposes.
     *
     * @param entries
     *         The entries of the adapter, which should be created, as a {@link CharSequence} array
     * @return The adapter, which has been created, as an instance of the class {@link
     * ProxySpinnerAdapter}
     */
    private ProxySpinnerAdapter createAdapter(final CharSequence[] entries) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, entries);
        ProxySpinnerAdapter proxySpinnerAdapter =
                new ProxySpinnerAdapter(getContext(), adapter, android.R.layout.simple_spinner_item,
                        null, null);
        return proxySpinnerAdapter;
    }

    /**
     * Tests, if all properties are set correctly by the constructor.
     */
    public final void testConstructor() {
        Context context = getContext();
        CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, entries);
        int hintViewId = android.R.layout.simple_spinner_item;
        CharSequence hint = "hint";
        ColorStateList hintColor = ColorStateList.valueOf(Color.GRAY);
        ProxySpinnerAdapter proxySpinnerAdapter =
                new ProxySpinnerAdapter(context, adapter, hintViewId, hint, hintColor);
        assertEquals(adapter, proxySpinnerAdapter.getAdapter());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, if the context is
     * null.
     */
    public final void testConstructorThrowsExceptionWhenContextIsNull() {
        try {
            CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(),
                    android.R.layout.simple_spinner_dropdown_item, entries);
            new ProxySpinnerAdapter(null, adapter, android.R.layout.simple_spinner_item, null,
                    null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, if the adapter is
     * null.
     */
    public final void testConstructorThrowsExceptionWhenAdapterIsNull() {
        try {
            new ProxySpinnerAdapter(getContext(), null, android.R.layout.simple_spinner_item, null,
                    null);
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Tests the functionality of the getView-method.
     */
    public final void testGetView() {
        CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
        ProxySpinnerAdapter proxySpinnerAdapter = createAdapter(entries);
        assertNotNull(proxySpinnerAdapter.getView(0, null, null));
    }

    /**
     * Tests the functionality of the getDropDownView-method.
     */
    public final void testGetDropDownView() {
        CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
        ProxySpinnerAdapter proxySpinnerAdapter = createAdapter(entries);
        assertNotNull(proxySpinnerAdapter.getDropDownView(0, null, null));
    }

    /**
     * Tests the functionality of the getCount-method.
     */
    public final void testGetCount() {
        CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
        ProxySpinnerAdapter adapter1 = createAdapter(new CharSequence[0]);
        ProxySpinnerAdapter adapter2 = createAdapter(entries);
        assertEquals(0, adapter1.getCount());
        assertEquals(3, adapter2.getCount());
    }

    /**
     * Tests the functionality of the getItem-method.
     */
    public final void testGetItem() {
        CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
        ProxySpinnerAdapter proxySpinnerAdapter = createAdapter(entries);
        assertNull(proxySpinnerAdapter.getItem(0));
        assertEquals("entry1", proxySpinnerAdapter.getItem(1));
        assertEquals("entry2", proxySpinnerAdapter.getItem(2));
    }

    /**
     * Tests the functionality of the getItemViewType-method.
     */
    public final void testGetItemViewType() {
        CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
        ProxySpinnerAdapter proxySpinnerAdapter = createAdapter(entries);
        assertEquals(0, proxySpinnerAdapter.getItemViewType(0));
    }

    /**
     * Tests the functionality of the getViewTypeCount-method.
     */
    public final void testGetViewTypeCount() {
        CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
        ProxySpinnerAdapter proxySpinnerAdapter = createAdapter(entries);
        assertEquals(1, proxySpinnerAdapter.getViewTypeCount());
    }

    /**
     * Tests the functionality of the getItemId-method.
     */
    public final void testGetItemId() {
        CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
        ProxySpinnerAdapter proxySpinnerAdapter = createAdapter(entries);
        assertEquals(proxySpinnerAdapter.getAdapter().hasStableIds(),
                proxySpinnerAdapter.hasStableIds());
        assertEquals(-1, proxySpinnerAdapter.getItemId(0));
        assertEquals(0, proxySpinnerAdapter.getItemId(1));
        assertEquals(1, proxySpinnerAdapter.getItemId(2));
    }

    /**
     * Tests the functionality of the hasStableIds-method.
     */
    public final void testHasStableIds() {
        CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
        ProxySpinnerAdapter proxySpinnerAdapter = createAdapter(entries);
        assertEquals(proxySpinnerAdapter.getAdapter().hasStableIds(),
                proxySpinnerAdapter.hasStableIds());
    }

    /**
     * Tests the functionality of the isEmpty-method.
     */
    public final void testIsEmpty() {
        CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
        ProxySpinnerAdapter adapter1 = createAdapter(new CharSequence[0]);
        ProxySpinnerAdapter adapter2 = createAdapter(entries);
        assertTrue(adapter1.isEmpty());
        assertFalse(adapter2.isEmpty());
    }

    /**
     * Tests the functionality of the areAllItemsEnabled-method.
     */
    public final void testAreAllItemsEnabled() {
        CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
        ProxySpinnerAdapter proxySpinnerAdapter = createAdapter(entries);
        assertFalse(proxySpinnerAdapter.areAllItemsEnabled());
    }

    /**
     * Tests the functionality of the isEnabled-method.
     */
    public final void testIsEnabled() {
        CharSequence[] entries = new CharSequence[]{"entry1", "entry2"};
        ProxySpinnerAdapter proxySpinnerAdapter = createAdapter(entries);
        assertFalse(proxySpinnerAdapter.isEnabled(0));
        assertTrue(proxySpinnerAdapter.isEnabled(1));
        assertTrue(proxySpinnerAdapter.isEnabled(2));
    }

}
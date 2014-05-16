/**
 * Copyright 2014 Fernando Rincon Martin <frm.rincon@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.abstractform.vaadin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

import org.abstractform.core.selector.SelectorProvider;
import org.abstractform.core.selector.SelectorProviderListener;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractContainer;

public class VaadinSelectorContainer extends AbstractContainer implements Container.ItemSetChangeNotifier {

	private static final long serialVersionUID = -3465654407255944675L;

	public static final String PROPERTY_CAPTION = "CAPTION";
	public static final String PROPERTY_OBJECT = "OBJECT";

	private SelectorProvider provider;

	private List<String> propertyIds = Arrays.asList(PROPERTY_CAPTION, PROPERTY_OBJECT);

	private SortedSet<?> elements;

	public VaadinSelectorContainer(SelectorProvider provider) {
		this.provider = provider;
		provider.addSelectorProviderListener(new SelectorProviderListener() {

			@Override
			public void elementsChanged() {
				clearCache();
				fireItemSetChange();
			}

		});
	}

	private void clearCache() {
		elements = null;
	}

	@Override
	public void addListener(ItemSetChangeListener listener) {
		super.addListener(listener);
	}

	@Override
	public void removeListener(ItemSetChangeListener listener) {
		super.removeListener(listener);
	}

	@Override
	public Item getItem(Object itemId) {
		if (getElements().contains(itemId)) {
			return new VaadinSelectorItem(provider.getText(itemId), itemId);
		} else {
			return null;
		}
	}

	@Override
	public Collection<?> getContainerPropertyIds() {
		return propertyIds;
	}

	@Override
	public Collection<?> getItemIds() {
		return Collections.unmodifiableCollection(getElements());
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		return getItem(itemId).getItemProperty(propertyId);
	}

	@Override
	public Class<?> getType(Object propertyId) {
		if (PROPERTY_CAPTION.equals(propertyId)) {
			return String.class;
		} else if (PROPERTY_OBJECT.equals(propertyIds)) {
			return Object.class;
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public int size() {
		return getElements().size();
	}

	@Override
	public boolean containsId(Object itemId) {
		return getElements().contains(itemId);
	}

	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	private SortedSet<?> getElements() {
		if (this.elements == null) {
			this.elements = provider.getElements();
		}
		return this.elements;
	}

	private class VaadinSelectorProperty implements Property {

		private static final long serialVersionUID = -2497350368537366306L;
		private Object value;
		private Class<?> type;

		public VaadinSelectorProperty(Object value, Class<?> type) {
			this.value = value;
			this.type = type;
		}

		@Override
		public Object getValue() {
			return value;
		}

		@Override
		public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
			throw new UnsupportedOperationException();
		}

		@Override
		public Class<?> getType() {
			return type;
		}

		@Override
		public boolean isReadOnly() {
			return true;
		}

		@Override
		public void setReadOnly(boolean newStatus) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String toString() {
			return value.toString();
		}

	}

	private class VaadinSelectorItem implements Item {

		private static final long serialVersionUID = 1938177136887350564L;

		private String caption;
		private Object element;

		public VaadinSelectorItem(String caption, Object element) {
			this.caption = caption;
			this.element = element;
		}

		@Override
		public Property getItemProperty(Object id) {
			if (PROPERTY_CAPTION.equals(id)) {
				return new VaadinSelectorProperty(caption, String.class);
			} else if (PROPERTY_OBJECT.equals(id)) {
				return new VaadinSelectorProperty(element, Object.class);
			} else {
				throw new UnsupportedOperationException();
			}
		}

		@Override
		public Collection<?> getItemPropertyIds() {
			return propertyIds;
		}

		@Override
		public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

	}
}

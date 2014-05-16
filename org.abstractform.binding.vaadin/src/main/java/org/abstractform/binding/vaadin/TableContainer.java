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
package org.abstractform.binding.vaadin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abstractform.binding.BField;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractInMemoryContainer;
import com.vaadin.data.util.MethodProperty;

@SuppressWarnings("serial")
public class TableContainer extends AbstractInMemoryContainer<Object, String, Item> {

	private List<String> propertyIds;
	private Collection<Object> values;
	private Map<Object, Item> itemMap = new HashMap<Object, Item>();
	private Map<Object, Map<String, Property>> mapObjectsProperties = new HashMap<Object, Map<String, Property>>();

	public TableContainer(List<BField> fields, Collection<Object> values) {
		this.values = values;
		this.propertyIds = new ArrayList<String>(fields.size());
		for (BField field : fields) {
			propertyIds.add(field.getPropertyName());
		}
		for (Object value : values) {
			internalAddItemAtEnd(value, getUnfilteredItem(value), true);
		}
	}

	@Override
	public Collection<?> getContainerPropertyIds() {
		return Collections.unmodifiableCollection(propertyIds);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		if (!mapObjectsProperties.containsKey(itemId)) {
			mapObjectsProperties.put(itemId, new HashMap<String, Property>(propertyIds.size()));
		}
		if (!mapObjectsProperties.get(itemId).containsKey(propertyId)) {
			mapObjectsProperties.get(itemId).put((String) propertyId, new MethodProperty(itemId, (String) propertyId));
		}
		return mapObjectsProperties.get(itemId).get(propertyId);
	}

	@Override
	public Class<?> getType(Object propertyId) {
		if (values.size() > 0) {
			return getContainerProperty(values.iterator().next(), propertyId).getType();
		} else {
			return Object.class;
		}
	}

	@Override
	protected Item getUnfilteredItem(Object itemId) {
		if (!itemMap.containsKey(itemId)) {
			itemMap.put(itemId, new TableContainerItem(itemId));
		}
		return itemMap.get(itemId);
	}

	private class TableContainerItem implements Item {

		private Object value;

		private TableContainerItem(Object value) {
			this.value = value;
		}

		@Override
		public Property getItemProperty(Object id) {
			return getContainerProperty(value, id);
		}

		@Override
		public Collection<?> getItemPropertyIds() {
			return getContainerPropertyIds();
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

	public Collection<Object> getValues() {
		return values;
	}

}

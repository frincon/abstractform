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
package org.abstractform.vaadin.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abstractform.vaadin.VaadinDataObject;
import org.abstractform.vaadin.VaadinFormInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

public class VaadinFormInstanceImpl implements VaadinFormInstance {

	private static final Logger LOG = LoggerFactory.getLogger(VaadinFormInstanceImpl.class);

	private Component implementation;
	private Map<String, AbstractComponent> mapComponents;
	private List<String> fieldIdList;

	private Map<PropertyChangeListener, FieldValueListener> mapListeners = new HashMap<PropertyChangeListener, FieldValueListener>();

	private class FieldValueListener implements Property.ValueChangeListener {
		private static final long serialVersionUID = -8963599736006190756L;

		private PropertyChangeListener listener;

		@Override
		public void valueChange(ValueChangeEvent event) {
			//TODO do better
			AbstractComponent component = (AbstractComponent) event.getProperty();
			VaadinDataObject dataObject = (VaadinDataObject) component.getData();
			listener.propertyChange(new PropertyChangeEvent(VaadinFormInstanceImpl.this, null, null, dataObject.getAccessor()
					.getFieldValue(VaadinFormInstanceImpl.this, component)));
		}

	}

	public VaadinFormInstanceImpl(Component implementation, Map<String, AbstractComponent> mapComponents, List<String> fieldIdList) {
		this.implementation = implementation;
		this.mapComponents = mapComponents;
		this.fieldIdList = Collections.unmodifiableList(fieldIdList);
	}

	@Override
	public Component getImplementation() {
		return implementation;
	}

	@Override
	public AbstractComponent getComponentById(String id) {
		return mapComponents.get(id);
	}

	@Override
	public Object getFieldValue(String fieldId) {
		AbstractComponent component = (AbstractComponent) getComponentById(fieldId);
		VaadinDataObject dataObject = (VaadinDataObject) component.getData();
		return dataObject.getAccessor().getFieldValue(this, component);
	}

	@Override
	public void setFieldValue(String fieldId, Object value) {
		AbstractComponent component = (AbstractComponent) getComponentById(fieldId);
		VaadinDataObject dataObject = (VaadinDataObject) component.getData();
		dataObject.getAccessor().setFieldValue(this, component, value);
	}

	@Override
	public List<String> getFieldIds() {
		return fieldIdList;
	}

	@Override
	public void addFieldChangeListener(String fieldId, PropertyChangeListener listener) {
		Component c = getComponentById(fieldId);
		if (c instanceof Property.ValueChangeNotifier) {
			FieldValueListener fl = new FieldValueListener();
			fl.listener = listener;
			((Property.ValueChangeNotifier) c).addListener(fl);
			mapListeners.put(listener, fl);
		} else {
			LOG.warn("Listener not added to field because filed has not recognozible type. FieldId: {}", fieldId);
		}
	}

	@Override
	public void removeFieldChangeListener(String fieldId, PropertyChangeListener listener) {
		Component c = getComponentById(fieldId);
		if (c instanceof Property.ValueChangeNotifier) {
			if (mapListeners.containsKey(listener)) {
				FieldValueListener fl = mapListeners.remove(listener);
				((Property.ValueChangeNotifier) c).removeListener(fl);
			} else {
				LOG.warn("Listener not removed because not added yet. FieldId: {}", fieldId);

			}
		} else {
			LOG.warn("Listener not removed because filed has not recognozible type. FieldId: {}", fieldId);
		}
	}

	@Override
	public void addFieldChangeListener(PropertyChangeListener listener) {
		for (String fieldId : getFieldIds()) {
			addFieldChangeListener(fieldId, listener);
		}
	}

	@Override
	public void removeFieldChangeListener(PropertyChangeListener listener) {
		for (String fieldId : getFieldIds()) {
			removeFieldChangeListener(fieldId, listener);
		}
	};

}
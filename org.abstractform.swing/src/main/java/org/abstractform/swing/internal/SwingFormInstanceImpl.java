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

package org.abstractform.swing.internal;

import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import org.abstractform.swing.SwingFormInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 */
public class SwingFormInstanceImpl implements SwingFormInstance {

	private static final Logger LOG = LoggerFactory.getLogger(SwingFormInstanceImpl.class);

	public static final String ACCESSOR_OBJECT = "ACCESSOR_OBJECT";

	public static final String ADAPTER_OBJECT = "ADAPTER_OBJECT";

	private JComponent implementation;
	private Map<String, JComponent> componentMap;
	private List<String> fieldIdList;

	/**
	 * @param implementation
	 * @param componentMap
	 * @param fieldIdList
	 */
	public SwingFormInstanceImpl(JComponent implementation, Map<String, JComponent> componentMap, List<String> fieldIdList) {
		super();
		this.implementation = implementation;
		this.componentMap = componentMap;
		this.fieldIdList = fieldIdList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.core.FormInstance#getImplementation()
	 */
	@Override
	public JComponent getImplementation() {
		return implementation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.core.FormInstance#getFieldValue(java.lang.String)
	 */
	@Override
	public Object getFieldValue(String fieldId) {
		JComponent component = componentMap.get(fieldId);
		SwingAccessor accessor = (SwingAccessor) component.getClientProperty(ACCESSOR_OBJECT);
		return accessor.getValue(component);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.core.FormInstance#setFieldValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setFieldValue(String fieldId, Object value) {
		JComponent component = componentMap.get(fieldId);
		SwingAccessor accessor = (SwingAccessor) component.getClientProperty(ACCESSOR_OBJECT);
		accessor.setValue(component, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.core.FormInstance#getFieldIds()
	 */
	@Override
	public List<String> getFieldIds() {
		return Collections.unmodifiableList(fieldIdList);
	}

	/**
	 * @param fieldId
	 * @return
	 */
	private JComponent getComponentById(String fieldId) {
		return componentMap.get(fieldId);
	}

	@Override
	public void addFieldChangeListener(String fieldId, PropertyChangeListener listener) {
		JComponent c = getComponentById(fieldId);
		SwingListenerAdapter adapter = (SwingListenerAdapter) c.getClientProperty(ADAPTER_OBJECT);
		adapter.addPropertyChangeListener(listener);
	}

	@Override
	public void removeFieldChangeListener(String fieldId, PropertyChangeListener listener) {
		JComponent c = getComponentById(fieldId);
		SwingListenerAdapter adapter = (SwingListenerAdapter) c.getClientProperty(ADAPTER_OBJECT);
		adapter.removePropertyChangeListener(listener);
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

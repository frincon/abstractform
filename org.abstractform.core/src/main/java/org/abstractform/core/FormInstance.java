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
package org.abstractform.core;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * A form instance represents a form rendered in a especific implementation
 * 
 * This is the result of the render the form and object of this class allow access to values of fields.
 * 
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 */
public interface FormInstance<S> {

	/**
	 * Return specific implementation of this form instance
	 * 
	 * This method usually returns the object need to interact with the render api.
	 * 
	 * @return Specific implementation of this form instance
	 */
	public S getImplementation();

	/**
	 * Return the value of the given field id
	 * 
	 * @param fieldId
	 *            The field id that wants to return the value
	 * @return The value of the field whith the given fieldId
	 */
	public Object getFieldValue(String fieldId);

	/**
	 * Set value of field of form.
	 * 
	 * This method avoid read only state.
	 * 
	 * @param fieldId
	 *            The id of field to set value
	 * @param value
	 *            The value to set
	 */
	public void setFieldValue(String fieldId, Object value);

	public List<String> getFieldIds();

	// TODO Make with self implementation of listener
	public void addFieldChangeListener(String fieldId, PropertyChangeListener listener);

	public void removeFieldChangeListener(String fieldId, PropertyChangeListener listener);

	public void addFieldChangeListener(PropertyChangeListener listener);

	public void removeFieldChangeListener(PropertyChangeListener listener);

}

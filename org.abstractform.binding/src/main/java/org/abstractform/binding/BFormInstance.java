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
package org.abstractform.binding;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.abstractform.binding.validation.ValidationError;
import org.abstractform.core.FormInstance;

/**
 * A form instance represents a form with binding capabilities rendered in a specific implementation.
 * 
 * This is the result of the render the form and object of this class allow access to values of fields.
 * 
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 * @param <S>
 *            The class of the objects that act as model for this form
 * @param <T>
 *            The class of the objects that this form return as specific implementation
 */
public interface BFormInstance<S, T> extends FormInstance<T> {

	/**
	 * Establish the binding context to this form instance
	 * 
	 * This method is intend to be called from Binding Framework, not from clients
	 * 
	 * @param context
	 *            The binding context to establish
	 */
	public void setBindingContext(BindingContext context);

	/**
	 * Retrun the binding context
	 * 
	 * @return The binding context. Null if this form is not binded
	 */
	public BindingContext getBindingContext();

	/**
	 * Establish the model of the form.
	 * 
	 * When this method is call, all the fields lost his old value and refresh with new values from the model
	 * 
	 * @param value
	 *            The model that this form must be show (binding)
	 */
	public void setValue(S value);

	/**
	 * Return the model that this form is actually binded
	 * 
	 * @return The model that this form is actually binded
	 */
	public S getValue();

	/**
	 * Add a PropertyChangeListener for a specific property.
	 * 
	 * The same listener object may be added more than once. For each
	 * property, the listener will be invoked the number of times it was added
	 * for that property.
	 * If <code>propertyName</code> or <code>listener</code> is null, no
	 * exception is thrown and no action is taken.
	 * 
	 * @param propertyName
	 *            The name of the property to listen on.
	 * @param listener
	 *            The PropertyChangeListener to be added
	 */
	public void addValuePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Remove a PropertyChangeListener for a specific property.
	 * If <code>listener</code> was added more than once to the same event
	 * source for the specified property, it will be notified one less time
	 * after being removed.
	 * If <code>propertyName</code> is null, no exception is thrown and no
	 * action is taken.
	 * If <code>listener</code> is null, or was never added for the specified
	 * property, no exception is thrown and no action is taken.
	 * 
	 * @param propertyName
	 *            The name of the property that was listened on.
	 * @param listener
	 *            The PropertyChangeListener to be removed
	 */
	public void removeValuePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Return true when the field with the given fieldId is read only
	 * 
	 * @param fieldId
	 *            The fieldId
	 * @return true when the field with the given fieldId is read only
	 */
	public boolean isFieldReadOnly(String fieldId);

	/**
	 * Establish the property readOnly to field with the given fieldId
	 * 
	 * @param fieldId
	 *            The fieldId
	 * @param readOnly
	 *            the value
	 */
	public void setFieldReadOnly(String fieldId, boolean readOnly);

	/**
	 * Establsh a error on field.
	 * 
	 * This method is intend to be called from Binding Framework, not from clients
	 * 
	 * @param fieldId
	 * @param errors
	 */
	public void setError(String fieldId, List<String> errors);

	/**
	 * Utility method to call BBindingContext.updateModel()
	 */
	public void updateModel();

	/**
	 * Utility method to call BBindingContext.getErrors()
	 * 
	 * @return
	 */
	public List<String> getErrors();

	public void setValidationErrorSummary(List<ValidationError> errorList);

	public List<ValidationError> getValidationErrorSummary();

	public void refreshValidationSummary();

}

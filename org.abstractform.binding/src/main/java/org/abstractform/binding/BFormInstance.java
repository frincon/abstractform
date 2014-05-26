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

public interface BFormInstance<S, T> extends FormInstance<T> {

	public void setBindingContext(BindingContext context);

	public BindingContext getBindingContext();

	public void setValue(S value);

	public S getValue();

	public void addValuePropertyChangeListener(PropertyChangeListener listener);

	public void removeValuePropertyChangeListener(PropertyChangeListener listener);

	public boolean isFieldReadOnly(String fieldId);

	public void setFieldReadOnly(String fieldId, boolean readOnly);

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

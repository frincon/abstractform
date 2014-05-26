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

package org.abstractform.binding.eclipse.validation;

import org.abstractform.binding.BFormInstance;
import org.abstractform.core.FormInstance;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.MultiValidator;
import org.eclipse.core.runtime.IStatus;

/**
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 */
public class ValidatorStatusProvider extends MultiValidator {

	private IValidator validator;
	private FormInstance formInstance;
	private DataBindingContext context;

	public ValidatorStatusProvider(IValidator validator, BFormInstance<?, ?> formInstance, DataBindingContext context) {
		this.validator = validator;
		this.formInstance = formInstance;
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.databinding.validation.MultiValidator#validate()
	 */
	@Override
	protected final IStatus validate() {
		//		for (Object observable : context.getBindings()) {
		//			Binding binding = (Binding) observable;
		//			IObservable model = binding.getModel();
		//			if (model instanceof IObservableValue) {
		//				IObservableValue value = (IObservableValue) model;
		//				value.getValue();
		//			} else if (model instanceof IObservableCollection) {
		//				IObservableCollection collection = (IObservableCollection) model;
		//				collection.size();
		//			} else if (model instanceof IObservableMap) {
		//				IObservableMap map = (IObservableMap) model;
		//				map.size();
		//			}
		//		}
		return validator.validate(formInstance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.databinding.validation.MultiValidator#getValidationStatus()
	 */
	@Override
	public IObservableValue getValidationStatus() {
		revalidate();
		return super.getValidationStatus();
	}

}

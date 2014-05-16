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
package org.abstractform.binding.eclipse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.abstractform.binding.BFormInstance;
import org.abstractform.binding.validation.ValidationError;
import org.eclipse.core.databinding.ValidationStatusProvider;
import org.eclipse.core.databinding.observable.list.ListDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.list.SimpleListProperty;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;

public class FormValidationSummaryProperty extends SimpleListProperty {

	public FormValidationSummaryProperty() {
	}

	@Override
	public INativePropertyListener adaptListener(ISimplePropertyListener listener) {
		return null;
	}

	@Override
	public Object getElementType() {
		return ValidationError.class;
	}

	@Override
	protected List doGetList(Object source) {
		return ((BFormInstance) source).getValidationErrorSummary();
	}

	@Override
	protected void doSetList(Object source, List list, ListDiff diff) {
		List<ValidationError> validationList = Collections.checkedList(list, ValidationError.class);
		BFormInstance instance = (BFormInstance) source;
		instance.setValidationErrorSummary(validationList);
	}

}

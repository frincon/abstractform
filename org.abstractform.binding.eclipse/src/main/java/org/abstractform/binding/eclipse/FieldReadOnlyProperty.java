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

import org.abstractform.binding.BFormInstance;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;

public class FieldReadOnlyProperty extends SimpleValueProperty {

	private String fieldId;

	public FieldReadOnlyProperty(String fieldId) {
		this.fieldId = fieldId;
	}

	@Override
	public Object getValueType() {
		return null;
	}

	@Override
	protected Object doGetValue(Object source) {
		BFormInstance<?> form = (BFormInstance<?>) source;
		return form.isFieldReadOnly(fieldId);
	}

	@Override
	protected void doSetValue(Object source, Object value) {
		BFormInstance<?> form = (BFormInstance<?>) source;
		form.setFieldReadOnly(fieldId, (Boolean) value);
	}

	@Override
	public INativePropertyListener adaptListener(ISimplePropertyListener listener) {
		return null;
	}
}

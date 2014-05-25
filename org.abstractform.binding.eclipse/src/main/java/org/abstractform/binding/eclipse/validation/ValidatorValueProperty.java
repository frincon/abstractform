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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.abstractform.binding.BFormInstance;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.NativePropertyListener;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;

public class ValidatorValueProperty extends SimpleValueProperty {

	private String fieldId;

	private class FieldValuePropertyListener extends NativePropertyListener implements PropertyChangeListener {

		public FieldValuePropertyListener(ISimplePropertyListener listener) {
			super(ValidatorValueProperty.this, listener);
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			fireChange(evt.getSource(), null);
		}

		@Override
		protected void doAddTo(Object source) {
			((BFormInstance<?>) source).addFieldChangeListener(fieldId, this);
		}

		@Override
		protected void doRemoveFrom(Object source) {
			((BFormInstance<?>) source).removeFieldChangeListener(fieldId, this);
		}

	}

	public ValidatorValueProperty(String fieldId) {
		this.fieldId = fieldId;
	}

	@Override
	public Object getValueType() {
		return null;
	}

	@Override
	protected Object doGetValue(Object source) {
		BFormInstance<?> form = (BFormInstance<?>) source;
		return form.getFieldValue(fieldId);
	}

	@Override
	protected void doSetValue(Object source, Object value) {
		BFormInstance<?> form = (BFormInstance<?>) source;
		form.setFieldValue(fieldId, value);
	}

	@Override
	public INativePropertyListener adaptListener(ISimplePropertyListener listener) {
		return new FieldValuePropertyListener(listener);
	}

	public String getFieldId() {
		return fieldId;
	}
}

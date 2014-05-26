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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.abstractform.binding.BFormInstance;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.NativePropertyListener;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;

@SuppressWarnings("unchecked")
public class FormValueProperty<T> extends SimpleValueProperty {

	private Class<T> beanClass;

	private class FormValuePropertyListener extends NativePropertyListener implements PropertyChangeListener {

		public FormValuePropertyListener(ISimplePropertyListener listener) {
			super(FormValueProperty.this, listener);
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			fireChange(evt.getSource(), null);
		}

		@Override
		protected void doAddTo(Object source) {
			((BFormInstance<?, ?>) source).addValuePropertyChangeListener(this);
		}

		@Override
		protected void doRemoveFrom(Object source) {
			((BFormInstance<?, ?>) source).removeValuePropertyChangeListener(this);
		}

	}

	public FormValueProperty(Class<T> beanClass) {
		this.beanClass = beanClass;
	}

	@Override
	public Object getValueType() {
		//return beanClass;
		return Object.class;
	}

	@Override
	protected Object doGetValue(Object source) {
		BFormInstance<T, ?> formInstance = (BFormInstance<T, ?>) source;
		return formInstance.getValue();
	}

	@Override
	protected void doSetValue(Object source, Object value) {
		BFormInstance<T, ?> formInstance = (BFormInstance<T, ?>) source;
		formInstance.setValue((T) value);
	}

	@Override
	public INativePropertyListener adaptListener(ISimplePropertyListener listener) {
		return new FormValuePropertyListener(listener);
	}

}

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

import org.abstractform.binding.BPresenter;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.NativePropertyListener;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;

public class PresenterValueProperty extends SimpleValueProperty {

	private String propertyName;

	private class PresenterValuePropertyListener extends NativePropertyListener implements PropertyChangeListener {

		public PresenterValuePropertyListener(ISimplePropertyListener listener) {
			super(PresenterValueProperty.this, listener);
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			fireChange(evt.getSource(), null);
		}

		@Override
		protected void doAddTo(Object source) {
			((BPresenter<?>) source).addPropertyChangeListener(propertyName, this);
		}

		@Override
		protected void doRemoveFrom(Object source) {
			((BPresenter<?>) source).removePropertyChangeListener(propertyName, this);
		}

	}

	public PresenterValueProperty(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public Object getValueType() {
		return Object.class;
	}

	@Override
	protected Object doGetValue(Object source) {
		BPresenter<?> presenter = (BPresenter<?>) source;
		return presenter.getPropertyValue(propertyName);
	}

	@Override
	protected void doSetValue(Object source, Object value) {
		BPresenter<?> presenter = (BPresenter<?>) source;
		presenter.setPropertyValue(propertyName, value);
	}

	@Override
	public INativePropertyListener adaptListener(ISimplePropertyListener listener) {
		return new PresenterValuePropertyListener(listener);
	}

}

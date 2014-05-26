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
package org.abstractform.binding.fluent;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

public class BFBeanBasedPresenter<S> extends BFAbstractPresenter<S> {

	public BFBeanBasedPresenter(S model) {
		super(model);
	}

	@Override
	public void fieldHasChanged(String fieldId) {
		// Nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.binding.BPresenter#getPropertyValue(java.lang.String)
	 */
	@Override
	public Object getPropertyValue(String propertyName) {
		if (getModel() == null) {
			return null;
		}
		try {
			return PropertyUtils.getProperty(getModel(), propertyName);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new IllegalArgumentException("The property passed as parameter can be wrong", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.binding.BPresenter#setPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(String propertyName, Object value) {
		try {
			PropertyUtils.setProperty(getModel(), propertyName, value);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new IllegalArgumentException("The property passed as parameter can be wrong", e);
		}
	}

}

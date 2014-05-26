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
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 */
public abstract class BFBeanBasedOwnPropertiesPresenter<S> extends BFBeanBasedPresenter<S> {

	private List<String> ownProperties;

	protected BFBeanBasedOwnPropertiesPresenter(S model, List<String> ownProperties) {
		super(model);
		this.ownProperties = ownProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.binding.fluent.BFBeanBasedPresenter#getPropertyValue(java.lang.String)
	 */
	@Override
	public Object getPropertyValue(String propertyName) {
		if (ownProperties.contains(propertyName)) {
			try {
				return PropertyUtils.getProperty(this, propertyName);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new IllegalArgumentException("The property passed as parameter can be wrong", e);
			}
		} else {
			return super.getPropertyValue(propertyName);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.binding.fluent.BFBeanBasedPresenter#setPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(String propertyName, Object value) {
		if (ownProperties.contains(propertyName)) {
			try {
				PropertyUtils.setProperty(this, propertyName, value);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new IllegalArgumentException("The property passed as parameter can be wrong", e);
			}
		} else {
			super.setPropertyValue(propertyName, value);
		}
	}

}

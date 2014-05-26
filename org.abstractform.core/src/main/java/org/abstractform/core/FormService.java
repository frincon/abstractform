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
package org.abstractform.core;

import java.util.Iterator;
import java.util.ServiceLoader;

import javax.management.ServiceNotFoundException;

/**
 * Utility class to get FormToolkit from Service Loader
 * 
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 */
@SuppressWarnings("rawtypes")
public class FormService {

	private static FormService instance;

	private ServiceLoader<FormToolkit> loader;

	private FormService() {
		loader = ServiceLoader.load(FormToolkit.class);
	}

	/**
	 * Return a singleton instance
	 * 
	 * @return The singleton instance
	 */
	public static synchronized FormService getInstance() {
		if (instance == null) {
			instance = new FormService();
		}
		return instance;
	}

	/**
	 * Find in the Service Loader a {@link FormToolkit} that returns the required form instance type of rendered forms
	 * 
	 * @param formInstanceRequired
	 *            The specific FormInstance class that the form toolkit must return when render form
	 * @return The FormToolkit that build form instances of the given form instance class
	 * @throws ServiceNotFoundException
	 *             When a FormToolkit is not found
	 */
	@SuppressWarnings("unchecked")
	public <S, T extends FormInstance<S>> FormToolkit<S, T> getFormToolkit(Class<T> formInstanceRequired)
			throws ServiceNotFoundException {
		Iterator<FormToolkit> it = loader.iterator();
		FormToolkit toolkit = null;
		while (toolkit == null && it.hasNext()) {
			FormToolkit tl = it.next();
			if (formInstanceRequired.isAssignableFrom(tl.getFormInstanceClass())) {
				toolkit = tl;
			}
		}
		if (toolkit == null) {
			throw new ServiceNotFoundException();
		} else {
			return toolkit;
		}
	}
}

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

import java.util.Iterator;
import java.util.ServiceLoader;

import javax.management.ServiceNotFoundException;

@SuppressWarnings("rawtypes")
public class BFormService {

	private static BFormService instance;

	private ServiceLoader<BFormToolkit> loader;

	private BFormService() {
		loader = ServiceLoader.load(BFormToolkit.class);
	}

	public static synchronized BFormService getInstance() {
		if (instance == null) {
			instance = new BFormService();
		}
		return instance;
	}

	@SuppressWarnings({ "unchecked" })
	public <S, T extends BFormInstance<?, S>> BFormToolkit<S, T> getFormToolkit(Class<T> formInstanceRequired)
			throws ServiceNotFoundException {
		Iterator<BFormToolkit> it = loader.iterator();
		BFormToolkit toolkit = null;
		while (toolkit == null && it.hasNext()) {
			BFormToolkit tl = it.next();
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

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

public class BindingService {

	private static BindingService instance;

	private ServiceLoader<BindingToolkit> loader;

	private BindingService() {
		loader = ServiceLoader.load(BindingToolkit.class);
	}

	public static synchronized BindingService getInstance() {
		if (instance == null) {
			instance = new BindingService();
		}
		return instance;
	}

	public BindingToolkit getBindingToolkit() throws ServiceNotFoundException {
		Iterator<BindingToolkit> it = loader.iterator();
		BindingToolkit toolkit = null;
		while (toolkit == null && it.hasNext()) {
			BindingToolkit tl = it.next();
			toolkit = tl;
		}
		if (toolkit == null) {
			throw new ServiceNotFoundException();
		} else {
			return toolkit;
		}
	}

}

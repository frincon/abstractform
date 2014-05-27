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

import org.abstractform.binding.BFormInstance;
import org.abstractform.binding.BPresenter;

/**
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 */
public class BeanBasedBFForm<S> extends BFForm<S> {

	public BeanBasedBFForm(String id, String name, final Class<S> beanClass) {
		super(id, name);
		putExtraFormObject(BeanConstants.EXTRA_OBJECT_BEAN_CLASS, beanClass);
	}

	@Override
	public BPresenter createPresenter(BFormInstance<S, ?> formInstance, S model) {
		return new BeanBasedPresenter<S>(model);
	}

	@SuppressWarnings("unchecked")
	public Class<S> getBeanClass() {
		return (Class<S>) getExtraFormObject(BeanConstants.EXTRA_OBJECT_BEAN_CLASS);
	}

}

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

import org.abstractform.core.fluent.FTabSheet;

public class BFTabSheet extends FTabSheet {

	private Class<?> beanClass;

	protected BFTabSheet(String id, Class<?> beanClass) {
		super(id);
		this.beanClass = beanClass;
	}

	@Override
	public BFTab addTab(String id, String name) {
		BFTab tab = new BFTab(id, name, beanClass);
		addComponent(tab);
		return tab;
	}

}

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

import org.abstractform.binding.fluent.selector.BFSelector;
import org.abstractform.binding.fluent.table.BFTable;

public class DefaultBFFieldFactory implements BFFieldFactory {

	@Override
	public BFField buildBFField(String id, String name, Class<?> beanClass, String propertyName) {
		return new BFField(id, name, beanClass, propertyName);
	}

	@Override
	public <T extends BFField> T buildBFField(String id, String name, Class<?> beanClass, String propertyName, Class<T> fieldClass) {
		if (fieldClass.equals(BFField.class)) {
			return (T) buildBFField(id, name, beanClass, propertyName);
		} else if (fieldClass.equals(BFSelector.class)) {
			return (T) new BFSelector(id, name, beanClass, propertyName);
		} else if (fieldClass.equals(BFTable.class)) {
			return (T) buildBFTable(id, name, beanClass, propertyName);
		}
		return null;
	}

	private BFTable buildBFTable(String id, String name, Class<?> beanClass, String propertyName) {
		return new BFTable(id, name, beanClass, propertyName);
	}

}

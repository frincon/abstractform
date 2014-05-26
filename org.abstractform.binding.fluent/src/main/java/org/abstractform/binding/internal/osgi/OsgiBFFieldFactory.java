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
package org.abstractform.binding.internal.osgi;

import org.abstractform.binding.fluent.BFField;
import org.abstractform.binding.fluent.BFFieldFactory;
import org.osgi.util.tracker.ServiceTracker;

public class OsgiBFFieldFactory implements BFFieldFactory {

	private ServiceTracker<BFFieldFactory, BFFieldFactory> tracker;

	public OsgiBFFieldFactory(ServiceTracker<BFFieldFactory, BFFieldFactory> tracker) {
		this.tracker = tracker;
	}

	@Override
	public BFField buildBFField(String id, String name, Class<?> beanClass, String propertyName) {
		BFField field = null;
		for (BFFieldFactory fieldFactory : tracker.getTracked().values()) {
			field = fieldFactory.buildBFField(id, name, beanClass, propertyName);
			if (field != null) {
				break;
			}
		}
		assert field != null;
		return field;
	}

	@Override
	public <T extends BFField> T buildBFField(String id, String name, Class<?> beanClass, String propertyName, Class<T> fieldClass) {
		T field = null;
		for (BFFieldFactory fieldFactory : tracker.getTracked().values()) {
			field = fieldFactory.buildBFField(id, name, beanClass, propertyName, fieldClass);
			if (field != null) {
				break;
			}
		}
		assert field != null;
		return field;
	}

}

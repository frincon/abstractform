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

import java.util.Map;

import org.abstractform.binding.fluent.BFField;
import org.abstractform.binding.fluent.BFFieldFactory;

public class BFFieldFactoryService implements BFFieldFactory, Comparable<BFFieldFactoryService> {

	private Integer ranking;
	private BFFieldFactory delegated;

	public BFFieldFactoryService(BFFieldFactory delegated, Integer ranking) {
		this.delegated = delegated;
		this.ranking = ranking;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.binding.fluent.BFFieldFactory#buildBFField(java.lang.String, java.lang.String, java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public BFField buildBFField(String id, String name, String propertyName, Map<String, Object> extraFormObjects) {
		return delegated.buildBFField(id, name, propertyName, extraFormObjects);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.binding.fluent.BFFieldFactory#buildBFField(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.Class, java.util.Map)
	 */
	@Override
	public <T extends BFField> T buildBFField(String id, String name, String propertyName, Class<T> fieldClass,
			Map<String, Object> extraFormObjects) {
		return delegated.buildBFField(id, name, propertyName, fieldClass, extraFormObjects);
	}

	@Override
	public int compareTo(BFFieldFactoryService o) {
		return Integer.compare(ranking, o.ranking);
	}

}

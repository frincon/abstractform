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

import java.util.Map;

import org.abstractform.binding.internal.fluent.BFFieldFactoryProvider;
import org.abstractform.core.fluent.FSubForm;

public class BFSubForm extends FSubForm {

	private Map<String, Object> extraObjects;

	protected BFSubForm(String id, int columns, Map<String, Object> extraObjects) {
		super(id, columns);
		this.extraObjects = extraObjects;
	}

	/**
	 * Create and add field to this subform.
	 * 
	 * @param row
	 *            The row where the field is added
	 * @param column
	 *            The column where the field is added
	 * @param id
	 *            The id of the field, if null a generated id is set
	 * @param name
	 *            The name of the field
	 * @param propertyName
	 *            The name of the property of bean that this field bind to
	 * @return The new field generated
	 */
	public BFField addField(int row, int column, String id, String name, String propertyName) {
		BFField field = BFFieldFactoryProvider.getInstance().getBFFieldFactory().buildBFField(id, name, propertyName, extraObjects);
		internalAddField(row, column, field);
		return field;
	}

	public <T extends BFField> T addField(int row, int column, String id, String name, String propertyName, Class<T> fieldClass) {
		T field = BFFieldFactoryProvider.getInstance().getBFFieldFactory()
				.buildBFField(id, name, propertyName, fieldClass, extraObjects);
		internalAddField(row, column, field);
		return field;
	}

}

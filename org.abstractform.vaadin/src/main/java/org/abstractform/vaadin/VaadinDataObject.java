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
package org.abstractform.vaadin;

import org.abstractform.core.Field;
import org.abstractform.core.selector.SelectorProvider;

public class VaadinDataObject {

	private Field field;
	private VaadinFieldValueAccessor accessor;

	public VaadinDataObject(Field field, VaadinFieldValueAccessor accessor) {
		super();
		this.field = field;
		this.accessor = accessor;
	}

	public Field getField() {
		return field;
	}

	public VaadinFieldValueAccessor getAccessor() {
		return accessor;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void setAccessor(VaadinFieldValueAccessor accessor) {
		this.accessor = accessor;
	}

}

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

import java.lang.reflect.Constructor;

import org.abstractform.core.FormInstance;

import com.vaadin.data.Property;
import com.vaadin.ui.AbstractComponent;

public class NumericPropertyFieldValueAccessor extends PropertyFieldValueAccessor {

	private Class<? extends Number> numberClass;

	public NumericPropertyFieldValueAccessor(Class<? extends Number> numberClass) {
		this.numberClass = numberClass;
	}

	@Override
	public Object getFieldValue(FormInstance formInstance, AbstractComponent field) {
		Property prop = (Property) field;
		Object value = prop.getValue();

		if (value != null && !numberClass.isAssignableFrom(value.getClass())) {
			try {

				// Gets the string constructor
				final Constructor<? extends Number> constr = numberClass.getConstructor(new Class[] { String.class });

				// Creates new object from the string
				value = constr.newInstance(new Object[] { value.toString() });
			} catch (final java.lang.Exception e) {
				throw new Property.ConversionException(e);
			}
		}
		return value;
	}

}

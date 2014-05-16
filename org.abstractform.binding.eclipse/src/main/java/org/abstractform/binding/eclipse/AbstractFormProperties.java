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
package org.abstractform.binding.eclipse;

import org.abstractform.binding.BForm;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;

public class AbstractFormProperties {

	private AbstractFormProperties() {
	}

	// TODO Generalize with pojo properties

	public static IValueProperty field(String fieldId) {
		return new FieldValueProperty(fieldId);
	}

	public static <T> IValueProperty value(BForm<T> form) {
		return new FormValueProperty<T>(form.getBeanClass());
	}

	public static IValueProperty readOnlyField(String fieldId) {
		return new FieldReadOnlyProperty(fieldId);
	}

	public static IValueProperty errorComponent(String fieldId) {
		return new ErrorComponentProperty(fieldId);
	}
	
	public static IListProperty validationStatusSummary() {
		return new FormValidationSummaryProperty();
	}

}

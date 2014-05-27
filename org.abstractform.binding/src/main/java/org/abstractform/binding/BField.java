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

import org.abstractform.binding.validation.Validator;
import org.abstractform.core.Field;

/**
 * Field of binding form
 * 
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 */
public interface BField extends Field {

	/**
	 * The property name that this field will be binding
	 * 
	 * @return The property name that this field will be binding
	 */
	public String getPropertyName();

	/**
	 * Returns the property name that will be binding to the read only property of the field
	 * 
	 * @return The property name that will be binding to the read only property of the field.
	 *         Null if this field will not be binding its read only property (its readOnly property is fixed)
	 */
	public String getReadOnlyPropertyName();

	/**
	 * Returns the validator of this field
	 * 
	 * @return The validator of this field. Null if this field hasn't validator
	 */
	public Validator<?> getValidator();

}

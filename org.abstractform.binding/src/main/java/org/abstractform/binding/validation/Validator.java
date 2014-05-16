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
package org.abstractform.binding.validation;

import java.util.List;

/**
 * This interface must be implemented by all validators
 * 
 * @author Fernando Rincon <frm.rincon@gmail.com>
 * 
 * @param <T>
 */
public interface Validator<T> {

	/**
	 * Validate object and return errors (if any)
	 * 
	 * @param value
	 *            The object to validate
	 * @return A list of Errors Strings, if the validation is ok then can return
	 *         null or emty list
	 */
	public List<String> validate(T value);
}

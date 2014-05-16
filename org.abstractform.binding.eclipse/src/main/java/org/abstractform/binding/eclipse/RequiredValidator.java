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

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;

public class RequiredValidator implements IValidator {

	public static final RequiredValidator INSTANCE = new RequiredValidator();

	private RequiredValidator() {

	}

	@Override
	public IStatus validate(Object value) {
		if (value == null) {
			return RequiredValidationStatus.INSTANCE;
		} else {
			return RequiredValidationStatus.OK_STATUS;
		}
	}
}

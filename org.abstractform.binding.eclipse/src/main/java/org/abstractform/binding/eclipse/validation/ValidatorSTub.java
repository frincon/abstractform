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
package org.abstractform.binding.eclipse.validation;

import java.util.List;

import org.abstractform.binding.validation.Validator;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

@SuppressWarnings("rawtypes")
public class ValidatorSTub implements IValidator {

	private Validator delegate;

	public ValidatorSTub(Validator delegate) {
		this.delegate = delegate;
	}

	@Override
	public IStatus validate(Object value) {
		List<String> errorList = delegate.validate(value);
		if (errorList == null || errorList.isEmpty()) {
			return ValidationStatus.ok();
		} else if (errorList.size() == 1) {
			return ValidationStatus.error(errorList.get(0));
		} else {
			MultiStatus multi = new MultiStatus(null, 0, "Multiple errors.", null);
			for (String error : errorList) {
				multi.add(ValidationStatus.error(error));
			}
			return multi;
		}
	}

}

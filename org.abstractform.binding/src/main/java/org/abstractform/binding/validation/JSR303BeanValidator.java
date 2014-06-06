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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

public class JSR303BeanValidator implements Validator<Object> {

	private Class<?> beanClass;
	private String propertyName;

	public JSR303BeanValidator(Class<?> beanClass, String propertyName) {
		this.beanClass = beanClass;
		this.propertyName = propertyName;
	}

	@Override
	public List<String> validate(Object value) {
		javax.validation.Validator validator = BeanValidationProvider.INSTANCE.getDefaultValidator();
		Set<?> tempList = validator.validateValue(beanClass, propertyName, value);
		List<String> result = new ArrayList<String>(tempList.size());
		for (Object obj : tempList) {
			ConstraintViolation<?> violation = (ConstraintViolation<?>) obj;
			result.add(violation.getMessage());
		}
		return Collections.unmodifiableList(result);
	}
}

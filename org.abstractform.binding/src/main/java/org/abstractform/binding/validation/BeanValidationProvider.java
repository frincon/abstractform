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

import javax.validation.Validation;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.PropertyDescriptor;

public class BeanValidationProvider {

	public static final BeanValidationProvider INSTANCE = new BeanValidationProvider();

	private javax.validation.Validator defaultValidator = null;

	private BeanValidationProvider() {
		// Check for default validator
		try {
			defaultValidator = Validation.buildDefaultValidatorFactory().getValidator();
		} catch (Exception ex) {
			System.err.println("Default Bean Validation not found: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public Validator<?> buildValidator(Class<?> beanClass, String propertyName) {
		javax.validation.Validator beanValidator = getDefaultValidator();
		if (beanValidator != null) {
			BeanDescriptor descriptor = beanValidator.getConstraintsForClass(beanClass);

			PropertyDescriptor propDescriptor = descriptor.getConstraintsForProperty(propertyName);
			if (propDescriptor != null && propDescriptor.hasConstraints()) {
				return new JSR303BeanValidator(beanClass, propertyName);
			}
		}
		return null;
	}

	public javax.validation.Validator getDefaultValidator() {
		return defaultValidator;
	}
}

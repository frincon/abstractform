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
import org.abstractform.core.Form;

/**
 * Represents a Form definition with binding capabilities
 * 
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 * @param <S>
 *            The class of the objects passed as model of the form
 */
public interface BForm<S> extends Form {

	/**
	 * Create new presenter with initial value of model
	 * 
	 * @param model
	 *            Initial value, can be null
	 * @return A presenter. The return value can't be null
	 */
	public BPresenter createPresenter(BFormInstance<S, ?> formInstance, S model);

	/**
	 * Return if the validation summary is visible
	 * 
	 * @return true when this form thefinition has the validation summary visible
	 */
	public boolean isValidationSummaryVisible();

	/**
	 * Return the validator of the form.
	 * 
	 * This validator can validate entire form but is independent of the validators of fields.
	 * 
	 * If this validator OR one of the validators of fields has errors, the form will have validation errors.
	 * 
	 * @return The validator of the form, null if this form definition has validator
	 */
	public Validator<BFormInstance<S, ?>> getValidator();

}

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
package org.abstractform.binding.fluent;

import org.abstractform.binding.BForm;
import org.abstractform.binding.BFormInstance;
import org.abstractform.binding.BPresenter;
import org.abstractform.binding.validation.CompoundValidator;
import org.abstractform.binding.validation.Validator;
import org.abstractform.core.fluent.FForm;

public class BFForm<S, T> extends FForm implements BForm<S, T> {

	private Class<T> beanClass;

	private boolean validationSummaryVisible = true;

	private CompoundValidator<BFormInstance<S, ?>> validator = new CompoundValidator<BFormInstance<S, ?>>();

	public BFForm(String id, String name, Class<T> beanClass) {
		super(id, name);
		this.beanClass = beanClass;
	}

	@Override
	public BFSubForm addSubForm(String id, int columns) {
		BFSubForm subForm = new BFSubForm(id, columns, beanClass);
		addComponent(subForm);
		return subForm;
	}

	@Override
	public BFDrawer addDrawer(String id, String name) {
		BFDrawer drawer = new BFDrawer(id, name, beanClass);
		addComponent(drawer);
		return drawer;

	}

	@Override
	public BFSection addSection(String id, String name) {
		BFSection section = new BFSection(id, name, beanClass);
		addComponent(section);
		return section;
	}

	@Override
	public BFTabSheet addTabSheet(String id) {
		BFTabSheet tabSheet = new BFTabSheet(id, beanClass);
		addComponent(tabSheet);
		return tabSheet;
	}

	@Override
	public Class<T> getBeanClass() {
		return beanClass;
	}

	@Override
	public BPresenter createPresenter(BFormInstance<S, ?> formInstance, S model) {
		return new BFBeanBasedPresenter<S>(model);
	}

	public boolean isValidationSummaryVisible() {
		return validationSummaryVisible;
	}

	public void setValidationSummaryVisible(boolean validationSummaryVisible) {
		this.validationSummaryVisible = validationSummaryVisible;
	}

	public BFForm<S, T> validationSummaryVisible(boolean validationSummaryVisible) {
		setValidationSummaryVisible(validationSummaryVisible);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.binding.BForm#getValidator()
	 */
	@Override
	public Validator<BFormInstance<S, ?>> getValidator() {
		return validator;
	}

	/**
	 * Add model level validator to this form
	 * 
	 * @param validator
	 * @return
	 */
	public BFForm<S, T> validator(Validator<BFormInstance<S, ?>> validator) {
		this.validator.addValidator(validator);
		return this;
	}

}

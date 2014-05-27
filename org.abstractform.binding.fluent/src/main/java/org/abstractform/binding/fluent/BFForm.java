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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.abstractform.binding.BForm;
import org.abstractform.binding.BFormInstance;
import org.abstractform.binding.validation.CompoundValidator;
import org.abstractform.binding.validation.Validator;
import org.abstractform.core.fluent.FForm;

public abstract class BFForm<S> extends FForm implements BForm<S> {

	private boolean validationSummaryVisible = true;

	private Map<String, Object> extraFormObjects = new HashMap<String, Object>();

	private CompoundValidator<BFormInstance<S, ?>> validator = new CompoundValidator<BFormInstance<S, ?>>();

	public BFForm(String id, String name) {
		this(id, name, Collections.<String, Object> emptyMap());
	}

	public BFForm(String id, String name, Map<String, Object> extraFormObjects) {
		super(id, name);
		this.extraFormObjects.putAll(extraFormObjects);
	}

	public void putExtraFormObject(String key, Object value) {
		extraFormObjects.put(key, value);
	}

	public Object getExtraFormObject(String key) {
		return extraFormObjects.get(key);
	}

	@Override
	public BFSubForm addSubForm(String id, int columns) {
		BFSubForm subForm = new BFSubForm(id, columns, extraFormObjects);
		addComponent(subForm);
		return subForm;
	}

	@Override
	public BFDrawer addDrawer(String id, String name) {
		BFDrawer drawer = new BFDrawer(id, name, extraFormObjects);
		addComponent(drawer);
		return drawer;

	}

	@Override
	public BFSection addSection(String id, String name) {
		BFSection section = new BFSection(id, name, extraFormObjects);
		addComponent(section);
		return section;
	}

	@Override
	public BFTabSheet addTabSheet(String id) {
		BFTabSheet tabSheet = new BFTabSheet(id, extraFormObjects);
		addComponent(tabSheet);
		return tabSheet;
	}

	public boolean isValidationSummaryVisible() {
		return validationSummaryVisible;
	}

	public void setValidationSummaryVisible(boolean validationSummaryVisible) {
		this.validationSummaryVisible = validationSummaryVisible;
	}

	public BFForm<S> validationSummaryVisible(boolean validationSummaryVisible) {
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
	public BFForm<S> validator(Validator<BFormInstance<S, ?>> validator) {
		this.validator.addValidator(validator);
		return this;
	}

}

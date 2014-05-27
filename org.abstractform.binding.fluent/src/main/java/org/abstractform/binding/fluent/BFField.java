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

import org.abstractform.binding.BField;
import org.abstractform.binding.validation.Validator;
import org.abstractform.core.fluent.FField;

public class BFField extends FField implements BField {

	private String propertyName;
	private String readOnlyPropertyName;
	private Validator<?> validator;

	/**
	 * This constructor is not intented to call from clients, only from field
	 * factories.
	 * 
	 * @param id
	 * @param name
	 * @param beanClass
	 * @param propertyName
	 */
	public BFField(String id, String name, String propertyName) {
		super(id, name);
		this.propertyName = propertyName;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public BFField readOnly(boolean readOnly) {
		super.readOnly(readOnly);
		return this;
	}

	@Override
	public BFField required(boolean required) {
		super.required(required);
		return this;
	}

	@Override
	public BFField description(String description) {
		super.description(description);
		return this;
	}

	@Override
	public BFField displayWidth(int displayWidth) {
		super.displayWidth(displayWidth);
		return this;
	}

	@Override
	public BFField maxLength(int maxLength) {
		super.maxLength(maxLength);
		return this;
	}

	@Override
	public BFField type(String type) {
		super.type(type);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.binding.BField#getReadOnlyPropertyName()
	 */
	@Override
	public String getReadOnlyPropertyName() {
		return readOnlyPropertyName;
	}

	public void setReadOnlyPropertyName(String readOnlyPropertyName) {
		this.readOnlyPropertyName = readOnlyPropertyName;
	}

	public BFField readOnlyPropertyName(String readOnlyPropertyName) {
		setReadOnlyPropertyName(readOnlyPropertyName);
		return this;
	}

	public Validator<?> getValidator() {
		return validator;
	}

	public void setValidator(Validator<?> validator) {
		this.validator = validator;
	}

	public BFField validator(Validator<?> validator) {
		setValidator(validator);
		return this;
	}

	@Override
	public BFField extra(String key, Object extraObject) {
		super.extra(key, extraObject);
		return this;
	}

}

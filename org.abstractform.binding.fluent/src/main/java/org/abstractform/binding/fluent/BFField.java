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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Date;

import org.abstractform.binding.BField;
import org.abstractform.binding.internal.validation.BeanValidationProvider;
import org.abstractform.binding.validation.Validator;
import org.abstractform.core.fluent.FField;

public class BFField extends FField implements BField {

	private Class<?> beanClass;
	private String propertyName;
	private String readOnlyPresenterProperty;
	private Validator<?> validator;
	private PropertyDescriptor propertyDescriptor;

	/**
	 * This constructor is not intented to call from clients, only from field
	 * factories.
	 * 
	 * @param id
	 * @param name
	 * @param beanClass
	 * @param propertyName
	 */
	public BFField(String id, String name, Class<?> beanClass, String propertyName) {
		super(id, name);
		this.beanClass = beanClass;
		this.propertyName = propertyName;
		try {
			this.propertyDescriptor = new PropertyDescriptor(propertyName, beanClass);
			fillFromProperty();
		} catch (IntrospectionException e) {
			System.out.println("WARN: Property not found, not filled type neither validation");
		}

	}

	public Class<?> getBeanClass() {
		return beanClass;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public BFField fillFromProperty() {
		fillTypeFromProperty();
		fillValidationFromProperty();
		return this;
	}

	public BFField fillTypeFromProperty() {
		Class<?> propertyClass = propertyDescriptor.getPropertyType();
		if (Boolean.class.isAssignableFrom(propertyClass) || boolean.class.isAssignableFrom(propertyClass)) {
			setType(TYPE_BOOL);
		} else if (Date.class.isAssignableFrom(propertyClass)) {
			setType(TYPE_DATETIME);
		} else if (String.class.isAssignableFrom(propertyClass)) {
			setType(TYPE_TEXT);
		} else if (Number.class.isAssignableFrom(propertyClass)) {
			setType(TYPE_NUMERIC);
			this.setExtra(EXTRA_NUMBER_CLASS, propertyClass);
		}
		return this;
	}

	/**
	 * Try to contruct default validator for property.
	 * 
	 * The default implementation find Bean Validation Factory (JSR303) if
	 * exists, construct a validator
	 * using it
	 * 
	 * @return
	 */
	public BFField fillValidationFromProperty() {
		Validator<?> validator = BeanValidationProvider.INSTANCE.buildValidator(beanClass, propertyName);
		if (validator != null) {
			setValidator(validator);
		}
		return this;
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

	@Override
	public String getReadOnlyPresenterProperty() {
		return readOnlyPresenterProperty;
	}

	public void setReadOnlyPresenterProperty(String readOnlyPresenterProperty) {
		this.readOnlyPresenterProperty = readOnlyPresenterProperty;
	}

	public BFField readOnlyPresenterProperty(String readOnlyPresenterProperty) {
		setReadOnlyPresenterProperty(readOnlyPresenterProperty);
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

	public PropertyDescriptor getPropertyDescriptor() {
		return propertyDescriptor;
	}

}

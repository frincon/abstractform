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

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.Map;

import org.abstractform.binding.fluent.selector.BFSelector;
import org.abstractform.binding.fluent.table.BFTable;
import org.abstractform.binding.fluent.table.BFTableField;
import org.abstractform.binding.internal.validation.BeanValidationProvider;
import org.abstractform.binding.validation.Validator;

public class DefaultBFFieldFactory implements BFFieldFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.binding.fluent.BFFieldFactory#buildBFField(java.lang.String, java.lang.String, java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public BFField buildBFField(String id, String name, String propertyName, Map<String, Object> extraFormObjects) {
		BFField field = new BFField(id, name, propertyName);
		checkAndFillIfBeanClass(field, extraFormObjects);
		return field;
	}

	protected void checkAndFillIfBeanClass(BFField field, Map<String, Object> extraFormObjects) {
		Class<?> beanClass = (Class<?>) extraFormObjects.get(BeanConstants.EXTRA_OBJECT_BEAN_CLASS);
		if (beanClass != null) {
			fillFromBeanClass(field, beanClass);
		}
	}

	protected void fillFromBeanClass(BFField field, Class<?> beanClass) {
		try {
			PropertyDescriptor desc = new PropertyDescriptor(field.getPropertyName(), beanClass);
			fillTypeFromProperty(field, beanClass, desc);
			fillValidationFromProperty(field, beanClass, desc);
		} catch (Exception e) {
			System.err.println("WARN: Bean property not found" + field.getPropertyName());
		}
	}

	/**
	 * @param field
	 * @param beanClass
	 * @param desc
	 */
	protected void fillValidationFromProperty(BFField field, Class<?> beanClass, PropertyDescriptor desc) {
		Validator<?> validator = BeanValidationProvider.INSTANCE.buildValidator(beanClass, field.getPropertyName());
		if (validator != null) {
			field.setValidator(validator);
		}
	}

	/**
	 * @param field
	 * @param beanClass
	 * @param desc
	 */
	protected void fillTypeFromProperty(BFField field, Class<?> beanClass, PropertyDescriptor desc) {
		Class<?> propertyClass = desc.getPropertyType();
		if (Boolean.class.isAssignableFrom(propertyClass) || boolean.class.isAssignableFrom(propertyClass)) {
			field.setType(BFField.TYPE_BOOL);
		} else if (Date.class.isAssignableFrom(propertyClass)) {
			field.setType(BFField.TYPE_DATETIME);
		} else if (String.class.isAssignableFrom(propertyClass)) {
			field.setType(BFField.TYPE_TEXT);
		} else if (Number.class.isAssignableFrom(propertyClass)) {
			field.setType(BFField.TYPE_NUMERIC);
			field.setExtra(BFField.EXTRA_NUMBER_CLASS, propertyClass);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.binding.fluent.BFFieldFactory#buildBFField(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.Class, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends BFField> T buildBFField(String id, String name, String propertyName, Class<T> fieldClass,
			Map<String, Object> extraFormObjects) {
		if (fieldClass.equals(BFField.class)) {
			return (T) buildBFField(id, name, propertyName, extraFormObjects);
		} else if (fieldClass.equals(BFSelector.class)) {
			BFSelector bfSelector = new BFSelector(id, name, propertyName);
			// TODO Fill validator?
			return (T) bfSelector;
		} else if (fieldClass.equals(BFTable.class)) {
			return (T) buildBFTable(id, name, propertyName, extraFormObjects);
		} else if (fieldClass.equals(BFTableField.class)) {
			return (T) buildBFTableField(id, name, propertyName, extraFormObjects);
		}
		return null;
	}

	/**
	 * @param id
	 * @param name
	 * @param propertyName
	 * @param extraFormObjects
	 * @return
	 */
	private BFTableField buildBFTableField(String id, String name, String propertyName, Map<String, Object> extraFormObjects) {
		BFTableField field = new BFTableField(id, name, propertyName);
		checkAndFillIfBeanClass(field, extraFormObjects);
		return field;
	}

	private BFTable buildBFTable(String id, String name, String propertyName, Map<String, Object> extraFormObjects) {
		return new BFTable(id, name, propertyName);
	}

}

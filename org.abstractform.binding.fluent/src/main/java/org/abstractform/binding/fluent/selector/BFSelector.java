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
package org.abstractform.binding.fluent.selector;

import org.abstractform.binding.fluent.BFField;
import org.abstractform.binding.validation.Validator;
import org.abstractform.core.fluent.selector.FSelectorMixin;
import org.abstractform.core.fluent.selector.IFSelector;
import org.abstractform.core.selector.SelectorConstants;
import org.abstractform.core.selector.SelectorProviderFactory;

public class BFSelector extends BFField implements IFSelector {

	public BFSelector(String id, String name, Class<?> beanClass, String propertyName) {
		super(id, name, beanClass, propertyName);
		setType(SelectorConstants.TYPE_SELECTOR);
	}

	@Override
	public BFSelector readOnly(boolean readOnly) {
		return (BFSelector) super.readOnly(readOnly);
	}

	@Override
	public BFSelector required(boolean required) {
		return (BFSelector) super.required(required);
	}

	@Override
	public BFSelector description(String description) {
		return (BFSelector) super.description(description);
	}

	@Override
	public BFSelector displayWidth(int displayWidth) {
		return (BFSelector) super.displayWidth(displayWidth);
	}

	@Override
	public BFSelector maxLength(int maxLength) {
		return (BFSelector) super.maxLength(maxLength);
	}

	@Override
	public BFSelector type(String type) {
		return (BFSelector) super.type(type);
	}

	@Override
	public BFSelector readOnlyPresenterProperty(String readOnlyPresenterProperty) {
		return (BFSelector) super.readOnlyPresenterProperty(readOnlyPresenterProperty);
	}

	@Override
	public BFSelector validator(Validator<?> validator) {
		return (BFSelector) super.validator(validator);
	}

	@Override
	public BFSelector extra(String key, Object extraObject) {
		return (BFSelector) super.extra(key, extraObject);
	}

	@Override
	public SelectorProviderFactory getSelectorProviderFactory() {
		return FSelectorMixin.getSelectorProviderFactory(this);
	}

	@Override
	public void setSelectorProviderFactory(SelectorProviderFactory selectorProviderFactory) {
		FSelectorMixin.setSelectorProviderFactory(this, selectorProviderFactory);
	}

	@Override
	public BFSelector selectorProviderFactory(SelectorProviderFactory selectorProviderFactory) {
		this.setSelectorProviderFactory(selectorProviderFactory);
		return this;
	}

}

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
package org.abstractform.core.fluent.selector;

import org.abstractform.core.fluent.FField;
import org.abstractform.core.selector.SelectorConstants;
import org.abstractform.core.selector.SelectorProviderFactory;

public class FSelector extends FField implements IFSelector {

	public FSelector(String id, String name) {
		super(id, name);
		setType(SelectorConstants.TYPE_SELECTOR);
	}

	@Override
	public FSelector description(String description) {
		return (FSelector) super.description(description);
	}

	@Override
	public FSelector readOnly(boolean readOnly) {
		return (FSelector) super.readOnly(readOnly);
	}

	@Override
	public FSelector required(boolean required) {
		return (FSelector) super.required(required);
	}

	@Override
	public FSelector displayWidth(int displayWidth) {
		return (FSelector) super.displayWidth(displayWidth);
	}

	@Override
	public FSelector maxLength(int maxLength) {
		return (FSelector) super.maxLength(maxLength);
	}

	@Override
	public FSelector type(String type) {
		return (FSelector) super.type(type);
	}

	@Override
	public FSelector extra(String key, Object extraObject) {
		return (FSelector) super.extra(key, extraObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.core.selector.IFSelector#getSelectorProvider()
	 */
	@Override
	public SelectorProviderFactory getSelectorProviderFactory() {
		return FSelectorMixin.getSelectorProviderFactory(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.core.selector.IFSelector#setSelectorProvider(org.
	 * abstractform.core.selector.SelectorProvider)
	 */
	@Override
	public void setSelectorProviderFactory(SelectorProviderFactory selectorProviderFactory) {
		FSelectorMixin.setSelectorProviderFactory(this, selectorProviderFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.abstractform.core.selector.IFSelector#selectorProvider(org.abstractform
	 * .core.selector.SelectorProvider)
	 */
	@Override
	public FSelector selectorProviderFactory(SelectorProviderFactory selectorProviderFactory) {
		this.setSelectorProviderFactory(selectorProviderFactory);
		return this;
	}

}

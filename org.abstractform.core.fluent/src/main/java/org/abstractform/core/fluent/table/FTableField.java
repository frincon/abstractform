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
package org.abstractform.core.fluent.table;

import org.abstractform.core.fluent.FField;

public class FTableField extends FField  {

	protected FTableField(String id, String name) {
		super(id, name);
	}

	@Override
	public FTableField readOnly(boolean readOnly) {
		return (FTableField) super.readOnly(readOnly);
	}

	@Override
	public FTableField required(boolean required) {
		return (FTableField) super.required(required);
	}

	@Override
	public FTableField description(String description) {
		return (FTableField) super.description(description);
	}

	@Override
	public FTableField displayWidth(int displayWidth) {
		return (FTableField) super.displayWidth(displayWidth);
	}

	@Override
	public FTableField maxLength(int maxLength) {
		return (FTableField) super.maxLength(maxLength);
	}

	@Override
	public FTableField type(String type) {
		return (FTableField) super.type(type);
	}

	@Override
	public FTableField extra(String key, Object extraObject) {
		return (FTableField) super.extra(key, extraObject);
	}

}

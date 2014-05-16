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
package org.abstractform.core.fluent;

import java.util.HashMap;
import java.util.Map;

import org.abstractform.core.Field;

public class FField extends FAbstractComponent implements Field {

	private String name;
	private boolean readOnly = false;
	private String description;
	private boolean required;
	private int displayWidth;
	private int maxLength;
	private String type;
	private Map<String, Object> extraMap = new HashMap<String, Object>();

	protected FField(String id, String name) {
		super(id);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public boolean isReadOnly() {
		return this.readOnly;
	}

	@Override
	public boolean isRequired() {
		return this.required;
	}

	@Override
	public int getDisplayWidth() {
		return this.displayWidth;
	}

	@Override
	public int getMaxLength() {
		return this.maxLength;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setDisplayWidth(int displayWidth) {
		this.displayWidth = displayWidth;
	}

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	// For fluent api

	public FField readOnly(boolean readOnly) {
		setReadOnly(readOnly);
		return this;
	}

	public FField required(boolean required) {
		setRequired(required);
		return this;
	}

	public FField description(String description) {
		setDescription(description);
		return this;
	}

	public FField displayWidth(int displayWidth) {
		setDisplayWidth(displayWidth);
		return this;
	}

	public FField maxLength(int maxLength) {
		setMaxLength(maxLength);
		return this;
	}

	public FField type(String type) {
		setType(type);
		return this;
	}

	@Override
	public Object getExtra(String key) {
		return extraMap.get(key);
	}

	public void setExtra(String key, Object extraObject) {
		extraMap.put(key, extraObject);
	}

	public FField extra(String key, Object extraObject) {
		setExtra(key, extraObject);
		return this;
	}
}

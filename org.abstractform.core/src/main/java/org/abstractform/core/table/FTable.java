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
package org.abstractform.core.table;

import java.util.ArrayList;
import java.util.List;

import org.abstractform.core.fluent.FField;

public class FTable extends FField implements IFTable {

	private List<FTableField> tableFieldList = new ArrayList<FTableField>();

	public FTable(String id, String name) {
		super(id, name);
		setType(IFTable.TYPE_TABLE);
		setPageLenght(DEFAULT_PAGE_LENGTH);
		setExtra(IFTable.EXTRA_TABLE_FIELD_LIST, tableFieldList);
		//TODO Ensure to prevent changes
	}

	@Override
	public FTable readOnly(boolean readOnly) {
		return (FTable) super.readOnly(readOnly);
	}

	@Override
	public FTable required(boolean required) {
		return (FTable) super.required(required);
	}

	@Override
	public FTable description(String description) {
		return (FTable) super.description(description);
	}

	@Override
	public FTable displayWidth(int displayWidth) {
		return (FTable) super.displayWidth(displayWidth);
	}

	@Override
	public FTable maxLength(int maxLength) {
		return (FTable) super.maxLength(maxLength);
	}

	@Override
	public FTable type(String type) {
		return (FTable) super.type(type);
	}

	@Override
	public FTable extra(String key, Object extraObject) {
		return (FTable) super.extra(key, extraObject);
	}

	public FTableField addTableField(String id, String name) {
		FTableField tableField = new FTableField(id, name);
		tableFieldList.add(tableField);
		return tableField;
	}

	public void setPageLenght(int pageLenght) {
		setExtra(IFTable.EXTRA_TABLE_PAGE_LENGHT, pageLenght);
	}

	public int getPageLenght() {
		return (Integer) getExtra(IFTable.EXTRA_TABLE_PAGE_LENGHT);
	}

	public FTable pageLenght(int pageLenght) {
		setPageLenght(pageLenght);
		return this;
	}

}

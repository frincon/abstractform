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
package org.abstractform.binding.fluent.table;

import java.util.ArrayList;
import java.util.List;

import org.abstractform.binding.fluent.BFField;
import org.abstractform.core.fluent.table.IFTable;
import org.abstractform.core.table.TableConstants;

public class BFTable extends BFField implements IFTable, TableConstants {

	private Class<?> elementsClass;
	private List<BFTableField> tableFieldList = new ArrayList<BFTableField>();

	public BFTable(String id, String name, Class<?> beanClass, String propertyName) {
		super(id, name, beanClass, propertyName);
		setType(TYPE_TABLE);
		setPageLenght(DEFAULT_PAGE_LENGTH);
		setExtra(EXTRA_TABLE_FIELD_LIST, tableFieldList);
	}

	@Override
	public BFTableField addTableField(String name, String propertyName) {
		BFTableField tableField = new BFTableField(null, name, elementsClass, propertyName);
		tableFieldList.add(tableField);
		return tableField;
	}

	public Class<?> getElementsClass() {
		return elementsClass;
	}

	public void setElementsClass(Class<?> elementsClass) {
		this.elementsClass = elementsClass;
	}

	public BFTable elementsClass(Class<?> elementClass) {
		setElementsClass(elementClass);
		return this;
	}

	@Override
	public void setPageLenght(int pageLenght) {
		setExtra(EXTRA_TABLE_PAGE_LENGHT, pageLenght);
	}

	@Override
	public int getPageLenght() {
		return (Integer) getExtra(EXTRA_TABLE_PAGE_LENGHT);
	}

	@Override
	public BFTable pageLenght(int pageLenght) {
		setPageLenght(pageLenght);
		return this;
	}

}

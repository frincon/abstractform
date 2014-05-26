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
import org.abstractform.core.SubForm;

/**
 * 
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 */
public class FSubForm extends FAbstractComponent implements SubForm {

	private int columns;
	private int rows = 0;
	private Map<Integer, Field[]> mapFields = new HashMap<Integer, Field[]>();

	protected FSubForm(String id, int columns) {
		super(id);
		this.columns = columns;
	}

	public FField addField(int row, int column, String id, String name) {
		FField field = new FField(id, name);
		internalAddField(row, column, field);
		return field;
	}

	public <T extends Field> T addField(int row, int column, T field) {
		internalAddField(row, column, field);
		return field;
	}

	protected void internalAddField(int row, int column, Field field) {
		Field fieldRows[] = mapFields.get(row);
		if (fieldRows == null) {
			fieldRows = new FField[columns];
			mapFields.put(row, fieldRows);
		}
		fieldRows[column] = field;
		if (row + 1 > rows) {
			rows = row + 1;
		}

	}

	@Override
	public int getColumns() {
		return columns;
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public Field getField(int row, int column) {
		if (row > rows - 1 || column > columns - 1) {
			throw new IllegalArgumentException();
		}
		Field[] rowFields = mapFields.get(row);
		if (rowFields == null) {
			return null;
		} else {
			return rowFields[column];
		}
	}

}

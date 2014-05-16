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
package org.abstractform.core;

/**
 * The SubForm element represents a part with fields. The subform may have many
 * columns and rows, and in each cell may contain a field.
 * 
 * @author Fernando Rincon <frm.rincon@gmail.com>
 * 
 */
public interface SubForm extends Component {

	/**
	 * The number of columns of this SubForm
	 * 
	 * @return The number of columns
	 */
	public int getColumns();

	/**
	 * The number of rows of this SubForm
	 * 
	 * @return The number of rows
	 */
	public int getRows();

	/**
	 * Get the field of a cell
	 * 
	 * @param row
	 *            The row of the field that must be return
	 * @param column
	 *            The columns of the field than must be return
	 * @return The field of a cell, or null in case of the cell is empty
	 */
	public Component getField(int row, int column);
}

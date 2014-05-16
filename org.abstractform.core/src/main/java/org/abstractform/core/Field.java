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
 * Then field represents a control than can be bind to a property of model and
 * the user can be change using the gui
 * 
 * @author Fernando Rincon <frm.rincon@gmail.com>
 * 
 */
public interface Field extends Component {

	public static final String TYPE_TEXT = "text";
	public static final String TYPE_PASSWORD = "password";
	public static final String TYPE_BOOL = "bool";
	public static final String TYPE_DATETIME = "dateTime";
	public static final String TYPE_NUMERIC = "numeric";

	public static final String EXTRA_NUMBER_CLASS = "EXTRA_NUMBER_CLASS";

	/**
	 * The description of field
	 * 
	 * This property in most cases render as a tooltip in controls
	 * 
	 * @return The description
	 */
	public String getDescription();

	/**
	 * Return if the field must be read only field
	 * 
	 * The field than is readOnly only show information, the user never can
	 * change the value of this field
	 * 
	 * @return true if the field is readonly
	 */
	public boolean isReadOnly();

	/**
	 * If the field is required
	 * 
	 * Most implementations represents required fields by asterisk in the field
	 * name.
	 * 
	 * This field must be used to generate validations but the implementation of
	 * bindinf must be implement this feature
	 * 
	 * 
	 * @return true if the field is require
	 */
	public boolean isRequired();

	/**
	 * Return the desired display with (in units of characters)
	 * 
	 * The tollkit must implement this feature.
	 * 
	 * @return The desired display with of the field or 0 if not set
	 */
	public int getDisplayWidth();

	/**
	 * Return the max length in characters of the field
	 * 
	 * 
	 * @return The max length in characters of the field or 0 if not set
	 */
	public int getMaxLength();

	/**
	 * The name of the field
	 * 
	 * The name is used in toolkits to display de title of the field
	 * 
	 * @return The name assigned or null if not set
	 */
	public String getName();

	/**
	 * The type of field
	 * 
	 * Must be a one of type defined as static fields
	 * 
	 * @return The type of field
	 */
	public String getType();

	/**
	 * Return an extra object specified by the parameter name.
	 * 
	 * Extra objects can hold any type of object whith any name. Use for another
	 * type of fields than need aditional information.
	 * 
	 * @param key
	 *            The key of the extra object
	 * @return The extra object if exists, otherwise <code>null</code>
	 */
	public Object getExtra(String key);

}

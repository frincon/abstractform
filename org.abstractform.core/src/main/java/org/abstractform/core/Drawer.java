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
 * Drawer is a component that represents a container that can be collapsed or expanded
 * 
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 */
public interface Drawer extends Container {

	/**
	 * Returns the name of the drawer
	 * 
	 * In most cases the name is used to render the drawer
	 * 
	 * @return The name of the drawer
	 */
	public String getName();

}

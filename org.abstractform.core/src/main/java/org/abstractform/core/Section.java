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
 * The section is a container with a name, in most implementations must render
 * as box with a title (the name of the section.
 * 
 * @author Fernando Rincon <frm.rincon@gmail.com>
 * 
 */
public interface Section extends Container {

	/**
	 * Return the name of the section
	 * 
	 * The name in most implementations render as header of the section
	 * 
	 * @return The name of the section
	 */
	public String getName();

}

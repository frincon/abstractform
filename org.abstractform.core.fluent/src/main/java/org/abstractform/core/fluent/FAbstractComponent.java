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

import org.abstractform.core.Component;
import org.abstractform.core.fluent.internal.IdGenerator;

/**
 * 
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 */
public class FAbstractComponent implements Component {

	private String id;

	public FAbstractComponent(String id) {
		if (id == null) {
			this.id = IdGenerator.INSTANCE.getId();
		} else {
			this.id = id;
		}
	}

	public String getId() {
		return id;
	}

}

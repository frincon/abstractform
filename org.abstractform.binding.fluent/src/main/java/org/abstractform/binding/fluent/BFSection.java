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
package org.abstractform.binding.fluent;

import java.util.Map;

import org.abstractform.core.fluent.FSection;

public class BFSection extends FSection {

	private Map<String, Object> extraFormObjects;

	public BFSection(String id, String name, Map<String, Object> extraFormObjects) {
		super(id, name);
		this.extraFormObjects = extraFormObjects;
	}

	@Override
	public BFSubForm addSubForm(String id, int columns) {
		BFSubForm subForm = new BFSubForm(id, columns, extraFormObjects);
		addComponent(subForm);
		return subForm;
	}

	@Override
	public BFDrawer addDrawer(String id, String name) {
		BFDrawer drawer = new BFDrawer(id, name, extraFormObjects);
		addComponent(drawer);
		return drawer;

	}

	@Override
	public BFSection addSection(String id, String name) {
		BFSection section = new BFSection(id, name, extraFormObjects);
		addComponent(section);
		return section;
	}

	@Override
	public BFTabSheet addTabSheet(String id) {
		BFTabSheet tabSheet = new BFTabSheet(id, extraFormObjects);
		addComponent(tabSheet);
		return tabSheet;
	}

}

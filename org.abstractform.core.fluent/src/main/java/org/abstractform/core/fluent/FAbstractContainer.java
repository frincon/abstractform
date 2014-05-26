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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.abstractform.core.Component;
import org.abstractform.core.Container;

/**
 * 
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 */
public abstract class FAbstractContainer extends FAbstractComponent implements Container {

	public FAbstractContainer(String id) {
		super(id);
	}

	private List<Component> containerList = new ArrayList<Component>();

	@Override
	public final List<Component> getChildList() {
		return Collections.unmodifiableList(containerList);
	}

	public FSubForm addSubForm(String id, int columns) {
		FSubForm subForm = new FSubForm(id, columns);
		addComponent(subForm);
		return subForm;
	}

	public FDrawer addDrawer(String id, String name) {
		FDrawer drawer = new FDrawer(id, name);
		addComponent(drawer);
		return drawer;
	}

	public FSection addSection(String id, String name) {
		FSection section = new FSection(id, name);
		addComponent(section);
		return section;
	}

	public FTabSheet addTabSheet(String id) {
		FTabSheet tabSheet = new FTabSheet(id);
		addComponent(tabSheet);
		return tabSheet;
	}

	protected synchronized void addComponent(Component component) {
		containerList.add(component);
	}

}

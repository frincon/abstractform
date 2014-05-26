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
package org.abstractform.vaadin.test;

import org.abstractform.core.Form;
import org.abstractform.core.fluent.test.SampleForm;
import org.abstractform.vaadin.VaadinFormInstance;
import org.abstractform.vaadin.VaadinFormToolkit;

import com.vaadin.Application;
import com.vaadin.ui.Window;

public class TestApplication extends Application {

	private static final long serialVersionUID = 7522813594722111623L;

	@Override
	public void init() {
		Window main = new Window("Test window");
		setMainWindow(main);
		VaadinFormToolkit toolkit = new VaadinFormToolkit();
		Form form = new SampleForm();
		VaadinFormInstance formInstance = toolkit.buildForm(form);
		main.addComponent(formInstance.getImplementation());
	}

}

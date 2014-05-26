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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.management.ServiceNotFoundException;

import org.abstractform.core.Form;
import org.abstractform.core.FormService;
import org.abstractform.core.FormToolkit;
import org.abstractform.core.fluent.test.SampleForm;
import org.abstractform.vaadin.VaadinFormInstance;
import org.abstractform.vaadin.VaadinFormToolkit;
import org.junit.Test;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class TestVaadinBuilder {

	@Test
	public void testBuilder() {
		Form form = new SampleForm();
		VaadinFormToolkit toolkit = new VaadinFormToolkit();
		VaadinFormInstance instance = toolkit.buildForm(form);
		assertNotNull(instance);
		Component component = instance.getImplementation();
		assertThat(component, instanceOf(VerticalLayout.class));

		Component cifCode = instance.getComponentById("fCif");
		assertThat(cifCode, instanceOf(TextField.class));

		Component active = instance.getComponentById("fActive");
		assertThat(active, instanceOf(CheckBox.class));

	}

	@Test
	public void testServiceLoader() throws ServiceNotFoundException {
		FormToolkit<Component, VaadinFormInstance> toolkit = FormService.getInstance().getFormToolkit(VaadinFormInstance.class);

		Form form = new SampleForm();
		VaadinFormInstance instance = toolkit.buildForm(form);
		assertNotNull(instance);
		Component component = instance.getImplementation();
		assertThat(component, instanceOf(VerticalLayout.class));

		Component cifCode = instance.getComponentById("fCif");
		assertThat(cifCode, instanceOf(TextField.class));

		Component active = instance.getComponentById("fActive");
		assertThat(active, instanceOf(CheckBox.class));

	}
}

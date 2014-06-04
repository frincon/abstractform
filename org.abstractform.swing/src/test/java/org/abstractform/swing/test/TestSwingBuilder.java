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
package org.abstractform.swing.test;

import static org.junit.Assert.*;

import javax.management.ServiceNotFoundException;
import javax.swing.JComponent;

import org.abstractform.core.Form;
import org.abstractform.core.FormInstance;
import org.abstractform.core.FormService;
import org.abstractform.core.FormToolkit;
import org.abstractform.core.fluent.test.SampleForm;
import org.abstractform.swing.SwingFormToolkit;
import org.junit.Test;

public class TestSwingBuilder {

	@Test
	public void testBuilder() {
		Form form = new SampleForm();
		SwingFormToolkit toolkit = new SwingFormToolkit();
		FormInstance<JComponent> instance = toolkit.buildForm(form);
		assertNotNull(instance);
	}

	@Test
	public void testServiceLoader() throws ServiceNotFoundException {
		FormToolkit<JComponent> toolkit = FormService.getInstance().getFormToolkit(JComponent.class);

		Form form = new SampleForm();
		FormInstance<JComponent> instance = toolkit.buildForm(form);
		assertNotNull(instance);

	}
}

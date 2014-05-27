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
package org.abstractform.itest;

import org.abstractform.binding.BForm;
import org.abstractform.binding.BFormInstance;
import org.abstractform.binding.BFormService;
import org.abstractform.binding.BFormToolkit;
import org.abstractform.binding.fluent.test.SampleForm;
import org.abstractform.test.common.beans.BusinessPartner;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class TestApplication extends Application {

	private static final long serialVersionUID = 7522813594722111623L;

	@Override
	public void init() {
		try {
			Window main = new Window("Test window");
			setMainWindow(main);
			BFormToolkit<Component> toolkit = BFormService.getInstance().getFormToolkit(Component.class);
			BForm<BusinessPartner> form = new SampleForm();
			final BFormInstance<BusinessPartner, Component> formInstance = toolkit.buildForm(form);
			main.addComponent(formInstance.getImplementation());

			final BusinessPartner bean1 = new BusinessPartner();
			bean1.setAbc("A");
			bean1.setActive(true);
			bean1.setCifCode("B55425451");
			bean1.setClient(true);
			bean1.setName("Sample Name");
			bean1.setMail("mail@nomail.org");

			final BusinessPartner bean2 = new BusinessPartner();
			bean2.setAbc("B");
			bean2.setActive(false);
			bean2.setCifCode("OTRO");
			bean2.setClient(false);
			bean2.setName("Otro nombre");
			bean2.setMail(null);

			formInstance.setValue(bean1);

			Button but1 = new Button("Change bean", new Button.ClickListener() {

				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					if (formInstance.getValue() == bean1) {
						formInstance.setValue(bean2);
					} else {
						formInstance.setValue(bean1);
					}
				}
			});

			main.addComponent(but1);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}

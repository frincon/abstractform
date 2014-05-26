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
package org.abstractform.binding.vaadin.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.abstractform.binding.BBindingToolkit;
import org.abstractform.binding.BForm;
import org.abstractform.binding.BFormInstance;
import org.abstractform.binding.BFormService;
import org.abstractform.binding.BFormToolkit;
import org.abstractform.binding.test.SampleForm;
import org.abstractform.binding.vaadin.VaadinBindingFormInstance;
import org.abstractform.binding.vaadin.VaadinBindingFormToolkit;
import org.abstractform.test.common.beans.BusinessPartner;
import org.junit.Test;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class TestVaadinBindingBuilder {

	@Test
	public void testBuilder() throws Exception {
		BForm<BusinessPartner, BusinessPartner> form = new SampleForm();
		VaadinBindingFormToolkit toolkit = new VaadinBindingFormToolkit();
		BBindingToolkit bindingToolkit = mock(BBindingToolkit.class);
		BFormInstance<BusinessPartner, ?> instance = toolkit.buildForm(form, bindingToolkit);
		assertNotNull(instance);
		Component component = (Component) instance.getImplementation();
		assertThat(component, instanceOf(VerticalLayout.class));

		BusinessPartner sampleBean1 = new BusinessPartner();
		sampleBean1.setAbc("A");
		sampleBean1.setOrganization(SampleForm.ORG1);
		sampleBean1.setActive(true);
		sampleBean1.setName("Sample Name");
		sampleBean1.setCifCode("123456");
		sampleBean1.setSearchKey("00001");
		sampleBean1.setCreated(new Date());
		sampleBean1.setUpdated(new Date());
		sampleBean1.setCreatedBy("User 1");
		sampleBean1.setUpdatedBy("User 2");

		instance.setValue(sampleBean1);

		verify(bindingToolkit).bindFields(instance, form);

	}

	@Test
	public void testBuilderWithServiceLoader() throws Exception {
		BForm<BusinessPartner, BusinessPartner> form = new SampleForm();
		BFormToolkit<Component, VaadinBindingFormInstance<?>> toolkit = BFormService.getInstance().getFormToolkit(
				VaadinBindingFormInstance.class);
		BBindingToolkit bindingToolkit = mock(BBindingToolkit.class);
		BFormInstance<BusinessPartner, ?> instance = toolkit.buildForm(form, bindingToolkit);
		assertNotNull(instance);
		Component component = (Component) instance.getImplementation();
		assertThat(component, instanceOf(VerticalLayout.class));

		BusinessPartner sampleBean1 = new BusinessPartner();
		sampleBean1.setAbc("A");
		sampleBean1.setActive(true);
		sampleBean1.setName("Sample Name");
		sampleBean1.setCifCode("123456");
		sampleBean1.setSearchKey("00001");
		sampleBean1.setCreated(new Date());
		sampleBean1.setUpdated(new Date());
		sampleBean1.setCreatedBy("User 1");
		sampleBean1.setUpdatedBy("User 2");

		instance.setValue(sampleBean1);

		verify(bindingToolkit).bindFields(instance, form);

	}
}

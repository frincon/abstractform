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
package org.abstractform.binding.eclipse.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.abstractform.binding.BBindingContext;
import org.abstractform.binding.BBindingService;
import org.abstractform.binding.BBindingToolkit;
import org.abstractform.binding.BFormInstance;
import org.abstractform.binding.eclipse.EclipseBindingToolkit;
import org.abstractform.binding.test.SampleForm;
import org.abstractform.core.Drawer;
import org.abstractform.core.Field;
import org.abstractform.core.Form;
import org.abstractform.core.Section;
import org.abstractform.core.SubForm;
import org.abstractform.test.common.beans.BusinessPartner;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class TestBindingEclipseApi {

	private static final String ST_NAME = "Name";
	private static final String ST_CIF = "Cif";

	private static final String ST_NAME2 = "Other name";
	private static final String ST_CIF2 = "Other cif";
	private static final String ST_ABC2 = "A";

	private static final String ST_FISCAL_NAME = "Fiscal Name example";

	@SuppressWarnings("unchecked")
	@Test
	public void testFluentApi() {
		SampleForm exampleForm = new SampleForm();
		Form form = (Form) exampleForm;
		assertEquals(4, form.getChildList().size());

		SubForm subForm = (SubForm) form.getChildList().get(0);
		Field field = (Field) subForm.getField(0, 0);
		assertEquals("Cif code", field.getName());
		assertEquals(Field.TYPE_TEXT, field.getType());
		assertNotNull(subForm.getField(0, 1));
		assertNull(subForm.getField(1, 0));
		assertNull(subForm.getField(1, 1));

		field = (Field) subForm.getField(2, 0);
		assertEquals("Search Key", field.getName());
		assertEquals(Field.TYPE_TEXT, field.getType());

		field = (Field) subForm.getField(2, 1);
		assertEquals("Active", field.getName());
		assertEquals(Field.TYPE_BOOL, field.getType());

		subForm = (SubForm) ((Section) ((Drawer) form.getChildList().get(2)).getChildList().get(1)).getChildList().get(0);
		field = (Field) subForm.getField(0, 1);
		assertEquals("Created", field.getName());
		assertEquals(Field.TYPE_DATETIME, field.getType());

		BFormInstance<BusinessPartner> mockForm = mock(BFormInstance.class);
		BusinessPartner bean = new BusinessPartner();
		bean.setName(ST_NAME);
		bean.setCifCode(ST_CIF);
		bean.setActive(true);
		bean.setClient(false);

		when(mockForm.getValue()).thenReturn(bean);

		ArgumentCaptor<PropertyChangeListener> listener = ArgumentCaptor.forClass(PropertyChangeListener.class);

		BBindingToolkit bindingToolkit = new EclipseBindingToolkit();
		bindingToolkit.bindFields(mockForm, exampleForm);

		// Verify if has calling addValueChangeListener in form instance
		verify(mockForm, atLeastOnce()).addValuePropertyChangeListener(listener.capture());
		verify(mockForm).setFieldValue(exampleForm.F_NAME.getId(), ST_NAME);
		verify(mockForm).setFieldValue(exampleForm.F_CIF.getId(), ST_CIF);
		verify(mockForm).setFieldValue(exampleForm.F_ORGANIZACION.getId(), null);
		verify(mockForm).setFieldValue(exampleForm.F_SEARCH_KEY.getId(), null);
		verify(mockForm).setFieldValue(exampleForm.F_ACTIVE.getId(), true);
		verify(mockForm).setFieldValue(exampleForm.F_IS_CLIENT.getId(), false);
		verify(mockForm).setFieldValue(exampleForm.F_FISCAL_NAME.getId(), null);

		// Change bean and verify if the field changed
		BusinessPartner bean2 = new BusinessPartner();
		bean2.setActive(false);
		bean2.setClient(true);
		bean2.setEmployee(true);
		bean2.setName(ST_NAME2);
		bean2.setCifCode(ST_CIF2);
		bean2.setAbc(ST_ABC2);

		//prepare mock
		when(mockForm.getValue()).thenReturn(bean2);

		//Launch event
		PropertyChangeEvent evt = new PropertyChangeEvent(mockForm, "value", bean, bean2);
		for (PropertyChangeListener ls : listener.getAllValues()) {
			ls.propertyChange(evt);
		}

		//verify calls
		verify(mockForm).setFieldValue(exampleForm.F_CIF.getId(), ST_CIF2);
		verify(mockForm).setFieldValue(exampleForm.F_NAME.getId(), ST_NAME2);
		verify(mockForm).setFieldValue(exampleForm.F_ACTIVE.getId(), false);
		verify(mockForm).setFieldValue(exampleForm.F_IS_CLIENT.getId(), true);
		verify(mockForm).setFieldValue(exampleForm.F_IS_EMPLOYEE.getId(), true);
		verify(mockForm).setFieldValue(exampleForm.F_FISCAL_NAME.getId(), null);
		verify(mockForm).setFieldValue(exampleForm.F_ABC.getId(), ST_ABC2);

		ArgumentCaptor<PropertyChangeListener> listenerFiscalName = ArgumentCaptor.forClass(PropertyChangeListener.class);
		verify(mockForm).addFieldChangeListener(eq(exampleForm.F_FISCAL_NAME.getId()), listenerFiscalName.capture());

		//Launch field change event
		when(mockForm.getFieldValue(eq(exampleForm.F_FISCAL_NAME.getId()))).thenReturn(ST_FISCAL_NAME);
		evt = new PropertyChangeEvent(mockForm, exampleForm.F_FISCAL_NAME.getId(), null, ST_FISCAL_NAME);
		listenerFiscalName.getValue().propertyChange(evt);

		//Verify
		assertEquals(ST_FISCAL_NAME, bean2.getFiscalName());

	}

	@Test
	public void testFormValidator() {
		SampleForm exampleForm = new SampleForm();
		BFormInstance<BusinessPartner> mockForm = mock(BFormInstance.class);
		BusinessPartner bean = new BusinessPartner();
		bean.setName(ST_NAME);
		bean.setCifCode(ST_CIF);
		bean.setActive(true);
		bean.setClient(false);

		when(mockForm.getValue()).thenReturn(bean);

		BBindingToolkit bindingToolkit = new EclipseBindingToolkit();
		bindingToolkit.bindFields(mockForm, exampleForm);

		ArgumentCaptor<BBindingContext> bindingContextCaptor = ArgumentCaptor.forClass(BBindingContext.class);
		verify(mockForm).setBindingContext(bindingContextCaptor.capture());

		BBindingContext context = bindingContextCaptor.getValue();

		assertFalse(context.getErrors().contains("The value ABC must be A or B or C"));

		ArgumentCaptor<PropertyChangeListener> listenerAbc = ArgumentCaptor.forClass(PropertyChangeListener.class);
		verify(mockForm).addFieldChangeListener(eq(exampleForm.F_ABC.getId()), listenerAbc.capture());
		when(mockForm.getFieldValue(eq(exampleForm.F_ABC.getId()))).thenReturn("D");
		PropertyChangeEvent evt = new PropertyChangeEvent(mockForm, exampleForm.F_ABC.getId(), null, "D");
		for (PropertyChangeListener ls : listenerAbc.getAllValues()) {
			ls.propertyChange(evt);
		}
		verify(mockForm, atLeastOnce()).getFieldValue(eq(exampleForm.F_ABC.getId()));
		//context.updateModel();

		assertTrue(bean.getAbc().equals("D"));

		assertTrue(context.getErrors().contains("The value ABC must be A or B or C"));
	}

	@Test
	public void testServiceLoader() throws Exception {
		BBindingToolkit tl = BBindingService.getInstance().getBindingToolkit();
		assertThat(tl, instanceOf(EclipseBindingToolkit.class));
	}
}

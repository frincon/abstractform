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
package org.abstractform.binding.fluent.test;

import static org.junit.Assert.*;

import org.abstractform.core.Drawer;
import org.abstractform.core.Field;
import org.abstractform.core.Form;
import org.abstractform.core.Section;
import org.abstractform.core.SubForm;
import org.junit.Test;

public class TestBindingFluentApi {

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

	}
}

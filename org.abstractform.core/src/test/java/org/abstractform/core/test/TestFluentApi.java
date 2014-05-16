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
package org.abstractform.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.abstractform.core.Field;
import org.abstractform.core.Form;
import org.abstractform.core.SubForm;
import org.abstractform.core.selector.SelectorConstants;
import org.junit.Test;

public class TestFluentApi {

	@Test
	public void testFluentApi() {
		SampleForm exampleForm = new SampleForm();
		Form form = (Form) exampleForm;
		assertEquals(4, form.getChildList().size());

		SubForm subForm = (SubForm) form.getChildList().get(0);
		Field field = (Field) subForm.getField(0, 0);
		assertEquals("Cif code", field.getName());
		assertNotNull(subForm.getField(0, 1));
		assertNull(subForm.getField(1, 0));
		assertNull(subForm.getField(1, 1));

		field = (Field) subForm.getField(2, 0);
		assertEquals("Search Key", field.getName());

		field = (Field) subForm.getField(0, 1);
		assertEquals("Organization", field.getName());
		assertNotNull(field.getExtra(SelectorConstants.EXTRA_SELECTOR_PROVIDER_FACTORY));

	}
}

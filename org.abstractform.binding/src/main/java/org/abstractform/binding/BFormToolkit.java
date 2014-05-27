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
package org.abstractform.binding;

import java.util.Map;

import org.abstractform.core.FormToolkit;

/**
 * /**
 * Interface that must be implement the specific form builder for specific form instance type.
 * 
 * This form toolkit is binding aware and construct forms with binding capabilities
 * 
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 * @param <S>
 *            The class of the component for especific implementation
 */
public interface BFormToolkit<S> extends FormToolkit<S> {

	public <U> BFormInstance<U, S> buildForm(BForm<U> form, BindingToolkit bindingToolkit, Map<String, Object> extraObjects,
			boolean immediate);

	public <U> BFormInstance<U, S> buildForm(BForm<U> form, BindingToolkit bindingToolkit, Map<String, Object> extraObjects);

	public <U> BFormInstance<U, S> buildForm(BForm<U> form, BindingToolkit bindingToolkit);

	public <U> BFormInstance<U, S> buildForm(BForm<U> form);
}

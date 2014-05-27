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
package org.abstractform.core;

import java.util.Map;

/**
 * Interface that must be implement the specific form builder for specific form instance type
 * 
 * @author Fernando Rincon <frm.rincon@gmail.com>
 * 
 * @param <S>
 *            The class of component that FormInstance must be return as specific implementation
 */
public interface FormToolkit<S> {

	/**
	 * Build given form
	 * 
	 * @param form
	 *            The form definition that must be build
	 * @return The form instance built
	 */
	public FormInstance<S> buildForm(Form form);

	/**
	 * Build given form, pass extra objects to the builder
	 * 
	 * @param form
	 *            The form definition that must be build
	 * @param extraObjects
	 *            A custom map of objects.
	 *            The keys allowed in this custom map is defined in the specific builder
	 * @return The form instance built
	 */
	public FormInstance<S> buildForm(Form form, Map<String, Object> extraObjects);

	/**
	 * Return the class that the FormInstances generated by this FormToolkit return as specific component
	 * 
	 * @return The class that the FormInstances generated by this FormToolkit return as specific component
	 */
	public Class<S> getImplementationClass();

}

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

import java.beans.PropertyChangeListener;

/**
 * Presenter are the objects than can read/write properties from the model and
 * expose this to the framework.
 * 
 * The presenter object can also receive events when the fields are changed, or
 * the model of form has been changed.
 * 
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 * @param <S>
 */

public interface BPresenter {

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

	public void fieldHasChanged(String fieldId);

	public Object getPropertyValue(String propertyName);

	public void setPropertyValue(String propertyName, Object value);

}

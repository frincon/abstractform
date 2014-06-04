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

package org.abstractform.swing.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 */
public abstract class AbstractSwingListenerAdapter implements SwingListenerAdapter {

	private List<PropertyChangeListener> listenerList = new CopyOnWriteArrayList<PropertyChangeListener>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.swing.internal.SwingListenerAdapter#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listenerList.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.swing.internal.SwingListenerAdapter#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listenerList.remove(listener);
	}

	public void firePropertyChange(Object source, Object oldValue, Object newValue) {
		PropertyChangeEvent evt = new PropertyChangeEvent(source, null, oldValue, newValue);
		for (PropertyChangeListener listener : listenerList) {
			listener.propertyChange(evt);
		}
	}
}

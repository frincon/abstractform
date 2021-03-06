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
package org.abstractform.core.selector;

import java.util.SortedSet;

// TODO Make lazy, more efficient, etc
/**
 * 
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 * @param <T>
 */
public interface SelectorProvider<T> {

	public SortedSet<T> getElements();

	public String getText(T element);

	public void addSelectorProviderListener(SelectorProviderListener listener);

	public void removeSelectorProviderListener(SelectorProviderListener listener);

}

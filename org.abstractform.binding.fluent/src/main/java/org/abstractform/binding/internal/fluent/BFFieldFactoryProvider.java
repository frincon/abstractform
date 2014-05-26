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
package org.abstractform.binding.internal.fluent;

import org.abstractform.binding.fluent.BFFieldFactory;
import org.abstractform.binding.fluent.DefaultBFFieldFactory;

public class BFFieldFactoryProvider {

	private final static BFFieldFactoryProvider INSTANCE = new BFFieldFactoryProvider();

	private BFFieldFactory bFFieldFactory;

	public static BFFieldFactoryProvider getInstance() {
		return INSTANCE;
	}

	private BFFieldFactoryProvider() {

	}

	public BFFieldFactory getBFFieldFactory() {
		if (bFFieldFactory == null) {
			bFFieldFactory = new DefaultBFFieldFactory();
		}
		return bFFieldFactory;
	}

	public void setBFFieldFactory(BFFieldFactory bFFieldFactory) {
		if (this.bFFieldFactory != null) {
			throw new UnsupportedOperationException("The field factory is established.");
		}
		this.bFFieldFactory = bFFieldFactory;
	}

}

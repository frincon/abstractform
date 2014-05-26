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
package org.abstractform.binding.internal.osgi;

import java.util.Hashtable;

import org.abstractform.binding.fluent.BFFieldFactory;
import org.abstractform.binding.fluent.DefaultBFFieldFactory;
import org.abstractform.binding.internal.fluent.BFFieldFactoryProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

	private ServiceTracker<BFFieldFactory, BFFieldFactory> fieldFactoryTracker;
	private ServiceRegistration<BFFieldFactory> defaultBFFieldFactoryRegistration;

	@Override
	public void start(BundleContext context) throws Exception {
		// Register the default implementation
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.put(Constants.SERVICE_RANKING, Integer.MIN_VALUE);
		defaultBFFieldFactoryRegistration = context.registerService(BFFieldFactory.class, new DefaultBFFieldFactory(), properties);

		fieldFactoryTracker = new ServiceTracker<BFFieldFactory, BFFieldFactory>(context, BFFieldFactory.class, null);
		fieldFactoryTracker.open();

		BFFieldFactoryProvider.getInstance().setBFFieldFactory(new OsgiBFFieldFactory(fieldFactoryTracker));
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		fieldFactoryTracker.close();
		defaultBFFieldFactoryRegistration.unregister();
	}

}

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
package org.abstractform.binding.eclipse;

import java.util.ArrayList;
import java.util.List;

import org.abstractform.binding.BBindingContext;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.ValidationStatusProvider;
import org.eclipse.core.runtime.IStatus;

public class EclipseBindingContext implements BBindingContext {

	private DataBindingContext ctx;

	protected EclipseBindingContext(DataBindingContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void updateModel() {
		ctx.updateModels();
	}

	@Override
	public List<String> getErrors() {
		List<String> errorList = new ArrayList<String>();
		for (ValidationStatusProvider prov : (List<ValidationStatusProvider>) ctx.getValidationStatusProviders()) {
			IStatus status = (IStatus) prov.getValidationStatus().getValue();
			if (!status.isOK()) {
				errorList.add(status.getMessage());
			}
		}
		return errorList;
	}

	@Override
	public void updateFields() {
		ctx.updateTargets();
	}

}

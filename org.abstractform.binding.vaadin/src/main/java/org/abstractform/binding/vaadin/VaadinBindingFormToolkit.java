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
package org.abstractform.binding.vaadin;

import java.util.Collections;
import java.util.Map;

import javax.management.ServiceNotFoundException;

import org.abstractform.binding.BBindingService;
import org.abstractform.binding.BBindingToolkit;
import org.abstractform.binding.BForm;
import org.abstractform.binding.BFormToolkit;
import org.abstractform.binding.vaadin.internal.VaadinBindingFormInstanceImpl;
import org.abstractform.core.Field;
import org.abstractform.core.Form;
import org.abstractform.core.FormService;
import org.abstractform.core.FormToolkit;
import org.abstractform.core.selector.SelectorConstants;
import org.abstractform.core.selector.SelectorProviderFactory;
import org.abstractform.vaadin.VaadinDataObject;
import org.abstractform.vaadin.VaadinFormInstance;
import org.abstractform.vaadin.VaadinSelectorContainer;

import com.vaadin.data.Container;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("rawtypes")
public class VaadinBindingFormToolkit implements BFormToolkit<VaadinBindingFormInstance> {

	private FormToolkit<VaadinFormInstance> delegateToolKit;

	public VaadinBindingFormToolkit() throws ServiceNotFoundException {
		delegateToolKit = FormService.getInstance().getFormToolkit(VaadinFormInstance.class);
	}

	public VaadinBindingFormToolkit(FormToolkit<VaadinFormInstance> delegateToolKit) {
		this.delegateToolKit = delegateToolKit;
	}

	@Override
	public VaadinBindingFormInstance buildForm(Form form, Map<String, Object> extraObjects) {
		VaadinFormInstance delegate = delegateToolKit.buildForm(form, extraObjects);
		ComponentContainer c = addValidationSummaryField(delegate.getImplementation(), false);
		VaadinBindingFormInstance<?> formInstance = new VaadinBindingFormInstanceImpl<Object>(c, (ComponentContainer) c
				.getComponentIterator().next(), delegate);
		replaceFormInstanceReferences(delegate, formInstance);
		return formInstance;
	}

	private void replaceFormInstanceReferences(VaadinFormInstance delegate, VaadinBindingFormInstance<?> real) {
		for (String fieldId : delegate.getFieldIds()) {
			AbstractComponent component = delegate.getComponentById(fieldId);
			if (component.getData() instanceof VaadinDataObject) {
				VaadinDataObject dataObject = (VaadinDataObject) component.getData();
				Field field = dataObject.getField();
				if (field.getType().equals(SelectorConstants.TYPE_SELECTOR)) {
					if (component instanceof Container.Viewer) {
						final Container.Viewer viewer = (Container.Viewer) component;
						SelectorProviderFactory factory = (SelectorProviderFactory) field
								.getExtra(SelectorConstants.EXTRA_SELECTOR_PROVIDER_FACTORY);
						final VaadinSelectorContainer container = new VaadinSelectorContainer(factory.createSelectorProvider(real));

						if (viewer instanceof AbstractSelect) {
							final AbstractSelect abstractSelect = (AbstractSelect) viewer;
							container.addListener(new Container.ItemSetChangeListener() {

								@Override
								public void containerItemSetChange(ItemSetChangeEvent event) {
									boolean readOnly = abstractSelect.isReadOnly();
									abstractSelect.setReadOnly(false);
									abstractSelect.sanitizeSelection();
									abstractSelect.setReadOnly(readOnly);
								}

							});
							viewer.setContainerDataSource(container);
						}
					}
				}
			}
		}
	}

	@Override
	public VaadinBindingFormInstance<?> buildForm(Form form) {
		return buildForm(form, Collections.<String, Object> emptyMap());
	}

	@Override
	public <S> VaadinBindingFormInstance<S> buildForm(BForm<S> form, BBindingToolkit bindingToolkit,
			Map<String, Object> extraObjects) {
		return buildForm(form, bindingToolkit, extraObjects, true);
	}

	@Override
	public Class<VaadinBindingFormInstance> getFormInstanceClass() {
		return VaadinBindingFormInstance.class;
	}

	@Override
	public <S> VaadinBindingFormInstance<S> buildForm(BForm<S> form) {
		try {
			return buildForm(form, BBindingService.getInstance().getBindingToolkit());
		} catch (ServiceNotFoundException e) {
			throw new UnsupportedOperationException("Default binding toolkit not found", e);
		}
	}

	private ComponentContainer addValidationSummaryField(Component oldMain, boolean validationSummaryVisible) {
		// add error sumary layout
		VerticalLayout main = new VerticalLayout();
		VerticalLayout summary = new VerticalLayout();
		summary.setVisible(validationSummaryVisible);
		main.addComponent(summary);
		main.addComponent(oldMain);
		return main;
	}

	@Override
	public <S> VaadinBindingFormInstance<S> buildForm(BForm<S> form, BBindingToolkit bindingToolkit) {
		return buildForm(form, bindingToolkit, Collections.<String, Object> emptyMap());
	}

	@Override
	public <S> VaadinBindingFormInstance<S> buildForm(BForm<S> form, BBindingToolkit bindingToolkit,
			Map<String, Object> extraObjects, boolean immediate) {
		VaadinFormInstance delegate = delegateToolKit.buildForm(form, extraObjects);
		ComponentContainer c = addValidationSummaryField(delegate.getImplementation(), form.isValidationSummaryVisible());
		VaadinBindingFormInstanceImpl<S> instance = new VaadinBindingFormInstanceImpl<S>(c, (ComponentContainer) c
				.getComponentIterator().next(), delegate);
		bindingToolkit.bindFields(instance, form);
		replaceFormInstanceReferences(delegate, instance);
		return instance;
	}

}

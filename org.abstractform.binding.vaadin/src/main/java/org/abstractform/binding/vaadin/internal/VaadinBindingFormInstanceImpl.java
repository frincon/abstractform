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
package org.abstractform.binding.vaadin.internal;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.abstractform.binding.BindingContext;
import org.abstractform.binding.vaadin.VaadinBindingFormInstance;
import org.abstractform.binding.validation.ValidationError;
import org.abstractform.vaadin.VaadinDataObject;
import org.abstractform.vaadin.VaadinFormInstance;

import com.vaadin.terminal.CompositeErrorMessage;
import com.vaadin.terminal.ErrorMessage;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.BaseTheme;

public class VaadinBindingFormInstanceImpl<S> implements VaadinBindingFormInstance<S> {

	private final static String PROPERTY_VALUE = "value";

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private S value;
	private VaadinFormInstance delegate;
	private BindingContext context;

	private ComponentContainer mainComponent;
	private ComponentContainer validationSummaryComponent;

	private List<ValidationError> validationErrosSummaryList = new ArrayList<ValidationError>();

	public VaadinBindingFormInstanceImpl(ComponentContainer mainComopnent, ComponentContainer validationSummaryComponent,
			VaadinFormInstance delegate) {
		this.delegate = delegate;
		this.mainComponent = mainComopnent;
		this.validationSummaryComponent = validationSummaryComponent;
	}

	@Override
	public void setValue(S value) {
		S old = this.value;
		this.value = value;
		pcs.firePropertyChange(PROPERTY_VALUE, old, value);
		this.validationErrosSummaryList.clear();
		refreshValidationSummary();
	}

	@Override
	public S getValue() {
		return value;
	}

	@Override
	public void addValuePropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(PROPERTY_VALUE, listener);
	}

	@Override
	public void removeValuePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(PROPERTY_VALUE, listener);
	}

	@Override
	public Component getImplementation() {
		return mainComponent;
	}

	@Override
	public AbstractComponent getComponentById(String id) {
		return delegate.getComponentById(id);
	}

	@Override
	public void setBindingContext(BindingContext context) {
		this.context = context;
	}

	@Override
	public BindingContext getBindingContext() {
		return context;
	}

	@Override
	public Object getFieldValue(String fieldId) {
		AbstractComponent component = (AbstractComponent) getComponentById(fieldId);
		VaadinDataObject dataObject = (VaadinDataObject) component.getData();
		return dataObject.getAccessor().getFieldValue(this, component);
	}

	@Override
	public void setFieldValue(String fieldId, Object value) {
		AbstractComponent component = (AbstractComponent) getComponentById(fieldId);
		VaadinDataObject dataObject = (VaadinDataObject) component.getData();
		dataObject.getAccessor().setFieldValue(this, component, value);
	}

	@Override
	public boolean isFieldReadOnly(String fieldId) {
		Component c = getComponentById(fieldId);
		return c.isReadOnly();
	}

	@Override
	public void setFieldReadOnly(String fieldId, boolean readOnly) {
		Component c = getComponentById(fieldId);
		c.setReadOnly(readOnly);
	}

	@Override
	public void setError(String fieldId, List<String> errors) {
		Component c = getComponentById(fieldId);
		List<String> realErrors = removeNullValues(errors);
		if (c instanceof AbstractComponent) {
			AbstractComponent co = (AbstractComponent) c;
			if (errors == null || errors.size() == 0) {
				co.setComponentError(null);
			} else if (realErrors.size() == 1) {
				co.setComponentError(new UserError(realErrors.get(0)));
			} else {
				List<ErrorMessage> errorList = new ArrayList<ErrorMessage>(realErrors.size());
				for (String error : realErrors) {
					errorList.add(new UserError(error));
				}
				co.setComponentError(new CompositeErrorMessage(errorList));
			}
		} else {
			// TODO Warning? Exception?
			assert false;
		}
	}

	private List<String> removeNullValues(List<String> listToRemove) {
		if (listToRemove == null) {
			return null;
		}
		List<String> result = new ArrayList<String>(listToRemove);
		result.removeAll(Collections.singleton(null));
		return Collections.unmodifiableList(result);
	}

	@Override
	public void updateModel() {
		context.updateModel();
	}

	@Override
	public List<String> getErrors() {
		return context.getErrors();
	}

	@Override
	public void setValidationErrorSummary(List<ValidationError> errorList) {
		this.validationErrosSummaryList = errorList;
		refreshValidationSummary();
	}

	@Override
	public List<ValidationError> getValidationErrorSummary() {
		return this.validationErrosSummaryList;
	}

	@Override
	public void refreshValidationSummary() {
		validationSummaryComponent.removeAllComponents();
		for (ValidationError error : validationErrosSummaryList) {
			if (error != null) {
				Component errorComponent = null;
				if (error.getFieldId() != null) {
					errorComponent = getComponentById(error.getFieldId());
				}
				if (errorComponent != null) {
					HorizontalLayout layout = new HorizontalLayout();
					if (errorComponent instanceof AbstractField) {
						final AbstractField component = (AbstractField) errorComponent;
						Button but = new Button(errorComponent.getCaption());
						but.setStyleName(BaseTheme.BUTTON_LINK);

						but.addListener(new Button.ClickListener() {

							private static final long serialVersionUID = -635674369175495232L;

							@Override
							public void buttonClick(ClickEvent event) {
								component.focus();
								if (component instanceof AbstractField) {
									AbstractTextField field = (AbstractTextField) component;
									field.selectAll();
								}
							}
						});
						layout.addComponent(but);
					} else {
						layout.addComponent(new Label(errorComponent.getCaption()));
					}
					layout.addComponent(new Label(" : " + error.getMessage()));
					validationSummaryComponent.addComponent(layout);

				} else {
					validationSummaryComponent.addComponent(new Label(error.getMessage()));
				}
			}
		}

	}

	@Override
	public List<String> getFieldIds() {
		return delegate.getFieldIds();
	}

	@Override
	public void addFieldChangeListener(String fieldId, PropertyChangeListener listener) {
		delegate.addFieldChangeListener(fieldId, listener);
	}

	@Override
	public void removeFieldChangeListener(String fieldId, PropertyChangeListener listener) {
		delegate.removeFieldChangeListener(fieldId, listener);
	}

	@Override
	public void addFieldChangeListener(PropertyChangeListener listener) {
		delegate.addFieldChangeListener(listener);
	}

	@Override
	public void removeFieldChangeListener(PropertyChangeListener listener) {
		delegate.removeFieldChangeListener(listener);
	}

}

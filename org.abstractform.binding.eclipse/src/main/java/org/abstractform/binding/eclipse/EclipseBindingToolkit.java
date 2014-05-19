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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.abstractform.binding.BBindingToolkit;
import org.abstractform.binding.BField;
import org.abstractform.binding.BForm;
import org.abstractform.binding.BFormInstance;
import org.abstractform.binding.BPresenter;
import org.abstractform.binding.eclipse.validation.ValidatorSTub;
import org.abstractform.binding.validation.ValidationError;
import org.abstractform.core.Component;
import org.abstractform.core.Container;
import org.abstractform.core.Field;
import org.abstractform.core.SubForm;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.BindingProperties;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.internal.databinding.property.value.SimplePropertyObservableValue;
import org.eclipse.core.runtime.IStatus;

public class EclipseBindingToolkit implements BBindingToolkit {

	@Override
	public <S> void bindFields(BFormInstance<S> formInstance, BForm<S> form) {
		bindFields(formInstance, form, true);
	}

	@Override
	public <S> void bindFields(BFormInstance<S> formInstance, BForm<S> form, boolean immediate) {
		Object context = formInstance.getBindingContext();
		if (context != null) {
			throw new UnsupportedOperationException("Binding context has been already set");
		}

		DataBindingContext dbCtx = createDataBindingContext(formInstance);

		//WritableValue formValue = new WritableValue(formInstance.getValue(), form.getBeanClass());
		WritableValue formValue = new WritableValue(formInstance.getValue(), Object.class);

		final BPresenter<S> presenter = form.createPresenter(formInstance, formInstance.getValue());

		bindFormValue(dbCtx, formValue, form, formInstance);

		recursiveAddBindings(dbCtx, formValue, formInstance, form.getBeanClass(), form, presenter, immediate);

		bindPresenter(dbCtx, presenter, formInstance);
		final Binding bindingSummary = bindValidationSummaryError(dbCtx, formInstance);

		formInstance.setBindingContext(new EclipseBindingContext(dbCtx));
	}

	protected <S> void bindPresenter(DataBindingContext dbCtx, final BPresenter<S> presenter, BFormInstance<S> formInstance) {
		final PropertyChangeListener listener = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				presenter.modelHasChanged(evt.getPropertyName(), (S) evt.getSource());
			}
		};
		if (formInstance.getValue() != null) {
			callAddPropertyChangeMethod(formInstance.getValue(), listener);
		}
		formInstance.addValuePropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				S model = (S) evt.getOldValue();
				callRemovePropertyChangeMethod(model, listener);
				presenter.setModel((S) evt.getNewValue());
				model = (S) evt.getNewValue();
				callAddPropertyChangeMethod(model, listener);
			}
		});
	}

	protected void callAddPropertyChangeMethod(Object value, PropertyChangeListener listener) {
		try {
			Method method = value.getClass().getMethod("addPropertyChangeListener", PropertyChangeListener.class);
			method.invoke(value, listener);
		} catch (Exception e) {
			// TODO
		}
	}

	protected void callRemovePropertyChangeMethod(Object value, PropertyChangeListener listener) {
		try {
			Method method = value.getClass().getMethod("removePropertyChangeListener", PropertyChangeListener.class);
			method.invoke(value, listener);
		} catch (Exception e) {
			// TODO
		}
	}

	private <S> void bindFormValue(DataBindingContext dbCtx, IObservableValue formValue, BForm<S> form,
			BFormInstance<S> formInstance) {
		IObservableValue target = formValue;
		IObservableValue model = AbstractFormProperties.value(form).observe(formInstance);
		UpdateValueStrategy modelToTarget = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
		UpdateValueStrategy targetToModel = new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER);
		dbCtx.bindValue(target, model, targetToModel, modelToTarget);
	}

	public <S> void recursiveAddBindings(DataBindingContext dbCtx, IObservableValue formValue, final BFormInstance<S> formInstance,
			Class<S> beanClass, Component component, final BPresenter<S> presenter, boolean immediate) {
		if (component instanceof SubForm) {
			SubForm subForm = (SubForm) component;
			for (int row = 0; row < subForm.getRows(); row++) {
				for (int col = 0; col < subForm.getColumns(); col++) {
					Component compSubForm = subForm.getField(row, col);
					if (compSubForm instanceof Field) {
						final Field field = (Field) compSubForm;

						if (field instanceof BField) {
							Binding binding = bindField(dbCtx, (BField) field, formValue, formInstance, beanClass, immediate,
									presenter);
							bindErrorMessage(dbCtx, binding.getValidationStatus(), (BField) field, formInstance);

							IObservable observableField = binding.getTarget();

							// Listen for all changes in all fields and notify to presenter
							// Better implementation can be made
							// TODO Use one listener for all fields
							observableField.addChangeListener(new IChangeListener() {

								@Override
								public void handleChange(ChangeEvent event) {
									if (event.getSource() instanceof SimplePropertyObservableValue) {
										SimplePropertyObservableValue value = (SimplePropertyObservableValue) event.getSource();
										String fieldId = ((FieldValueProperty) value.getProperty()).getFieldId();
										callPresenterFieldChanged(presenter, fieldId, formInstance);
									} else {
										String fieldId = field.getId();
										callPresenterFieldChanged(presenter, fieldId, formInstance);
									}
								}
							});

							//Bind read only property

							if (((BField) field).getReadOnlyPresenterProperty() != null) {
								bindReadOnlyProperty(dbCtx, ((BField) field), formInstance, presenter);
							}
						}

					} else {
						recursiveAddBindings(dbCtx, formValue, formInstance, beanClass, compSubForm, presenter, immediate);
					}
				}
			}
		} else if (component instanceof Container) {
			Container container = (Container) component;
			for (Component child : container.getChildList()) {
				recursiveAddBindings(dbCtx, formValue, formInstance, beanClass, child, presenter, immediate);
			}
		}
	}

	protected <S> void callPresenterFieldChanged(BPresenter<S> presenter, String fieldId, BFormInstance<S> formInstance) {
		presenter.fieldHasChanged(fieldId, formInstance.getValue());
	}

	private Binding bindValidationSummaryError(final DataBindingContext dbCtx, final BFormInstance<?> formInstance) {
		IObservableList target = BindingProperties.validationStatus().observeDetail(dbCtx.getValidationStatusProviders());
		IObservableList model = AbstractFormProperties.validationStatusSummary().observe(formInstance);
		UpdateListStrategy targetToModel = new UpdateListStrategy(false, UpdateListStrategy.POLICY_ON_REQUEST);
		UpdateListStrategy modelToTarget = new UpdateListStrategy(false, UpdateListStrategy.POLICY_NEVER);

		targetToModel.setConverter(new IConverter() {

			@Override
			public Object getToType() {
				return null;
			}

			@Override
			public Object getFromType() {
				return null;
			}

			@Override
			public Object convert(Object fromObject) {
				IStatus status = (IStatus) fromObject;
				if (!status.isOK()) {
					String fieldId = getFieldIdByStatus(dbCtx, status);
					return new ValidationError(fieldId, status.getMessage());
				}
				return null;
			}

			private String getFieldIdByStatus(DataBindingContext ctx, IStatus status) {
				List<Binding> list = Collections.checkedList(ctx.getBindings(), Binding.class);
				for (Binding binding : list) {
					IStatus bindingStatus = (IStatus) binding.getValidationStatus().getValue();
					if (status == bindingStatus) {
						try {
							return ((FieldValueProperty) ((SimplePropertyObservableValue) binding.getTarget()).getProperty())
									.getFieldId();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
				return null;
			}

		});

		model.addListChangeListener(new IListChangeListener() {

			@Override
			public void handleListChange(ListChangeEvent event) {
				formInstance.refreshValidationSummary();
			}
		});

		return dbCtx.bindList(target, model, targetToModel, modelToTarget);

	}

	private <S> Binding bindReadOnlyProperty(DataBindingContext dbc, BField field, BFormInstance<S> formInstance,
			BPresenter<S> presenter) {
		IObservableValue model = BeanProperties.value(presenter.getClass(), field.getReadOnlyPresenterProperty())
				.observe(presenter);
		IObservableValue target = AbstractFormProperties.readOnlyField(field.getId()).observe(formInstance);
		UpdateValueStrategy modelToTarget = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
		UpdateValueStrategy targetToModel = new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER);
		return dbc.bindValue(target, model, targetToModel, modelToTarget);

	}

	protected <S> Binding bindField(DataBindingContext dbCtx, BField field, IObservableValue master, BFormInstance<S> formInstance,
			Class<S> beanClass, boolean immediate, BPresenter<S> presenter) {
		IObservableValue model = getObservableValue(master, field, beanClass);
		IObservableValue target = AbstractFormProperties.field(field.getId()).observe(formInstance);
		UpdateValueStrategy modelToTarget = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
		UpdateValueStrategy targetToModel = new UpdateValueStrategy(immediate ? UpdateValueStrategy.POLICY_UPDATE
				: UpdateValueStrategy.POLICY_CONVERT);
		if (field.isRequired()) {
			targetToModel.setAfterConvertValidator(RequiredValidator.INSTANCE);
		}
		if (field.getValidator() != null) {
			targetToModel.setBeforeSetValidator(new ValidatorSTub(field.getValidator()));
		}

		Binding binding = dbCtx.bindValue(target, model, targetToModel, modelToTarget);

		return binding;
	}

	protected IObservableValue getObservableValue(IObservableValue master, BField field, Class<?> beanClass) {
		return BeanProperties.value(beanClass, field.getPropertyName()).observeDetail(master);
	}

	protected void bindErrorMessage(DataBindingContext dbCtx, IObservableValue validationStatus, BField field,
			BFormInstance<?> formInstance) {
		IObservableValue model = validationStatus;
		IObservableValue target = AbstractFormProperties.errorComponent(field.getId()).observe(formInstance);
		UpdateValueStrategy modelToTarget = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
		modelToTarget.setConverter(new IConverter() {

			@Override
			public Object getToType() {
				return List.class;
			}

			@Override
			public Object getFromType() {
				return IStatus.class;
			}

			@Override
			public Object convert(Object fromObject) {
				IStatus status = (IStatus) fromObject;

				if (status.isOK()) {
					return null;
				} else {
					// TODO control MultiStatus
					List<String> list = new ArrayList<String>();
					recursiveAddErrors(list, status);
					return Collections.unmodifiableList(list);
				}
			}

			private void recursiveAddErrors(List<String> errorList, IStatus status) {
				if (status.isMultiStatus()) {
					IStatus[] children = status.getChildren();
					for (int i = 0; i < children.length; i++) {
						recursiveAddErrors(errorList, children[i]);
					}
				} else {
					//Filter Required validation status
					if (!(status instanceof RequiredValidationStatus) && !status.isOK()) {
						errorList.add(status.getMessage());
					}
				}
			}
		});
		UpdateValueStrategy targetToModel = new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER);
		dbCtx.bindValue(target, model, targetToModel, modelToTarget);
	}

	private DataBindingContext createDataBindingContext(BFormInstance<?> formInstance) {
		Realm realm = new DefaultRealm();
		return new DataBindingContext(realm);
	}

}

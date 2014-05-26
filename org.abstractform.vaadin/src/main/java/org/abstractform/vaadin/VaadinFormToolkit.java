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
package org.abstractform.vaadin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abstractform.core.Component;
import org.abstractform.core.Drawer;
import org.abstractform.core.Field;
import org.abstractform.core.Form;
import org.abstractform.core.FormToolkit;
import org.abstractform.core.Section;
import org.abstractform.core.SubForm;
import org.abstractform.core.Tab;
import org.abstractform.core.TabSheet;
import org.abstractform.core.selector.SelectorConstants;
import org.abstractform.core.selector.SelectorProviderFactory;
import org.abstractform.core.table.TableConstants;
import org.abstractform.vaadin.internal.VaadinFormInstanceImpl;

import com.vaadin.data.Container;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class VaadinFormToolkit implements FormToolkit<com.vaadin.ui.Component, VaadinFormInstance> {

	@Override
	public VaadinFormInstance buildForm(Form form, Map<String, Object> extraObjects) {
		VerticalLayout layout = new VerticalLayout();
		Map<String, AbstractComponent> mapComponents = new HashMap<String, AbstractComponent>();
		List<String> fieldIdList = new ArrayList<String>();
		layout.setSpacing(true);
		layout.setSizeFull();
		for (Component part : form.getChildList()) {
			ComponentContainer container = buildComponent(part, mapComponents, fieldIdList, extraObjects);
			if (container != null) {
				layout.addComponent(container);
			}
		}
		addComponent(mapComponents, form, layout);
		VaadinFormInstance formInstance = new VaadinFormInstanceImpl(layout, Collections.unmodifiableMap(mapComponents),
				Collections.unmodifiableList(fieldIdList));

		//build SelectorProviders
		buildSelectorProviders(mapComponents, formInstance);
		return formInstance;
	}

	protected void buildSelectorProviders(Map<String, AbstractComponent> mapComponents, VaadinFormInstance formInstance) {
		for (AbstractComponent component : mapComponents.values()) {
			if (component.getData() instanceof VaadinDataObject) {
				VaadinDataObject dataObject = (VaadinDataObject) component.getData();
				Field field = dataObject.getField();
				if (field.getType().equals(SelectorConstants.TYPE_SELECTOR)) {
					if (component instanceof Container.Viewer) {
						Container.Viewer viewer = (Container.Viewer) component;
						SelectorProviderFactory factory = (SelectorProviderFactory) field
								.getExtra(SelectorConstants.EXTRA_SELECTOR_PROVIDER_FACTORY);
						viewer.setContainerDataSource(new VaadinSelectorContainer(factory.createSelectorProvider(formInstance)));
					}
				}
			}
		}

	}

	@Override
	public VaadinFormInstance buildForm(Form form) {
		return buildForm(form, Collections.<String, Object> emptyMap());
	}

	private void addComponent(Map<String, AbstractComponent> mapComponents, Component component, AbstractComponent vaadinComponent) {
		if (mapComponents.containsKey(component.getId())) {
			throw new UnsupportedOperationException("Id has been repeated: " + component.getId());
		}
		mapComponents.put(component.getId(), vaadinComponent);
	}

	private ComponentContainer buildComponent(Component component, Map<String, AbstractComponent> mapComponents,
			List<String> fieldIdList, Map<String, Object> extraObjects) {
		AbstractComponentContainer container;
		if (component instanceof Drawer) {
			container = buildDrawer((Drawer) component, mapComponents, fieldIdList, extraObjects);
		} else if (component instanceof Section) {
			container = buildSection((Section) component, mapComponents, fieldIdList, extraObjects);
		} else if (component instanceof SubForm) {
			container = buildSubForm((SubForm) component, mapComponents, fieldIdList, extraObjects);
		} else if (component instanceof TabSheet) {
			container = buildTabSheet((TabSheet) component, mapComponents, fieldIdList, extraObjects);
		} else {
			throw new UnsupportedOperationException();
		}
		addComponent(mapComponents, component, container);
		return container;
	}

	private AbstractComponentContainer buildDrawer(final Drawer drawer, Map<String, AbstractComponent> mapComponents,
			List<String> fieldIdList, Map<String, Object> extraObjects) {
		VerticalLayout layout = new VerticalLayout();
		final Button button = new Button();
		// button.setStyleName(BaseTheme.BUTTON_LINK);
		button.setCaption("\u25B6" + drawer.getName());
		final VerticalLayout innerLayout = new VerticalLayout();
		for (Component component : drawer.getChildList()) {
			ComponentContainer container = buildComponent(component, mapComponents, fieldIdList, extraObjects);
			innerLayout.addComponent(container);
		}
		button.addListener(new Button.ClickListener() {

			private static final long serialVersionUID = 4970466538378502562L;

			@Override
			public void buttonClick(ClickEvent event) {
				innerLayout.setVisible(!innerLayout.isVisible());
				if (innerLayout.isVisible()) {
					button.setCaption("\u25BC" + drawer.getName());
				} else {
					button.setCaption("\u25B6" + drawer.getName());

				}
			}
		});
		layout.addComponent(button);
		layout.addComponent(innerLayout);
		innerLayout.setVisible(false);
		return layout;
	}

	private AbstractComponentContainer buildSection(Section part, Map<String, AbstractComponent> mapComponents,
			List<String> fieldIdList, Map<String, Object> extraObjects) {
		Panel panel = new Panel();
		panel.setCaption(part.getName());
		for (Component component : part.getChildList()) {
			ComponentContainer container = buildComponent(component, mapComponents, fieldIdList, extraObjects);
			panel.addComponent(container);
		}
		return panel;
	}

	private AbstractComponentContainer buildTabSheet(TabSheet part, Map<String, AbstractComponent> mapComponents,
			List<String> fieldIdList, Map<String, Object> extraObjects) {
		com.vaadin.ui.TabSheet tabSheet = new com.vaadin.ui.TabSheet();
		for (Component component : part.getChildList()) {
			if (component instanceof Tab) {
				Tab tab = (Tab) component;
				VerticalLayout layout = new VerticalLayout();
				layout.setMargin(true);
				for (Component child : tab.getChildList()) {
					layout.addComponent(buildComponent(child, mapComponents, fieldIdList, extraObjects));
				}
				tabSheet.addTab(layout, tab.getName());
			} else {
				throw new UnsupportedOperationException();
			}
		}
		tabSheet.setSizeFull();
		return tabSheet;
	}

	private AbstractComponentContainer buildSubForm(SubForm subForm, Map<String, AbstractComponent> mapComponents,
			List<String> fieldIdList, Map<String, Object> extraObjects) {
		//panel.setCaption(formEditor.getName());
		GridLayout layout = new GridLayout(subForm.getColumns(), subForm.getRows());
		layout.setWidth("100%");
		layout.setSpacing(true);
		for (int row = 0; row < subForm.getRows(); row++) {
			for (int column = 0; column < subForm.getColumns(); column++) {
				Component component = subForm.getField(row, column);
				if (component == null) {
					layout.addComponent(new Label("&nbsp;", Label.CONTENT_XHTML));
				} else if (component instanceof Field) {
					Field editor = (Field) component;
					if (editor != null) {
						layout.addComponent(buildField(editor, mapComponents, fieldIdList, extraObjects));
					}
				} else {
					buildComponent(component, mapComponents, fieldIdList, extraObjects);
				}
			}
		}
		return layout;
	}

	protected final com.vaadin.ui.Component buildField(Field field, Map<String, AbstractComponent> mapComponents,
			List<String> fieldIdList, Map<String, Object> extraObjects) {
		fieldIdList.add(field.getId());
		AbstractComponent component = internalBuildField(field, extraObjects);
		VaadinFieldValueAccessor accessor = getValueAccessor(field, component);
		component.setData(new VaadinDataObject(field, accessor));
		addComponent(mapComponents, field, component);
		return component;
	}

	protected VaadinFieldValueAccessor getValueAccessor(Field field, AbstractComponent component) {
		if (field.getType().equals(TableConstants.TYPE_TABLE)) {
			return new TableFieldValueAccessor();
		} else if (field.getType().equals(Field.TYPE_NUMERIC)) {
			return new NumericPropertyFieldValueAccessor((Class<? extends Number>) field.getExtra(Field.EXTRA_NUMBER_CLASS));
		} else {
			return new PropertyFieldValueAccessor();
		}
	}

	// TODO Make this generic with loader or register mechanism to plug into here
	protected AbstractComponent internalBuildField(Field field, Map<String, Object> extraObjects) {
		AbstractComponent ret = null;
		if (field.getType().equals(Field.TYPE_BOOL)) {
			CheckBox cb = new CheckBox(field.getName());
			cb.setImmediate(true);
			ret = cb;
		} else if (field.getType().equals(Field.TYPE_DATETIME)) {
			DateField dt = new DateField(field.getName());
			dt.setResolution(DateField.RESOLUTION_DAY);
			dt.setDescription(field.getDescription());
			dt.setRequired(field.isRequired());
			dt.setImmediate(true);
			dt.setReadOnly(field.isReadOnly());
			ret = dt;
		} else if (field.getType().equals(Field.TYPE_TEXT) || field.getType().equals(Field.TYPE_NUMERIC)
				|| field.getType().equals(Field.TYPE_PASSWORD)) {
			AbstractTextField textField;
			if (field.getType().equals(Field.TYPE_PASSWORD)) {
				textField = new PasswordField(field.getName());
			} else {
				textField = new TextField(field.getName());
			}
			//textField.setColumns(field.getDisplayWidth());
			if (field.getMaxLength() != 0) {
				textField.setMaxLength(field.getMaxLength());
			}
			textField.setDescription(field.getDescription());
			textField.setReadOnly(field.isReadOnly());
			textField.setNullRepresentation("".intern());
			textField.setNullSettingAllowed(true);
			textField.setRequired(field.isRequired());
			textField.setImmediate(true);
			textField.setWidth("100%");
			ret = textField;
		} else if (field.getType().equals(SelectorConstants.TYPE_SELECTOR)) {
			ComboBox comboBox = new ComboBox(field.getName());
			comboBox.setTextInputAllowed(false);
			comboBox.setNullSelectionAllowed(!field.isRequired());
			comboBox.setDescription(field.getDescription());
			comboBox.setReadOnly(field.isReadOnly());
			comboBox.setRequired(field.isRequired());
			comboBox.setImmediate(true);
			comboBox.setItemCaptionMode(ComboBox.ITEM_CAPTION_MODE_PROPERTY);
			comboBox.setItemCaptionPropertyId(VaadinSelectorContainer.PROPERTY_CAPTION);
			comboBox.setWidth("100%");
			ret = comboBox;
		} else if (field.getType().equals(TableConstants.TYPE_TABLE)) {
			ret = buildTableField(field, extraObjects);
		}
		return ret;
	}

	@Override
	public Class<VaadinFormInstance> getFormInstanceClass() {
		return VaadinFormInstance.class;
	}

	protected AbstractComponent buildTableField(Field field, Map<String, Object> extraObjects) {
		Table table = new Table();
		table.setRowHeaderMode(Table.ROW_HEADER_MODE_INDEX);
		List<Field> fieldList = (List<Field>) field.getExtra(TableConstants.EXTRA_TABLE_FIELD_LIST);
		for (Field tableField : fieldList) {
			table.addContainerProperty(tableField.getId(), String.class, "", tableField.getName(), null, Table.ALIGN_LEFT);
		}
		table.setSizeFull();
		table.setSelectable(!field.isReadOnly());
		table.setMultiSelect(!field.isReadOnly());
		table.setPageLength((Integer) field.getExtra(TableConstants.EXTRA_TABLE_PAGE_LENGHT));
		return table;

	}

}

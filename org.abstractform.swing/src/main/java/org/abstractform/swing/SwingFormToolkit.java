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

package org.abstractform.swing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.sourceforge.jdatepicker.DateModel;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

import org.abstractform.core.Component;
import org.abstractform.core.Drawer;
import org.abstractform.core.Field;
import org.abstractform.core.Form;
import org.abstractform.core.FormInstance;
import org.abstractform.core.FormToolkit;
import org.abstractform.core.Section;
import org.abstractform.core.SubForm;
import org.abstractform.core.Tab;
import org.abstractform.core.TabSheet;
import org.abstractform.core.selector.SelectorConstants;
import org.abstractform.core.table.TableConstants;
import org.abstractform.swing.internal.ChangeListenerAdapter;
import org.abstractform.swing.internal.DocumentListenerAdapter;
import org.abstractform.swing.internal.ItemListenerAdapter;
import org.abstractform.swing.internal.SwingAccessor;
import org.abstractform.swing.internal.SwingFormInstanceImpl;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;

/**
 * @author Fernando Rincon Martin <frm.rincon@gmail.com>
 * 
 */
public class SwingFormToolkit implements FormToolkit<JComponent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.core.FormToolkit#buildForm(org.abstractform.core.Form)
	 */
	@Override
	public FormInstance<JComponent> buildForm(Form form) {
		return buildForm(form, Collections.<String, Object> emptyMap());
	}

	private void addComponent(Map<String, JComponent> mapComponents, Component component, JComponent swingComponent) {
		if (mapComponents.containsKey(component.getId())) {
			throw new UnsupportedOperationException("Id has been repeated: " + component.getId());
		}
		mapComponents.put(component.getId(), swingComponent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.core.FormToolkit#buildForm(org.abstractform.core.Form, java.util.Map)
	 */
	@Override
	public FormInstance<JComponent> buildForm(Form form, Map<String, Object> extraObjects) {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		Map<String, JComponent> mapComponents = new HashMap<String, JComponent>();
		List<String> fieldIdList = new ArrayList<String>();
		for (Component part : form.getChildList()) {
			JComponent container = buildComponent(part, mapComponents, fieldIdList, extraObjects);
			if (container != null) {
				jPanel.add(container);
			}
		}
		addComponent(mapComponents, form, jPanel);
		SwingFormInstance formInstance = new SwingFormInstanceImpl(jPanel, mapComponents, fieldIdList);

		//build SelectorProviders
		//buildSelectorProviders(mapComponents, formInstance);
		return formInstance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.abstractform.core.FormToolkit#getImplementationClass()
	 */
	@Override
	public Class<JComponent> getImplementationClass() {
		return JComponent.class;
	}

	private JComponent buildComponent(Component component, Map<String, JComponent> mapComponents, List<String> fieldIdList,
			Map<String, Object> extraObjects) {
		JComponent container;
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

	private JComponent buildDrawer(Drawer part, Map<String, JComponent> mapComponents, List<String> fieldIdList,
			Map<String, Object> extraObjects) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setName(part.getName());
		for (Component component : part.getChildList()) {
			JComponent container = buildComponent(component, mapComponents, fieldIdList, extraObjects);
			panel.add(container);
		}
		return panel;
	}

	private JComponent buildSection(Section part, Map<String, JComponent> mapComponents, List<String> fieldIdList,
			Map<String, Object> extraObjects) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setName(part.getName());
		for (Component component : part.getChildList()) {
			JComponent container = buildComponent(component, mapComponents, fieldIdList, extraObjects);
			panel.add(container);
		}
		return panel;
	}

	private JComponent buildTabSheet(TabSheet part, Map<String, JComponent> mapComponents, List<String> fieldIdList,
			Map<String, Object> extraObjects) {
		JTabbedPane tabbedPane = new JTabbedPane();
		for (Component component : part.getChildList()) {
			if (component instanceof Tab) {
				Tab tab = (Tab) component;
				JPanel panel = new JPanel();
				panel.setBorder(BorderFactory.createEmptyBorder());
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				for (Component child : tab.getChildList()) {
					panel.add(buildComponent(child, mapComponents, fieldIdList, extraObjects));
				}
				tabbedPane.addTab(tab.getName(), panel);
			} else {
				throw new UnsupportedOperationException();
			}
		}
		return tabbedPane;
	}

	private JComponent buildSubForm(SubForm subForm, Map<String, JComponent> mapComponents, List<String> fieldIdList,
			Map<String, Object> extraObjects) {
		//panel.setCaption(formEditor.getName());
		StringBuilder buff = new StringBuilder("right:max(40dlu;pref), 3dlu, 20dlu:grow");
		for (int i = 1; i < subForm.getColumns(); i++) {
			buff.append(", 7dlu, right:max(40dlu;pref), 3dlu, 20dlu:grow");
		}
		FormLayout layout = new FormLayout(buff.toString(), ""); // add rows dynamically
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		for (int row = 0; row < subForm.getRows(); row++) {
			for (int column = 0; column < subForm.getColumns(); column++) {
				Component component = subForm.getField(row, column);
				if (component == null) {
					builder.nextColumn(4);
				} else if (component instanceof Field) {
					Field editor = (Field) component;
					buildField(builder, editor, mapComponents, fieldIdList, extraObjects);
				} else {
					throw new UnsupportedOperationException("A subform contains a component that is not a field");
				}
			}
			builder.nextLine();
			builder.appendRow(FormSpecs.LINE_GAP_ROWSPEC);
			builder.nextLine();
			builder.appendRow("max(6dlu;pref)");
		}
		return builder.build();
	}

	/**
	 * @param builder
	 * @param editor
	 * @param mapComponents
	 * @param fieldIdList
	 * @param extraObjects
	 */
	private JComponent buildField(DefaultFormBuilder builder, Field field, Map<String, JComponent> mapComponents,
			List<String> fieldIdList, Map<String, Object> extraObjects) {
		fieldIdList.add(field.getId());
		JComponent component = internalBuildField(builder, field, extraObjects);
		return component;
	}

	/**
	 * @param builder
	 * @param field
	 * @param extraObjects
	 * @return
	 */
	private JComponent internalBuildField(DefaultFormBuilder builder, Field field, Map<String, Object> extraObjects) {
		JComponent ret = null;
		if (field.getType().equals(Field.TYPE_BOOL)) {
			builder.nextColumn(2);
			final JCheckBox checkBox = new JCheckBox(field.getName());
			checkBox.putClientProperty(SwingFormInstanceImpl.ACCESSOR_OBJECT, new SwingAccessor() {

				@Override
				public void setValue(JComponent component, Object value) {
					((JCheckBox) component).setSelected((Boolean) value);
				}

				@Override
				public Object getValue(JComponent component) {
					return ((JCheckBox) component).isSelected();
				}
			});
			ItemListenerAdapter adapter = new ItemListenerAdapter() {

				@Override
				protected Object getValue() {
					return checkBox.isSelected();
				}
			};
			checkBox.putClientProperty(SwingFormInstanceImpl.ADAPTER_OBJECT, adapter);
			checkBox.addItemListener(adapter);
			builder.append(checkBox);
			ret = checkBox;
		} else if (field.getType().equals(Field.TYPE_DATETIME)) {
			final JDatePickerImpl dtPicker = (JDatePickerImpl) JDateComponentFactory.createJDatePicker();
			dtPicker.putClientProperty(SwingFormInstanceImpl.ACCESSOR_OBJECT, new SwingAccessor() {

				@Override
				public void setValue(JComponent component, Object value) {
					((DateModel<Calendar>) ((JDatePicker) component).getModel()).setValue((Calendar) value);
				}

				@Override
				public Object getValue(JComponent component) {
					return ((DateModel<Calendar>) ((JDatePicker) component).getModel()).getValue();
				}
			});
			builder.append(field.getName(), dtPicker);
			ChangeListenerAdapter adapter = new ChangeListenerAdapter() {

				@Override
				protected Object getValue() {
					return dtPicker.getModel().getValue();
				}
			};
			dtPicker.putClientProperty(SwingFormInstanceImpl.ADAPTER_OBJECT, adapter);
			dtPicker.getModel().addChangeListener(adapter);
			ret = dtPicker;
		} else if (field.getType().equals(Field.TYPE_TEXT) || field.getType().equals(Field.TYPE_NUMERIC)
				|| field.getType().equals(Field.TYPE_PASSWORD)) {
			final JTextField textField;
			if (field.getType().equals(Field.TYPE_PASSWORD)) {
				textField = new JPasswordField();
			} else {
				textField = new JTextField();
			}
			//textField.setColumns(field.getDisplayWidth());
			if (field.getMaxLength() != 0) {
				// TODO
			}
			textField.setToolTipText(field.getDescription());
			textField.setEditable(field.isReadOnly());

			DocumentListenerAdapter adapter = new DocumentListenerAdapter() {

				@Override
				protected Object getValue() {
					return textField.getText();
				}

				@Override
				protected Object getSource() {
					return textField;
				}
			};
			textField.putClientProperty(SwingFormInstanceImpl.ADAPTER_OBJECT, adapter);
			textField.getDocument().addDocumentListener(adapter);
			builder.append(field.getName(), textField);
			ret = textField;
		} else if (field.getType().equals(SelectorConstants.TYPE_SELECTOR)) {
			builder.nextColumn(4);
		} else if (field.getType().equals(TableConstants.TYPE_TABLE)) {
			builder.nextColumn(4);
		}
		return ret;
	}
}

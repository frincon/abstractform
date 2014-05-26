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
package org.abstractform.sampleapp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.abstractform.binding.BFormInstance;
import org.abstractform.binding.BPresenter;
import org.abstractform.binding.fluent.BFBeanBasedOwnPropertiesPresenter;
import org.abstractform.binding.fluent.BFDrawer;
import org.abstractform.binding.fluent.BFField;
import org.abstractform.binding.fluent.BFForm;
import org.abstractform.binding.fluent.BFSection;
import org.abstractform.binding.fluent.BFSubForm;
import org.abstractform.binding.fluent.BFTab;
import org.abstractform.binding.fluent.BFTabSheet;
import org.abstractform.binding.fluent.table.BFTable;
import org.abstractform.binding.fluent.table.BFTableField;
import org.abstractform.binding.validation.EMailValidator;
import org.abstractform.binding.validation.Validator;
import org.abstractform.core.FormInstance;
import org.abstractform.core.fluent.selector.AbstractSelectorProvider;
import org.abstractform.core.selector.SelectorConstants;
import org.abstractform.core.selector.SelectorProvider;
import org.abstractform.core.selector.SelectorProviderFactory;
import org.abstractform.test.common.beans.BusinessPartner;
import org.abstractform.test.common.beans.BusinessPartnerLocation;
import org.abstractform.test.common.beans.Organization;

public class SampleForm extends BFForm<BusinessPartner, BusinessPartner> {

	public static final Organization ORG1 = new Organization(1, "A0001", "Organizacion de Prueba", "Esta es la descripcion");
	public static final Organization ORG2 = new Organization(1, "A0002", "Prueba2", "Esta es la descripcion 2");
	public static final Organization ORG3 = new Organization(1, "G0056", "Prueba 3", "Esta es la descripcion 3");
	public static final Organization ORG4 = new Organization(1, "P8789", "Prueba 4", "Esta es la descripcion 4");

	private SelectorProviderFactory organizationSPF = new SelectorProviderFactory() {

		@Override
		public SelectorProvider<?> createSelectorProvider(FormInstance formInstance) {
			return organizationSelectionProvider;
		}
	};

	private SelectorProvider<Organization> organizationSelectionProvider = new AbstractSelectorProvider<Organization>() {

		private SortedSet<Organization> organizationSet;

		private Comparator<Organization> comparator = new Comparator<Organization>() {

			@Override
			public int compare(Organization o1, Organization o2) {
				return getText(o1).compareTo(getText(o2));
			}

		};

		@Override
		public SortedSet<Organization> getElements() {
			if (organizationSet == null) {
				organizationSet = new TreeSet<Organization>(comparator);

				organizationSet.add(ORG1);
				organizationSet.add(ORG2);
				organizationSet.add(ORG3);
				organizationSet.add(ORG4);

			}
			return organizationSet;
		}

		@Override
		public String getText(Organization element) {
			return element.getSearchKey() + " - " + element.getName();
		}
	};

	public static final String NAME = "Example Form";
	public static final String ID = "fSampleForm";

	public final BFSubForm SF_MAIN = addSubForm("sfMain", 2);
	public final BFField F_CIF = SF_MAIN.addField(0, 0, "fCif", "Cif code", BusinessPartner.PROPERTY_CIF_CODE)
			.description("The CIF").displayWidth(40).maxLength(10).readOnly(true).required(true);
	public final BFField F_ORGANIZACION = SF_MAIN
			.addField(0, 1, "fOrganization", "Organizacion", BusinessPartner.PROPERTY_ORGANIZATION).description("The organization")
			.displayWidth(40).readOnly(false).required(true).type(SelectorConstants.TYPE_SELECTOR)
			.extra(SelectorConstants.EXTRA_SELECTOR_PROVIDER_FACTORY, organizationSPF);

	public final BFField F_SEARCH_KEY = SF_MAIN.addField(2, 0, "fSearchKey", "Search Key", BusinessPartner.PROPERTY_SEARCH_KEY)
			.description("Search key").displayWidth(40).maxLength(40).readOnly(false).required(true);
	public final BFField F_ACTIVE = SF_MAIN.addField(2, 1, "fActive", "Active", BusinessPartner.PROPERTY_ACTIVE)
			.description("The active field").displayWidth(40).readOnly(false).required(true);
	public final BFField F_NAME = SF_MAIN.addField(3, 0, "fName", "Name", BusinessPartner.PROPERTY_NAME).description("The name")
			.displayWidth(60).maxLength(60).readOnly(false).required(true);
	public final BFField F_FISCAL_NAME = SF_MAIN.addField(3, 1, "fFiscalName", "Fiscal Name", BusinessPartner.PROPERTY_FISCAL_NAME)
			.description("The fiscal name").displayWidth(60).maxLength(60).readOnly(false).required(false);

	public final BFSection SE_FACETS = addSection("seFacets", "Facets");
	public final BFSubForm SF_FACETS = SE_FACETS.addSubForm("sfFacets", 5);
	public final BFField F_IS_CLIENT = SF_FACETS.addField(0, 0, "fIsClient", "Client", BusinessPartner.PROPERTY_CLIENT)
			.description("Is client?").readOnly(false).required(true);
	public final BFField F_IS_CREDITOR = SF_FACETS.addField(0, 1, "fIsCreditor", "Creditor", BusinessPartner.PROPERTY_CREDITOR)
			.description("Is Creditor?").readOnly(false).required(true);
	public final BFField F_IS_SUPPLIER = SF_FACETS.addField(0, 2, "fIsSupplier", "Supplier", BusinessPartner.PROPERTY_SUPPLIER)
			.description("Is supplier?").readOnly(false).required(true);
	public final BFField F_IS_EMPLOYEE = SF_FACETS.addField(0, 3, "fIsEmployee", "Employee", BusinessPartner.PROPERTY_EMPLOYEE)
			.description("Is employee?").readOnlyPresenterProperty(Presenter.PROPERTY_EMPLOYEE_READ_ONLY).required(true);
	public final BFField F_IS_AGENT = SF_FACETS.addField(0, 4, "fIsAgent", "Agent", BusinessPartner.PROPERTY_AGENT)
			.description("Is agent?").readOnlyPresenterProperty(Presenter.PROPERTY_AGENT_READ_ONLY).required(true);

	public final BFDrawer DR_OTHERS = addDrawer("drOthers", "Others");
	public final BFSection SE_MORE_DATA = DR_OTHERS.addSection("seMoreData", "More data");
	public final BFSubForm SF_MORE_DATA = SE_MORE_DATA.addSubForm("sfMoreData", 2);
	public final BFField F_FISCAL_CODE = SF_MORE_DATA
			.addField(0, 0, "fFiscalCode", "Fiscal code", BusinessPartner.PROPERTY_FISCAL_CODE).description("The fiscal code")
			.readOnly(false).required(false).displayWidth(60).maxLength(60).required(false);
	public final BFField F_MAIL = SF_MORE_DATA.addField(0, 1, "gMail", "Mail", BusinessPartner.PROPERTY_MAIL)
			.description("The e-mail").readOnly(false).displayWidth(60).maxLength(60).required(false)
			.validator(EMailValidator.INSTANCE);
	public final BFField F_ABC = SF_MORE_DATA.addField(1, 0, "fAbc", "ABC", BusinessPartner.PROPERTY_ABC)
			.description("Valoration of client").readOnly(false).required(false).displayWidth(1).maxLength(1).required(false);
	public final BFSection SE_AUDIT = DR_OTHERS.addSection("seAudit", "Audit");
	public final BFSubForm SF_AUDIT = SE_AUDIT.addSubForm("sfAudit", 2);
	public final BFField F_CREATED_BY = SF_AUDIT.addField(0, 0, "fCreatedBy", "Created By", BusinessPartner.PROPERTY_CREATED_BY)
			.description("Creator of entity").readOnly(true).required(true);
	public final BFField F_CREATED = SF_AUDIT.addField(0, 1, "fCreated", "Created", BusinessPartner.PROPERTY_CREATED)
			.description("Date of creation").readOnly(true).required(true);
	public final BFField F_UPDATED_BY = SF_AUDIT.addField(1, 0, "fUpdatedBy", "Update By", BusinessPartner.PROPERTY_UPDATED_BY)
			.description("Updated of entity").readOnly(true).required(true);
	public final BFField F_UPDATED = SF_AUDIT.addField(1, 1, "fUpdated", "Updated", BusinessPartner.PROPERTY_UPDATED)
			.description("Date of last update").readOnly(true).required(true);

	public final BFTabSheet TS_DETAIL = addTabSheet("tsDetail");
	public final BFTab T_CLIENT = TS_DETAIL.addTab("tClient", "Client");
	public final BFSubForm SF_CLIENT = T_CLIENT.addSubForm("sfClient", 2);
	public final BFField F_FIRST_SALE = SF_CLIENT.addField(0, 0, "fFirstSale", "First Sale", BusinessPartner.PROPERTY_FIRST_SALE)
			.description("Date of first sale").readOnly(true);
	public final BFField F_LAST_SALE = SF_CLIENT.addField(0, 1, "fLastSale", "Last Sale", BusinessPartner.PROPERTY_LAST_SALE)
			.description("Date of last sale").readOnly(true);
	public final BFField F_PAYMENTTERMS = SF_CLIENT
			.addField(1, 0, "fPaymentterms", "Payment Terms", BusinessPartner.PROPERTY_PAYMENT_TERMS)
			.description("Default payment terms").readOnly(false);
	public final BFField F_PAYMENTMETHOD = SF_CLIENT
			.addField(1, 1, "fPaymentmode", "Payment Method", BusinessPartner.PROPERTY_PAYMENT_METHOD)
			.description("Default payment mode").readOnly(false);

	public final BFTab T_LOCATIONS = TS_DETAIL.addTab(null, "Locations");
	public final BFSubForm SF_LOCATIONS = T_LOCATIONS.addSubForm(null, 1);
	public final BFTable F_LOCATIONS = SF_LOCATIONS.addField(0, 0, null, "Locations",
			BusinessPartner.PROPERTY_BUSINESS_PARTNER_LOCATION_SET, BFTable.class).elementsClass(BusinessPartnerLocation.class);
	public final BFTableField F_LOCATIONS_F_ADDRESS1 = F_LOCATIONS.addTableField("Address 1",
			BusinessPartnerLocation.PROPERTY_ADDRESS1);
	public final BFTableField F_LOCATIONS_F_ADDRESS2 = F_LOCATIONS.addTableField("Address 2",
			BusinessPartnerLocation.PROPERTY_ADDRESS2);
	public final BFTableField F_LOCATIONS_F_ZIP_CODE = F_LOCATIONS.addTableField("Zip Code",
			BusinessPartnerLocation.PROPERTY_ZIP_CODE);

	public SampleForm() {
		super(ID, NAME, BusinessPartner.class);
		validator(new Validator<BFormInstance<BusinessPartner, ?>>() {

			@Override
			public List<String> validate(BFormInstance<BusinessPartner, ?> value) {
				value.updateModel();
				BusinessPartner bp = value.getValue();
				if (bp != null && bp.getAbc() != null && !Arrays.asList("A", "B", "C").contains(bp.getAbc())) {
					return Arrays.asList("The value ABC must be A or B or C");
				} else {
					return null;
				}
			}
		});
		setValidationSummaryVisible(true);
	}

	@Override
	public BPresenter createPresenter(BFormInstance<BusinessPartner, ?> formInstance, BusinessPartner model) {
		Presenter presenter = new Presenter(model);
		return presenter;
	}

	public class Presenter extends BFBeanBasedOwnPropertiesPresenter<BusinessPartner> {
		private boolean employeeReadOnly = false;
		private boolean agentReadOnly = false;

		public final static String PROPERTY_EMPLOYEE_READ_ONLY = "employeeReadOnly";
		public final static String PROPERTY_AGENT_READ_ONLY = "agentReadOnly";

		protected Presenter(BusinessPartner model) {
			super(model, Arrays.asList(PROPERTY_AGENT_READ_ONLY, PROPERTY_EMPLOYEE_READ_ONLY));
			checkEmployeeAgentReadOnly();
		}

		protected void checkEmployeeAgentReadOnly() {
			if (getModel() != null) {
				if (getModel().isEmployee()) {
					setAgentReadOnly(false);
				} else {
					setAgentReadOnly(true);
				}
				if (getModel().isAgent()) {
					setEmployeeReadOnly(true);
				} else {
					setEmployeeReadOnly(false);
				}
			}
		}

		public boolean isEmployeeReadOnly() {
			return employeeReadOnly;
		}

		protected void setEmployeeReadOnly(boolean employeeReadOnly) {
			Object old = this.employeeReadOnly;
			this.employeeReadOnly = employeeReadOnly;
			firePropertyChange(PROPERTY_EMPLOYEE_READ_ONLY, old, this.employeeReadOnly);
		}

		public boolean isAgentReadOnly() {
			return agentReadOnly;
		}

		protected void setAgentReadOnly(boolean agentReadOnly) {
			Object old = this.agentReadOnly;
			this.agentReadOnly = agentReadOnly;
			firePropertyChange(PROPERTY_AGENT_READ_ONLY, old, this.agentReadOnly);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.abstractform.binding.fluent.BFBeanBasedOwnPropertiesPresenter#setPropertyValue(java.lang.String,
		 * java.lang.Object)
		 */
		@Override
		public void setPropertyValue(String propertyName, Object value) {
			super.setPropertyValue(propertyName, value);
			if (BusinessPartner.PROPERTY_EMPLOYEE.equals(propertyName) || BusinessPartner.PROPERTY_AGENT.equals(propertyName)) {
				checkEmployeeAgentReadOnly();
			} else if (BusinessPartner.PROPERTY_NAME.equals(propertyName) && getModel().getFiscalName() == null) {
				getModel().setFiscalName(getModel().getName());
			}
		}

	}
}

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
package org.abstractform.core.fluent.test;

import java.util.SortedSet;
import java.util.TreeSet;

import org.abstractform.core.Field;
import org.abstractform.core.FormInstance;
import org.abstractform.core.fluent.FDrawer;
import org.abstractform.core.fluent.FField;
import org.abstractform.core.fluent.FForm;
import org.abstractform.core.fluent.FSection;
import org.abstractform.core.fluent.FSubForm;
import org.abstractform.core.fluent.FTab;
import org.abstractform.core.fluent.FTabSheet;
import org.abstractform.core.fluent.selector.AbstractSelectorProvider;
import org.abstractform.core.fluent.selector.FSelector;
import org.abstractform.core.fluent.selector.IFSelector;
import org.abstractform.core.fluent.table.FTable;
import org.abstractform.core.fluent.table.FTableField;
import org.abstractform.core.selector.SelectorProvider;
import org.abstractform.core.selector.SelectorProviderFactory;

public class SampleForm extends FForm {

	private SelectorProviderFactory factory = new SelectorProviderFactory() {

		@Override
		public SelectorProvider<?> createSelectorProvider(FormInstance formInstance) {
			return selectorProvider;
		}
	};
	private SelectorProvider<String> selectorProvider = new AbstractSelectorProvider<String>() {

		private SortedSet<String> organizationList;

		@Override
		public SortedSet<String> getElements() {
			if (organizationList == null) {
				organizationList = new TreeSet<String>();
				organizationList.add("Organizacion 1");
				organizationList.add("Organizacion 2");
				organizationList.add("Prueba de ordenación");
				organizationList.add("AAAA Otra prueba");
			}
			return organizationList;
		}

		@Override
		public String getText(String element) {
			return element.toString();
		}
	};

	public static final String NAME = "Example Form";
	public static final String ID = "fSampleForm";

	public final FSubForm SF_MAIN = addSubForm("sfMain", 2);
	public final FField F_CIF = SF_MAIN.addField(0, 0, "fCif", "Cif code").description("The CIF").displayWidth(40).maxLength(10)
			.readOnly(true).required(true).type(Field.TYPE_TEXT);

	public final IFSelector F_ORGANIZATION = SF_MAIN.addField(0, 1, new FSelector("fOrganization", "Organization"))
			.description("Organization of the example").displayWidth(40).required(true).readOnly(false)
			.selectorProviderFactory(factory);
	public final FField F_SEARCH_KEY = SF_MAIN.addField(2, 0, "fSearchKey", "Search Key").description("Search key")
			.displayWidth(40).maxLength(40).readOnly(false).required(true).type(Field.TYPE_TEXT);
	public final FField F_ACTIVE = SF_MAIN.addField(2, 1, "fActive", "Active").description("The active field").displayWidth(40)
			.readOnly(false).required(true).type(Field.TYPE_BOOL);
	public final FField F_NAME = SF_MAIN.addField(3, 0, "fName", "Name").description("The name").displayWidth(60).maxLength(60)
			.readOnly(false).required(true).type(Field.TYPE_TEXT);
	public final FField F_FISCAL_NAME = SF_MAIN.addField(3, 1, "fFiscalName", "Fiscal Name").description("The fiscal name")
			.displayWidth(60).maxLength(60).readOnly(false).required(false).type(Field.TYPE_TEXT);

	public final FSection SE_FACETS = addSection("seFacets", "Facets");
	public final FSubForm SF_FACETS = SE_FACETS.addSubForm("sfFacets", 5);
	public final FField F_IS_CLIENT = SF_FACETS.addField(0, 0, "fIsClient", "Client").description("Is client?").readOnly(false)
			.required(true).type(Field.TYPE_BOOL);
	public final FField F_IS_CREDITOR = SF_FACETS.addField(0, 1, "fIsCreditor", "Creditor").description("Is Creditor?")
			.readOnly(false).required(true).type(Field.TYPE_BOOL);
	public final FField F_IS_SUPPLIER = SF_FACETS.addField(0, 2, "fIsSupplier", "Supplier").description("Is supplier?")
			.readOnly(false).required(true).type(Field.TYPE_BOOL);
	public final FField F_IS_EMPLOYEE = SF_FACETS.addField(0, 3, "fIsEmployee", "Employee").description("Is employee?")
			.readOnly(false).required(true).type(Field.TYPE_BOOL);
	public final FField F_IS_AGENT = SF_FACETS.addField(0, 4, "fIsAgent", "Agent").description("Is agent?").readOnly(false)
			.required(true).type(Field.TYPE_BOOL);

	public final FDrawer DR_OTHERS = addDrawer("drOthers", "Others");
	public final FSection SE_MORE_DATA = DR_OTHERS.addSection("seMoreData", "More data");
	public final FSubForm SF_MORE_DATA = SE_MORE_DATA.addSubForm("sfMoreData", 2);
	public final FField F_FISCAL_CODE = SF_MORE_DATA.addField(0, 0, "fFiscalCode", "Fiscal code").description("The fiscal code")
			.readOnly(false).required(false).displayWidth(60).maxLength(60).required(false).type(Field.TYPE_TEXT);
	public final FField F_MAIL = SF_MORE_DATA.addField(0, 1, "gMail", "Mail").description("The e-mail").readOnly(false)
			.required(false).displayWidth(60).maxLength(60).required(false).type(Field.TYPE_TEXT);
	public final FField F_ABC = SF_MORE_DATA.addField(1, 0, "fAbc", "ABC").description("Valoration of client").readOnly(false)
			.required(false).displayWidth(1).maxLength(1).required(false).type(Field.TYPE_TEXT);
	public final FSection SE_AUDIT = DR_OTHERS.addSection("seAudit", "Audit");
	public final FSubForm SF_AUDIT = SE_AUDIT.addSubForm("sfAudit", 2);
	public final FField F_CREATED_BY = SF_AUDIT.addField(0, 0, "fCreatedBy", "Created By").description("Creator of entity")
			.readOnly(true).required(true).type(Field.TYPE_TEXT);
	public final FField F_CREATED = SF_AUDIT.addField(0, 1, "fCreated", "Created").description("Date of creation").readOnly(true)
			.required(true).type(Field.TYPE_DATETIME);

	public final FField F_UPDATED_BY = SF_AUDIT.addField(1, 0, "fUpdatedBy", "Update By").description("Updated of entity")
			.readOnly(true).required(true).type(Field.TYPE_TEXT);
	public final FField F_UPDATED = SF_AUDIT.addField(0, 1, "fUpdated", "Updated").description("Date of last update")
			.readOnly(true).required(true).type(Field.TYPE_DATETIME);

	public final FTabSheet TS_DETAIL = addTabSheet("tsDetail");
	public final FTab T_CLIENT = TS_DETAIL.addTab("tClient", "Client");
	public final FSubForm SF_CLIENT = T_CLIENT.addSubForm("sfClient", 2);
	public final FField F_FIRST_SALE = SF_CLIENT.addField(0, 0, "fFirstSale", "First Sale").description("Date of first sale")
			.readOnly(true).type(Field.TYPE_DATETIME);
	public final FField F_LAST_SALE = SF_CLIENT.addField(0, 1, "fLastSale", "Last Sale").description("Date of last sale")
			.readOnly(true).type(Field.TYPE_DATETIME);
	public final FField F_PAYMENTTERMS = SF_CLIENT.addField(1, 0, "fPaymentterms", "Payment Terms")
			.description("Default payment terms").readOnly(false).type(Field.TYPE_TEXT);
	public final FField F_PAYMENTMODE = SF_CLIENT.addField(1, 1, "fPaymentmode", "Payment Mode")
			.description("Default payment mode").readOnly(false).type(Field.TYPE_TEXT);

	public final FTab T_LOCATIONS = TS_DETAIL.addTab("tLocation", "Locations");
	public final FSubForm SF_LOCATIONS = T_LOCATIONS.addSubForm(null, 1);
	public final FTable FT_LOCATIONS = SF_LOCATIONS.addField(0, 0, new FTable(null, "Locations")).pageLenght(2);
	public final FTableField FTF_ADDRESS1 = FT_LOCATIONS.addTableField(null, "Address 1").description("Line 1 of address")
			.readOnly(false).type(Field.TYPE_TEXT).maxLength(50);
	public final FTableField FTF_ADDRESS2 = FT_LOCATIONS.addTableField(null, "Address 2").description("Line 2 of address")
			.readOnly(false).type(Field.TYPE_TEXT).maxLength(50);
	public final FTableField FTF_ZIP_CODE = FT_LOCATIONS.addTableField(null, "ZIP Code").description("The ZIP Code")
			.readOnly(false).type(Field.TYPE_TEXT).maxLength(50);

	public SampleForm() {
		super(ID, NAME);
	}

}

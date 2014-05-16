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
package org.abstractform.test.common.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BusinessPartner {

	public static final String PROPERTY_ORGANIZATION = "organization";
	public static final String PROPERTY_CIF_CODE = "cifCode";
	public static final String PROPERTY_SEARCH_KEY = "searchKey";
	public static final String PROPERTY_ACTIVE = "active";
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_FISCAL_NAME = "fiscalName";
	public static final String PROPERTY_CLIENT = "client";
	public static final String PROPERTY_CREDITOR = "creditor";
	public static final String PROPERTY_SUPPLIER = "supplier";
	public static final String PROPERTY_EMPLOYEE = "employee";
	public static final String PROPERTY_AGENT = "agent";
	public static final String PROPERTY_FISCAL_CODE = "fiscalCode";
	public static final String PROPERTY_MAIL = "mail";
	public static final String PROPERTY_ABC = "abc";
	public static final String PROPERTY_CREATED = "created";
	public static final String PROPERTY_CREATED_BY = "createdBy";
	public static final String PROPERTY_UPDATED_BY = "updated";
	public static final String PROPERTY_UPDATED = "updatedBy";
	public static final String PROPERTY_FIRST_SALE = "firstSale";
	public static final String PROPERTY_LAST_SALE = "lastSale";
	public static final String PROPERTY_PAYMENT_METHOD = "paymentMethod";
	public static final String PROPERTY_PAYMENT_TERMS = "paymentTerms";
	public static final String PROPERTY_BUSINESS_PARTNER_LOCATION_SET = "businessPartnerLocationSet";

	private Organization organization;
	private String cifCode;
	private String searchKey;
	private boolean active;
	private String name;
	private String fiscalName;
	private boolean client;
	private boolean creditor;
	private boolean supplier;
	private boolean employee;
	private boolean agent;
	private String fiscalCode;
	private String mail;
	private String abc;
	private Date created;
	private String createdBy;
	private Date updated;
	private String updatedBy;
	private Date firstSale;
	private Date lastSale;
	private String paymentMethod;
	private String paymentTerms;

	private Set<BusinessPartnerLocation> businessPartnerLocationSet = new HashSet<BusinessPartnerLocation>();

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getCifCode() {
		return cifCode;
	}

	public void setCifCode(String cifCode) {
		Object old = this.cifCode;
		this.cifCode = cifCode;
		firePropertyChange("cifCode", old, this.cifCode);
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		Object old = this.searchKey;
		this.searchKey = searchKey;
		firePropertyChange("searchKey", old, this.searchKey);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		Object old = this.active;
		this.active = active;
		firePropertyChange("active", old, this.active);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		Object old = this.name;
		this.name = name;
		firePropertyChange("name", old, this.name);
	}

	public String getFiscalName() {
		return fiscalName;
	}

	public void setFiscalName(String fiscalName) {
		Object old = this.fiscalName;
		this.fiscalName = fiscalName;
		firePropertyChange("fiscalName", old, this.fiscalName);
	}

	public boolean isClient() {
		return client;
	}

	public void setClient(boolean client) {
		Object old = this.client;
		this.client = client;
		firePropertyChange("client", old, this.client);
	}

	public boolean isCreditor() {
		return creditor;
	}

	public void setCreditor(boolean creditor) {
		Object old = this.creditor;
		this.creditor = creditor;
		firePropertyChange("creditor", old, this.creditor);
	}

	public boolean isSupplier() {
		return supplier;
	}

	public void setSupplier(boolean supplier) {
		Object old = this.supplier;
		this.supplier = supplier;
		firePropertyChange("supplier", old, this.supplier);
	}

	public boolean isEmployee() {
		return employee;
	}

	public void setEmployee(boolean employee) {
		Object old = this.employee;
		this.employee = employee;
		firePropertyChange("employee", old, this.employee);
	}

	public boolean isAgent() {
		return agent;
	}

	public void setAgent(boolean agent) {
		Object old = this.agent;
		this.agent = agent;
		firePropertyChange("agent", old, this.agent);
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		Object old = this.fiscalCode;
		this.fiscalCode = fiscalCode;
		firePropertyChange("fiscalCode", old, this.fiscalCode);
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		Object old = this.mail;
		this.mail = mail;
		firePropertyChange("mail", old, this.mail);
	}

	public String getAbc() {
		return abc;
	}

	public void setAbc(String abc) {
		Object old = this.abc;
		this.abc = abc;
		firePropertyChange("abc", old, this.abc);
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		Object old = this.created;
		this.created = created;
		firePropertyChange("created", old, this.created);
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		Object old = this.createdBy;
		this.createdBy = createdBy;
		firePropertyChange("createdBy", old, this.createdBy);
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		Object old = this.updated;
		this.updated = updated;
		firePropertyChange("updated", old, this.updated);
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		Object old = this.updatedBy;
		this.updatedBy = updatedBy;
		firePropertyChange("updatedBy", old, this.updatedBy);
	}

	public Date getFirstSale() {
		return firstSale;
	}

	public void setFirstSale(Date firstSale) {
		Object old = this.firstSale;
		this.firstSale = firstSale;
		firePropertyChange("firstSale", old, this.firstSale);
	}

	public Date getLastSale() {
		return lastSale;
	}

	public void setLastSale(Date lastSale) {
		Object old = this.lastSale;
		this.lastSale = lastSale;
		firePropertyChange("lastSale", old, this.lastSale);
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		Object old = this.paymentMethod;
		this.paymentMethod = paymentMethod;
		firePropertyChange("paymentMethod", old, this.paymentMethod);
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		Object old = this.paymentTerms;
		this.paymentTerms = paymentTerms;
		firePropertyChange("paymentTerms", old, this.paymentTerms);
	}

	public Set<BusinessPartnerLocation> getBusinessPartnerLocationSet() {
		return businessPartnerLocationSet;
	}

	public void setBusinessPartnerLocationSet(Set<BusinessPartnerLocation> businessPartnerLocationSet) {
		Object old = this.businessPartnerLocationSet;
		this.businessPartnerLocationSet = businessPartnerLocationSet;
		firePropertyChange(PROPERTY_BUSINESS_PARTNER_LOCATION_SET, old, this.businessPartnerLocationSet);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}

	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pcs.firePropertyChange(propertyName, oldValue, newValue);
	}

}

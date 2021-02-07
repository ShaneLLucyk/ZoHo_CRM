/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin.
 */
package org.apache.camel.salesforce.dto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import org.apache.camel.component.salesforce.api.PicklistEnumConverter;
import org.apache.camel.component.salesforce.api.dto.AbstractDescribedSObjectBase;
import org.apache.camel.component.salesforce.api.dto.SObjectDescription;
import org.apache.camel.component.salesforce.api.dto.SObjectDescriptionUrls;
import org.apache.camel.component.salesforce.api.dto.SObjectField;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Salesforce DTO for SObject Case
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
@XStreamAlias("Case")
public class Case extends AbstractDescribedSObjectBase {

    public Case() {
        getAttributes().setType("Case");
    }

    private static final SObjectDescription DESCRIPTION = createSObjectDescription();

    private String CaseNumber;

    @JsonProperty("CaseNumber")
    public String getCaseNumber() {
        return this.CaseNumber;
    }

    @JsonProperty("CaseNumber")
    public void setCaseNumber(String CaseNumber) {
        this.CaseNumber = CaseNumber;
    }

    private String ContactId;

    @JsonProperty("ContactId")
    public String getContactId() {
        return this.ContactId;
    }

    @JsonProperty("ContactId")
    public void setContactId(String ContactId) {
        this.ContactId = ContactId;
    }

    private String AccountId;

    @JsonProperty("AccountId")
    public String getAccountId() {
        return this.AccountId;
    }

    @JsonProperty("AccountId")
    public void setAccountId(String AccountId) {
        this.AccountId = AccountId;
    }

    private String AssetId;

    @JsonProperty("AssetId")
    public String getAssetId() {
        return this.AssetId;
    }

    @JsonProperty("AssetId")
    public void setAssetId(String AssetId) {
        this.AssetId = AssetId;
    }

    private String ParentId;

    @JsonProperty("ParentId")
    public String getParentId() {
        return this.ParentId;
    }

    @JsonProperty("ParentId")
    public void setParentId(String ParentId) {
        this.ParentId = ParentId;
    }

    private String SuppliedName;

    @JsonProperty("SuppliedName")
    public String getSuppliedName() {
        return this.SuppliedName;
    }

    @JsonProperty("SuppliedName")
    public void setSuppliedName(String SuppliedName) {
        this.SuppliedName = SuppliedName;
    }

    private String SuppliedEmail;

    @JsonProperty("SuppliedEmail")
    public String getSuppliedEmail() {
        return this.SuppliedEmail;
    }

    @JsonProperty("SuppliedEmail")
    public void setSuppliedEmail(String SuppliedEmail) {
        this.SuppliedEmail = SuppliedEmail;
    }

    private String SuppliedPhone;

    @JsonProperty("SuppliedPhone")
    public String getSuppliedPhone() {
        return this.SuppliedPhone;
    }

    @JsonProperty("SuppliedPhone")
    public void setSuppliedPhone(String SuppliedPhone) {
        this.SuppliedPhone = SuppliedPhone;
    }

    private String SuppliedCompany;

    @JsonProperty("SuppliedCompany")
    public String getSuppliedCompany() {
        return this.SuppliedCompany;
    }

    @JsonProperty("SuppliedCompany")
    public void setSuppliedCompany(String SuppliedCompany) {
        this.SuppliedCompany = SuppliedCompany;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private Case_TypeEnum Type;

    @JsonProperty("Type")
    public Case_TypeEnum getType() {
        return this.Type;
    }

    @JsonProperty("Type")
    public void setType(Case_TypeEnum Type) {
        this.Type = Type;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private Case_StatusEnum Status;

    @JsonProperty("Status")
    public Case_StatusEnum getStatus() {
        return this.Status;
    }

    @JsonProperty("Status")
    public void setStatus(Case_StatusEnum Status) {
        this.Status = Status;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private Case_ReasonEnum Reason;

    @JsonProperty("Reason")
    public Case_ReasonEnum getReason() {
        return this.Reason;
    }

    @JsonProperty("Reason")
    public void setReason(Case_ReasonEnum Reason) {
        this.Reason = Reason;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private Case_OriginEnum Origin;

    @JsonProperty("Origin")
    public Case_OriginEnum getOrigin() {
        return this.Origin;
    }

    @JsonProperty("Origin")
    public void setOrigin(Case_OriginEnum Origin) {
        this.Origin = Origin;
    }

    private String Subject;

    @JsonProperty("Subject")
    public String getSubject() {
        return this.Subject;
    }

    @JsonProperty("Subject")
    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private Case_PriorityEnum Priority;

    @JsonProperty("Priority")
    public Case_PriorityEnum getPriority() {
        return this.Priority;
    }

    @JsonProperty("Priority")
    public void setPriority(Case_PriorityEnum Priority) {
        this.Priority = Priority;
    }

    private String Description;

    @JsonProperty("Description")
    public String getDescription() {
        return this.Description;
    }

    @JsonProperty("Description")
    public void setDescription(String Description) {
        this.Description = Description;
    }

    private Boolean IsClosed;

    @JsonProperty("IsClosed")
    public Boolean getIsClosed() {
        return this.IsClosed;
    }

    @JsonProperty("IsClosed")
    public void setIsClosed(Boolean IsClosed) {
        this.IsClosed = IsClosed;
    }

    private java.time.ZonedDateTime ClosedDate;

    @JsonProperty("ClosedDate")
    public java.time.ZonedDateTime getClosedDate() {
        return this.ClosedDate;
    }

    @JsonProperty("ClosedDate")
    public void setClosedDate(java.time.ZonedDateTime ClosedDate) {
        this.ClosedDate = ClosedDate;
    }

    private Boolean IsEscalated;

    @JsonProperty("IsEscalated")
    public Boolean getIsEscalated() {
        return this.IsEscalated;
    }

    @JsonProperty("IsEscalated")
    public void setIsEscalated(Boolean IsEscalated) {
        this.IsEscalated = IsEscalated;
    }

    private String EngineeringReqNumber__c;

    @JsonProperty("EngineeringReqNumber__c")
    public String getEngineeringReqNumber__c() {
        return this.EngineeringReqNumber__c;
    }

    @JsonProperty("EngineeringReqNumber__c")
    public void setEngineeringReqNumber__c(String EngineeringReqNumber__c) {
        this.EngineeringReqNumber__c = EngineeringReqNumber__c;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private Case_SLAViolationEnum SLAViolation__c;

    @JsonProperty("SLAViolation__c")
    public Case_SLAViolationEnum getSLAViolation__c() {
        return this.SLAViolation__c;
    }

    @JsonProperty("SLAViolation__c")
    public void setSLAViolation__c(Case_SLAViolationEnum SLAViolation__c) {
        this.SLAViolation__c = SLAViolation__c;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private Case_ProductEnum Product__c;

    @JsonProperty("Product__c")
    public Case_ProductEnum getProduct__c() {
        return this.Product__c;
    }

    @JsonProperty("Product__c")
    public void setProduct__c(Case_ProductEnum Product__c) {
        this.Product__c = Product__c;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private Case_PotentialLiabilityEnum PotentialLiability__c;

    @JsonProperty("PotentialLiability__c")
    public Case_PotentialLiabilityEnum getPotentialLiability__c() {
        return this.PotentialLiability__c;
    }

    @JsonProperty("PotentialLiability__c")
    public void setPotentialLiability__c(Case_PotentialLiabilityEnum PotentialLiability__c) {
        this.PotentialLiability__c = PotentialLiability__c;
    }

     private QueryRecordsCase Cases;

    @JsonProperty("Cases")
    public QueryRecordsCase getCases() {
        return Cases;
    }

    @JsonProperty("Cases")
    public void setCases(QueryRecordsCase Cases) {
        this.Cases = Cases;
    }
    private QueryRecordsEmailMessage EmailMessages;

    @JsonProperty("EmailMessages")
    public QueryRecordsEmailMessage getEmailMessages() {
        return EmailMessages;
    }

    @JsonProperty("EmailMessages")
    public void setEmailMessages(QueryRecordsEmailMessage EmailMessages) {
        this.EmailMessages = EmailMessages;
    }
    private QueryRecordsEvent Events;

    @JsonProperty("Events")
    public QueryRecordsEvent getEvents() {
        return Events;
    }

    @JsonProperty("Events")
    public void setEvents(QueryRecordsEvent Events) {
        this.Events = Events;
    }
    private QueryRecordsTask Tasks;

    @JsonProperty("Tasks")
    public QueryRecordsTask getTasks() {
        return Tasks;
    }

    @JsonProperty("Tasks")
    public void setTasks(QueryRecordsTask Tasks) {
        this.Tasks = Tasks;
    }

    @Override
    public final SObjectDescription description() {
        return DESCRIPTION;
    }

    private static SObjectDescription createSObjectDescription() {
        final SObjectDescription description = new SObjectDescription();



        final List<SObjectField> fields1 = new ArrayList<>();
        description.setFields(fields1);

        final SObjectField sObjectField1 = createField("Id", "Case ID", "id", "tns:ID", 18, false, false, false, false, false, false, true);
        fields1.add(sObjectField1);
        final SObjectField sObjectField2 = createField("IsDeleted", "Deleted", "boolean", "xsd:boolean", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField2);
        final SObjectField sObjectField3 = createField("CaseNumber", "Case Number", "string", "xsd:string", 30, false, false, true, false, false, false, true);
        fields1.add(sObjectField3);
        final SObjectField sObjectField4 = createField("ContactId", "Contact ID", "reference", "tns:ID", 18, false, true, false, false, false, false, false);
        fields1.add(sObjectField4);
        final SObjectField sObjectField5 = createField("AccountId", "Account ID", "reference", "tns:ID", 18, false, true, false, false, false, false, false);
        fields1.add(sObjectField5);
        final SObjectField sObjectField6 = createField("AssetId", "Asset ID", "reference", "tns:ID", 18, false, true, false, false, false, false, false);
        fields1.add(sObjectField6);
        final SObjectField sObjectField7 = createField("ParentId", "Parent Case ID", "reference", "tns:ID", 18, false, true, false, false, false, false, false);
        fields1.add(sObjectField7);
        final SObjectField sObjectField8 = createField("SuppliedName", "Name", "string", "xsd:string", 80, false, true, false, false, false, false, false);
        fields1.add(sObjectField8);
        final SObjectField sObjectField9 = createField("SuppliedEmail", "Email Address", "email", "xsd:string", 80, false, true, false, false, false, false, false);
        fields1.add(sObjectField9);
        final SObjectField sObjectField10 = createField("SuppliedPhone", "Phone", "string", "xsd:string", 40, false, true, false, false, false, false, false);
        fields1.add(sObjectField10);
        final SObjectField sObjectField11 = createField("SuppliedCompany", "Company", "string", "xsd:string", 80, false, true, false, false, false, false, false);
        fields1.add(sObjectField11);
        final SObjectField sObjectField12 = createField("Type", "Case Type", "picklist", "xsd:string", 40, false, true, false, false, false, false, false);
        fields1.add(sObjectField12);
        final SObjectField sObjectField13 = createField("Status", "Status", "picklist", "xsd:string", 40, false, true, false, false, false, false, false);
        fields1.add(sObjectField13);
        final SObjectField sObjectField14 = createField("Reason", "Case Reason", "picklist", "xsd:string", 40, false, true, false, false, false, false, false);
        fields1.add(sObjectField14);
        final SObjectField sObjectField15 = createField("Origin", "Case Origin", "picklist", "xsd:string", 40, false, true, false, false, false, false, false);
        fields1.add(sObjectField15);
        final SObjectField sObjectField16 = createField("Subject", "Subject", "string", "xsd:string", 255, false, true, false, false, false, false, false);
        fields1.add(sObjectField16);
        final SObjectField sObjectField17 = createField("Priority", "Priority", "picklist", "xsd:string", 40, false, true, false, false, false, false, false);
        fields1.add(sObjectField17);
        final SObjectField sObjectField18 = createField("Description", "Description", "textarea", "xsd:string", 32000, false, true, false, false, false, false, false);
        fields1.add(sObjectField18);
        final SObjectField sObjectField19 = createField("IsClosed", "Closed", "boolean", "xsd:boolean", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField19);
        final SObjectField sObjectField20 = createField("ClosedDate", "Closed Date", "datetime", "xsd:dateTime", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField20);
        final SObjectField sObjectField21 = createField("IsEscalated", "Escalated", "boolean", "xsd:boolean", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField21);
        final SObjectField sObjectField22 = createField("OwnerId", "Owner ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField22);
        final SObjectField sObjectField23 = createField("CreatedDate", "Created Date", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField23);
        final SObjectField sObjectField24 = createField("CreatedById", "Created By ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField24);
        final SObjectField sObjectField25 = createField("LastModifiedDate", "Last Modified Date", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField25);
        final SObjectField sObjectField26 = createField("LastModifiedById", "Last Modified By ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField26);
        final SObjectField sObjectField27 = createField("SystemModstamp", "System Modstamp", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField27);
        final SObjectField sObjectField28 = createField("LastViewedDate", "Last Viewed Date", "datetime", "xsd:dateTime", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField28);
        final SObjectField sObjectField29 = createField("LastReferencedDate", "Last Referenced Date", "datetime", "xsd:dateTime", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField29);
        final SObjectField sObjectField30 = createField("EngineeringReqNumber__c", "Engineering Req Number", "string", "xsd:string", 12, false, true, false, false, true, false, false);
        fields1.add(sObjectField30);
        final SObjectField sObjectField31 = createField("SLAViolation__c", "SLA Violation", "picklist", "xsd:string", 255, false, true, false, false, true, false, false);
        fields1.add(sObjectField31);
        final SObjectField sObjectField32 = createField("Product__c", "Product", "picklist", "xsd:string", 255, false, true, false, false, true, false, false);
        fields1.add(sObjectField32);
        final SObjectField sObjectField33 = createField("PotentialLiability__c", "Potential Liability", "picklist", "xsd:string", 255, false, true, false, false, true, false, false);
        fields1.add(sObjectField33);

        description.setLabel("Case");
        description.setLabelPlural("Cases");
        description.setName("Case");

        final SObjectDescriptionUrls sObjectDescriptionUrls1 = new SObjectDescriptionUrls();
        sObjectDescriptionUrls1.setApprovalLayouts("/services/data/v34.0/sobjects/Case/describe/approvalLayouts");
        sObjectDescriptionUrls1.setCaseArticleSuggestions("/services/data/v34.0/sobjects/Case/suggestedArticles");
        sObjectDescriptionUrls1.setCaseRowArticleSuggestions("/services/data/v34.0/sobjects/Case/{ID}/suggestedArticles");
        sObjectDescriptionUrls1.setCompactLayouts("/services/data/v34.0/sobjects/Case/describe/compactLayouts");
        sObjectDescriptionUrls1.setDescribe("/services/data/v34.0/sobjects/Case/describe");
        sObjectDescriptionUrls1.setLayouts("/services/data/v34.0/sobjects/Case/describe/layouts");
        sObjectDescriptionUrls1.setListviews("/services/data/v34.0/sobjects/Case/listviews");
        sObjectDescriptionUrls1.setQuickActions("/services/data/v34.0/sobjects/Case/quickActions");
        sObjectDescriptionUrls1.setRowTemplate("/services/data/v34.0/sobjects/Case/{ID}");
        sObjectDescriptionUrls1.setSobject("/services/data/v34.0/sobjects/Case");
        sObjectDescriptionUrls1.setUiDetailTemplate("https://na172.salesforce.com/{ID}");
        sObjectDescriptionUrls1.setUiEditTemplate("https://na172.salesforce.com/{ID}/e");
        sObjectDescriptionUrls1.setUiNewRecord("https://na172.salesforce.com/500/e");
        description.setUrls(sObjectDescriptionUrls1);

        return description;
    }
}

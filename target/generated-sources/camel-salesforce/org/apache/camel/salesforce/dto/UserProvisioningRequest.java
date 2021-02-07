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
import org.apache.camel.component.salesforce.api.dto.Attributes;
import org.apache.camel.component.salesforce.api.dto.ChildRelationShip;
import org.apache.camel.component.salesforce.api.dto.InfoUrls;
import org.apache.camel.component.salesforce.api.dto.NamedLayoutInfo;
import org.apache.camel.component.salesforce.api.dto.RecordTypeInfo;
import org.apache.camel.component.salesforce.api.dto.SObjectDescription;
import org.apache.camel.component.salesforce.api.dto.SObjectDescriptionUrls;
import org.apache.camel.component.salesforce.api.dto.SObjectField;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Salesforce DTO for SObject UserProvisioningRequest
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
@XStreamAlias("UserProvisioningRequest")
public class UserProvisioningRequest extends AbstractDescribedSObjectBase {

    public UserProvisioningRequest() {
        getAttributes().setType("UserProvisioningRequest");
    }

    private static final SObjectDescription DESCRIPTION = createSObjectDescription();

    private String SalesforceUserId;

    @JsonProperty("SalesforceUserId")
    public String getSalesforceUserId() {
        return this.SalesforceUserId;
    }

    @JsonProperty("SalesforceUserId")
    public void setSalesforceUserId(String SalesforceUserId) {
        this.SalesforceUserId = SalesforceUserId;
    }

    private String ExternalUserId;

    @JsonProperty("ExternalUserId")
    public String getExternalUserId() {
        return this.ExternalUserId;
    }

    @JsonProperty("ExternalUserId")
    public void setExternalUserId(String ExternalUserId) {
        this.ExternalUserId = ExternalUserId;
    }

    private String AppName;

    @JsonProperty("AppName")
    public String getAppName() {
        return this.AppName;
    }

    @JsonProperty("AppName")
    public void setAppName(String AppName) {
        this.AppName = AppName;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private UserProvisioningRequest_StateEnum State;

    @JsonProperty("State")
    public UserProvisioningRequest_StateEnum getState() {
        return this.State;
    }

    @JsonProperty("State")
    public void setState(UserProvisioningRequest_StateEnum State) {
        this.State = State;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private UserProvisioningRequest_OperationEnum Operation;

    @JsonProperty("Operation")
    public UserProvisioningRequest_OperationEnum getOperation() {
        return this.Operation;
    }

    @JsonProperty("Operation")
    public void setOperation(UserProvisioningRequest_OperationEnum Operation) {
        this.Operation = Operation;
    }

    private java.time.ZonedDateTime ScheduleDate;

    @JsonProperty("ScheduleDate")
    public java.time.ZonedDateTime getScheduleDate() {
        return this.ScheduleDate;
    }

    @JsonProperty("ScheduleDate")
    public void setScheduleDate(java.time.ZonedDateTime ScheduleDate) {
        this.ScheduleDate = ScheduleDate;
    }

    private String ConnectedAppId;

    @JsonProperty("ConnectedAppId")
    public String getConnectedAppId() {
        return this.ConnectedAppId;
    }

    @JsonProperty("ConnectedAppId")
    public void setConnectedAppId(String ConnectedAppId) {
        this.ConnectedAppId = ConnectedAppId;
    }

    private String UserProvConfigId;

    @JsonProperty("UserProvConfigId")
    public String getUserProvConfigId() {
        return this.UserProvConfigId;
    }

    @JsonProperty("UserProvConfigId")
    public void setUserProvConfigId(String UserProvConfigId) {
        this.UserProvConfigId = UserProvConfigId;
    }

    private String UserProvAccountId;

    @JsonProperty("UserProvAccountId")
    public String getUserProvAccountId() {
        return this.UserProvAccountId;
    }

    @JsonProperty("UserProvAccountId")
    public void setUserProvAccountId(String UserProvAccountId) {
        this.UserProvAccountId = UserProvAccountId;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private UserProvisioningRequest_ApprovalStatusEnum ApprovalStatus;

    @JsonProperty("ApprovalStatus")
    public UserProvisioningRequest_ApprovalStatusEnum getApprovalStatus() {
        return this.ApprovalStatus;
    }

    @JsonProperty("ApprovalStatus")
    public void setApprovalStatus(UserProvisioningRequest_ApprovalStatusEnum ApprovalStatus) {
        this.ApprovalStatus = ApprovalStatus;
    }

    private String ManagerId;

    @JsonProperty("ManagerId")
    public String getManagerId() {
        return this.ManagerId;
    }

    @JsonProperty("ManagerId")
    public void setManagerId(String ManagerId) {
        this.ManagerId = ManagerId;
    }

 
    @Override
    public final SObjectDescription description() {
        return DESCRIPTION;
    }

    private static SObjectDescription createSObjectDescription() {
        final SObjectDescription description = new SObjectDescription();



        final List<SObjectField> fields1 = new ArrayList<>();
        description.setFields(fields1);

        final SObjectField sObjectField1 = createField("Id", "UserProvisioningRequest ID", "id", "tns:ID", 18, false, false, false, false, false, false, true);
        fields1.add(sObjectField1);
        final SObjectField sObjectField2 = createField("OwnerId", "Owner ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField2);
        final SObjectField sObjectField3 = createField("IsDeleted", "Deleted", "boolean", "xsd:boolean", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField3);
        final SObjectField sObjectField4 = createField("Name", "Name", "string", "xsd:string", 255, false, false, true, false, false, false, true);
        fields1.add(sObjectField4);
        final SObjectField sObjectField5 = createField("CreatedDate", "Created Date", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField5);
        final SObjectField sObjectField6 = createField("CreatedById", "Created By ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField6);
        final SObjectField sObjectField7 = createField("LastModifiedDate", "Last Modified Date", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField7);
        final SObjectField sObjectField8 = createField("LastModifiedById", "Last Modified By ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField8);
        final SObjectField sObjectField9 = createField("SystemModstamp", "System Modstamp", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField9);
        final SObjectField sObjectField10 = createField("SalesforceUserId", "User ID", "reference", "tns:ID", 18, false, true, false, false, false, false, false);
        fields1.add(sObjectField10);
        final SObjectField sObjectField11 = createField("ExternalUserId", "External User Id", "string", "xsd:string", 150, false, true, false, false, false, false, true);
        fields1.add(sObjectField11);
        final SObjectField sObjectField12 = createField("AppName", "App Name", "string", "xsd:string", 150, false, true, false, false, false, false, true);
        fields1.add(sObjectField12);
        final SObjectField sObjectField13 = createField("State", "State", "picklist", "xsd:string", 255, false, false, false, false, false, false, false);
        fields1.add(sObjectField13);
        final SObjectField sObjectField14 = createField("Operation", "Operation", "picklist", "xsd:string", 255, false, false, false, false, false, false, false);
        fields1.add(sObjectField14);
        final SObjectField sObjectField15 = createField("ScheduleDate", "Scheduled Provisioning Time", "datetime", "xsd:dateTime", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField15);
        final SObjectField sObjectField16 = createField("ConnectedAppId", "Connected App ID", "reference", "tns:ID", 18, false, true, false, false, false, false, false);
        fields1.add(sObjectField16);
        final SObjectField sObjectField17 = createField("UserProvConfigId", "UserProvisioningConfig ID", "reference", "tns:ID", 18, false, true, false, false, false, false, false);
        fields1.add(sObjectField17);
        final SObjectField sObjectField18 = createField("UserProvAccountId", "User Provisioning Account ID", "reference", "tns:ID", 18, false, true, false, false, false, false, false);
        fields1.add(sObjectField18);
        final SObjectField sObjectField19 = createField("ApprovalStatus", "Approval Status", "picklist", "xsd:string", 255, false, false, false, false, false, false, false);
        fields1.add(sObjectField19);
        final SObjectField sObjectField20 = createField("ManagerId", "User ID", "reference", "tns:ID", 18, false, true, false, false, false, false, false);
        fields1.add(sObjectField20);

        description.setLabel("User Provisioning Request");
        description.setLabelPlural("User Provisioning Requests");
        description.setName("UserProvisioningRequest");

        final SObjectDescriptionUrls sObjectDescriptionUrls1 = new SObjectDescriptionUrls();
        sObjectDescriptionUrls1.setApprovalLayouts("/services/data/v34.0/sobjects/UserProvisioningRequest/describe/approvalLayouts");
        sObjectDescriptionUrls1.setCompactLayouts("/services/data/v34.0/sobjects/UserProvisioningRequest/describe/compactLayouts");
        sObjectDescriptionUrls1.setDescribe("/services/data/v34.0/sobjects/UserProvisioningRequest/describe");
        sObjectDescriptionUrls1.setLayouts("/services/data/v34.0/sobjects/UserProvisioningRequest/describe/layouts");
        sObjectDescriptionUrls1.setRowTemplate("/services/data/v34.0/sobjects/UserProvisioningRequest/{ID}");
        sObjectDescriptionUrls1.setSobject("/services/data/v34.0/sobjects/UserProvisioningRequest");
        sObjectDescriptionUrls1.setUiDetailTemplate("https://shanelucykcompany-dev-ed.my.salesforce.com/{ID}");
        sObjectDescriptionUrls1.setUiEditTemplate("https://shanelucykcompany-dev-ed.my.salesforce.com/{ID}/e");
        sObjectDescriptionUrls1.setUiNewRecord("https://shanelucykcompany-dev-ed.my.salesforce.com/0HP/e");
        description.setUrls(sObjectDescriptionUrls1);

        return description;
    }
}

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
 * Salesforce DTO for SObject OpportunityContactRole
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
@XStreamAlias("OpportunityContactRole")
public class OpportunityContactRole extends AbstractDescribedSObjectBase {

    public OpportunityContactRole() {
        getAttributes().setType("OpportunityContactRole");
    }

    private static final SObjectDescription DESCRIPTION = createSObjectDescription();

    private String OpportunityId;

    @JsonProperty("OpportunityId")
    public String getOpportunityId() {
        return this.OpportunityId;
    }

    @JsonProperty("OpportunityId")
    public void setOpportunityId(String OpportunityId) {
        this.OpportunityId = OpportunityId;
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

    @XStreamConverter(PicklistEnumConverter.class)
    private OpportunityContactRole_RoleEnum Role;

    @JsonProperty("Role")
    public OpportunityContactRole_RoleEnum getRole() {
        return this.Role;
    }

    @JsonProperty("Role")
    public void setRole(OpportunityContactRole_RoleEnum Role) {
        this.Role = Role;
    }

    private Boolean IsPrimary;

    @JsonProperty("IsPrimary")
    public Boolean getIsPrimary() {
        return this.IsPrimary;
    }

    @JsonProperty("IsPrimary")
    public void setIsPrimary(Boolean IsPrimary) {
        this.IsPrimary = IsPrimary;
    }

 
    @Override
    public final SObjectDescription description() {
        return DESCRIPTION;
    }

    private static SObjectDescription createSObjectDescription() {
        final SObjectDescription description = new SObjectDescription();



        final List<SObjectField> fields1 = new ArrayList<>();
        description.setFields(fields1);

        final SObjectField sObjectField1 = createField("Id", "Contact Role ID", "id", "tns:ID", 18, false, false, false, false, false, false, true);
        fields1.add(sObjectField1);
        final SObjectField sObjectField2 = createField("OpportunityId", "Opportunity ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField2);
        final SObjectField sObjectField3 = createField("ContactId", "Contact ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField3);
        final SObjectField sObjectField4 = createField("Role", "Role", "picklist", "xsd:string", 40, false, true, false, false, false, false, false);
        fields1.add(sObjectField4);
        final SObjectField sObjectField5 = createField("IsPrimary", "Primary", "boolean", "xsd:boolean", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField5);
        final SObjectField sObjectField6 = createField("CreatedDate", "Created Date", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField6);
        final SObjectField sObjectField7 = createField("CreatedById", "Created By ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField7);
        final SObjectField sObjectField8 = createField("LastModifiedDate", "Last Modified Date", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField8);
        final SObjectField sObjectField9 = createField("LastModifiedById", "Last Modified By ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField9);
        final SObjectField sObjectField10 = createField("SystemModstamp", "System Modstamp", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField10);
        final SObjectField sObjectField11 = createField("IsDeleted", "Deleted", "boolean", "xsd:boolean", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField11);

        description.setLabel("Opportunity Contact Role");
        description.setLabelPlural("Opportunity Contact Role");
        description.setName("OpportunityContactRole");

        final SObjectDescriptionUrls sObjectDescriptionUrls1 = new SObjectDescriptionUrls();
        sObjectDescriptionUrls1.setCompactLayouts("/services/data/v34.0/sobjects/OpportunityContactRole/describe/compactLayouts");
        sObjectDescriptionUrls1.setDescribe("/services/data/v34.0/sobjects/OpportunityContactRole/describe");
        sObjectDescriptionUrls1.setLayouts("/services/data/v34.0/sobjects/OpportunityContactRole/describe/layouts");
        sObjectDescriptionUrls1.setQuickActions("/services/data/v34.0/sobjects/OpportunityContactRole/quickActions");
        sObjectDescriptionUrls1.setRowTemplate("/services/data/v34.0/sobjects/OpportunityContactRole/{ID}");
        sObjectDescriptionUrls1.setSobject("/services/data/v34.0/sobjects/OpportunityContactRole");
        sObjectDescriptionUrls1.setUiDetailTemplate("https://na172.salesforce.com/{ID}");
        sObjectDescriptionUrls1.setUiEditTemplate("https://na172.salesforce.com/{ID}/e");
        sObjectDescriptionUrls1.setUiNewRecord("https://na172.salesforce.com/00K/e");
        description.setUrls(sObjectDescriptionUrls1);

        return description;
    }
}

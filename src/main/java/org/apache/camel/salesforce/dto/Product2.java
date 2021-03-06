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
 * Salesforce DTO for SObject Product2
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
@XStreamAlias("Product2")
public class Product2 extends AbstractDescribedSObjectBase {

    public Product2() {
        getAttributes().setType("Product2");
    }

    private static final SObjectDescription DESCRIPTION = createSObjectDescription();

    private String ProductCode;

    @JsonProperty("ProductCode")
    public String getProductCode() {
        return this.ProductCode;
    }

    @JsonProperty("ProductCode")
    public void setProductCode(String ProductCode) {
        this.ProductCode = ProductCode;
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

    private Boolean IsActive;

    @JsonProperty("IsActive")
    public Boolean getIsActive() {
        return this.IsActive;
    }

    @JsonProperty("IsActive")
    public void setIsActive(Boolean IsActive) {
        this.IsActive = IsActive;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private Product2_FamilyEnum Family;

    @JsonProperty("Family")
    public Product2_FamilyEnum getFamily() {
        return this.Family;
    }

    @JsonProperty("Family")
    public void setFamily(Product2_FamilyEnum Family) {
        this.Family = Family;
    }

     private QueryRecordsAsset Assets;

    @JsonProperty("Assets")
    public QueryRecordsAsset getAssets() {
        return Assets;
    }

    @JsonProperty("Assets")
    public void setAssets(QueryRecordsAsset Assets) {
        this.Assets = Assets;
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
    private QueryRecordsPricebookEntry PricebookEntries;

    @JsonProperty("PricebookEntries")
    public QueryRecordsPricebookEntry getPricebookEntries() {
        return PricebookEntries;
    }

    @JsonProperty("PricebookEntries")
    public void setPricebookEntries(QueryRecordsPricebookEntry PricebookEntries) {
        this.PricebookEntries = PricebookEntries;
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

        final SObjectField sObjectField1 = createField("Id", "Product ID", "id", "tns:ID", 18, false, false, false, false, false, false, true);
        fields1.add(sObjectField1);
        final SObjectField sObjectField2 = createField("Name", "Product Name", "string", "xsd:string", 255, false, false, true, false, false, false, true);
        fields1.add(sObjectField2);
        final SObjectField sObjectField3 = createField("ProductCode", "Product Code", "string", "xsd:string", 255, false, true, false, false, false, false, false);
        fields1.add(sObjectField3);
        final SObjectField sObjectField4 = createField("Description", "Product Description", "textarea", "xsd:string", 4000, false, true, false, false, false, false, false);
        fields1.add(sObjectField4);
        final SObjectField sObjectField5 = createField("IsActive", "Active", "boolean", "xsd:boolean", 0, false, false, false, false, false, false, false);
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
        final SObjectField sObjectField11 = createField("Family", "Product Family", "picklist", "xsd:string", 40, false, true, false, false, false, false, false);
        fields1.add(sObjectField11);
        final SObjectField sObjectField12 = createField("IsDeleted", "Deleted", "boolean", "xsd:boolean", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField12);
        final SObjectField sObjectField13 = createField("LastViewedDate", "Last Viewed Date", "datetime", "xsd:dateTime", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField13);
        final SObjectField sObjectField14 = createField("LastReferencedDate", "Last Referenced Date", "datetime", "xsd:dateTime", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField14);

        description.setLabel("Product");
        description.setLabelPlural("Products");
        description.setName("Product2");

        final SObjectDescriptionUrls sObjectDescriptionUrls1 = new SObjectDescriptionUrls();
        sObjectDescriptionUrls1.setApprovalLayouts("/services/data/v34.0/sobjects/Product2/describe/approvalLayouts");
        sObjectDescriptionUrls1.setCompactLayouts("/services/data/v34.0/sobjects/Product2/describe/compactLayouts");
        sObjectDescriptionUrls1.setDescribe("/services/data/v34.0/sobjects/Product2/describe");
        sObjectDescriptionUrls1.setLayouts("/services/data/v34.0/sobjects/Product2/describe/layouts");
        sObjectDescriptionUrls1.setQuickActions("/services/data/v34.0/sobjects/Product2/quickActions");
        sObjectDescriptionUrls1.setRowTemplate("/services/data/v34.0/sobjects/Product2/{ID}");
        sObjectDescriptionUrls1.setSobject("/services/data/v34.0/sobjects/Product2");
        sObjectDescriptionUrls1.setUiDetailTemplate("https://na172.salesforce.com/{ID}");
        sObjectDescriptionUrls1.setUiEditTemplate("https://na172.salesforce.com/{ID}/e");
        sObjectDescriptionUrls1.setUiNewRecord("https://na172.salesforce.com/01t/e");
        description.setUrls(sObjectDescriptionUrls1);

        return description;
    }
}

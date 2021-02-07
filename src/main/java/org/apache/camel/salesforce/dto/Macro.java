/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin.
 */
package org.apache.camel.salesforce.dto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.thoughtworks.xstream.annotations.XStreamAlias;

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
 * Salesforce DTO for SObject Macro
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
@XStreamAlias("Macro")
public class Macro extends AbstractDescribedSObjectBase {

    public Macro() {
        getAttributes().setType("Macro");
    }

    private static final SObjectDescription DESCRIPTION = createSObjectDescription();

    private String Description;

    @JsonProperty("Description")
    public String getDescription() {
        return this.Description;
    }

    @JsonProperty("Description")
    public void setDescription(String Description) {
        this.Description = Description;
    }

 
    @Override
    public final SObjectDescription description() {
        return DESCRIPTION;
    }

    private static SObjectDescription createSObjectDescription() {
        final SObjectDescription description = new SObjectDescription();



        final List<SObjectField> fields1 = new ArrayList<>();
        description.setFields(fields1);

        final SObjectField sObjectField1 = createField("Id", "Macro ID", "id", "tns:ID", 18, false, false, false, false, false, false, true);
        fields1.add(sObjectField1);
        final SObjectField sObjectField2 = createField("OwnerId", "Owner ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField2);
        final SObjectField sObjectField3 = createField("IsDeleted", "Deleted", "boolean", "xsd:boolean", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField3);
        final SObjectField sObjectField4 = createField("Name", "Macro Name", "string", "xsd:string", 255, false, false, true, false, false, false, true);
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
        final SObjectField sObjectField10 = createField("LastViewedDate", "Last Viewed Date", "datetime", "xsd:dateTime", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField10);
        final SObjectField sObjectField11 = createField("LastReferencedDate", "Last Referenced Date", "datetime", "xsd:dateTime", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField11);
        final SObjectField sObjectField12 = createField("Description", "Description", "textarea", "xsd:string", 4096, false, true, false, false, false, false, false);
        fields1.add(sObjectField12);

        description.setLabel("Macro");
        description.setLabelPlural("Macros");
        description.setName("Macro");

        final SObjectDescriptionUrls sObjectDescriptionUrls1 = new SObjectDescriptionUrls();
        sObjectDescriptionUrls1.setCompactLayouts("/services/data/v34.0/sobjects/Macro/describe/compactLayouts");
        sObjectDescriptionUrls1.setDescribe("/services/data/v34.0/sobjects/Macro/describe");
        sObjectDescriptionUrls1.setLayouts("/services/data/v34.0/sobjects/Macro/describe/layouts");
        sObjectDescriptionUrls1.setRowTemplate("/services/data/v34.0/sobjects/Macro/{ID}");
        sObjectDescriptionUrls1.setSobject("/services/data/v34.0/sobjects/Macro");
        sObjectDescriptionUrls1.setUiDetailTemplate("https://na172.salesforce.com/{ID}");
        sObjectDescriptionUrls1.setUiEditTemplate("https://na172.salesforce.com/{ID}/e");
        sObjectDescriptionUrls1.setUiNewRecord("https://na172.salesforce.com/0JZ/e");
        description.setUrls(sObjectDescriptionUrls1);

        return description;
    }
}

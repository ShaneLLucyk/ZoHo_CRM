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
 * Salesforce DTO for SObject ContentDocument
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
@XStreamAlias("ContentDocument")
public class ContentDocument extends AbstractDescribedSObjectBase {

    public ContentDocument() {
        getAttributes().setType("ContentDocument");
    }

    private static final SObjectDescription DESCRIPTION = createSObjectDescription();

    private Boolean IsArchived;

    @JsonProperty("IsArchived")
    public Boolean getIsArchived() {
        return this.IsArchived;
    }

    @JsonProperty("IsArchived")
    public void setIsArchived(Boolean IsArchived) {
        this.IsArchived = IsArchived;
    }

    private String ArchivedById;

    @JsonProperty("ArchivedById")
    public String getArchivedById() {
        return this.ArchivedById;
    }

    @JsonProperty("ArchivedById")
    public void setArchivedById(String ArchivedById) {
        this.ArchivedById = ArchivedById;
    }

    private java.time.LocalDate ArchivedDate;

    @JsonProperty("ArchivedDate")
    public java.time.LocalDate getArchivedDate() {
        return this.ArchivedDate;
    }

    @JsonProperty("ArchivedDate")
    public void setArchivedDate(java.time.LocalDate ArchivedDate) {
        this.ArchivedDate = ArchivedDate;
    }

    private String Title;

    @JsonProperty("Title")
    public String getTitle() {
        return this.Title;
    }

    @JsonProperty("Title")
    public void setTitle(String Title) {
        this.Title = Title;
    }

    @XStreamConverter(PicklistEnumConverter.class)
    private ContentDocument_PublishStatusEnum PublishStatus;

    @JsonProperty("PublishStatus")
    public ContentDocument_PublishStatusEnum getPublishStatus() {
        return this.PublishStatus;
    }

    @JsonProperty("PublishStatus")
    public void setPublishStatus(ContentDocument_PublishStatusEnum PublishStatus) {
        this.PublishStatus = PublishStatus;
    }

    private String LatestPublishedVersionId;

    @JsonProperty("LatestPublishedVersionId")
    public String getLatestPublishedVersionId() {
        return this.LatestPublishedVersionId;
    }

    @JsonProperty("LatestPublishedVersionId")
    public void setLatestPublishedVersionId(String LatestPublishedVersionId) {
        this.LatestPublishedVersionId = LatestPublishedVersionId;
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

    private String Description;

    @JsonProperty("Description")
    public String getDescription() {
        return this.Description;
    }

    @JsonProperty("Description")
    public void setDescription(String Description) {
        this.Description = Description;
    }

    private Integer ContentSize;

    @JsonProperty("ContentSize")
    public Integer getContentSize() {
        return this.ContentSize;
    }

    @JsonProperty("ContentSize")
    public void setContentSize(Integer ContentSize) {
        this.ContentSize = ContentSize;
    }

    private String FileType;

    @JsonProperty("FileType")
    public String getFileType() {
        return this.FileType;
    }

    @JsonProperty("FileType")
    public void setFileType(String FileType) {
        this.FileType = FileType;
    }

    private String FileExtension;

    @JsonProperty("FileExtension")
    public String getFileExtension() {
        return this.FileExtension;
    }

    @JsonProperty("FileExtension")
    public void setFileExtension(String FileExtension) {
        this.FileExtension = FileExtension;
    }

    private java.time.ZonedDateTime ContentModifiedDate;

    @JsonProperty("ContentModifiedDate")
    public java.time.ZonedDateTime getContentModifiedDate() {
        return this.ContentModifiedDate;
    }

    @JsonProperty("ContentModifiedDate")
    public void setContentModifiedDate(java.time.ZonedDateTime ContentModifiedDate) {
        this.ContentModifiedDate = ContentModifiedDate;
    }

     private QueryRecordsContentVersion ContentVersions;

    @JsonProperty("ContentVersions")
    public QueryRecordsContentVersion getContentVersions() {
        return ContentVersions;
    }

    @JsonProperty("ContentVersions")
    public void setContentVersions(QueryRecordsContentVersion ContentVersions) {
        this.ContentVersions = ContentVersions;
    }

    @Override
    public final SObjectDescription description() {
        return DESCRIPTION;
    }

    private static SObjectDescription createSObjectDescription() {
        final SObjectDescription description = new SObjectDescription();



        final List<SObjectField> fields1 = new ArrayList<>();
        description.setFields(fields1);

        final SObjectField sObjectField1 = createField("Id", "ContentDocument ID", "id", "tns:ID", 18, false, false, false, false, false, false, true);
        fields1.add(sObjectField1);
        final SObjectField sObjectField2 = createField("CreatedById", "Created By ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField2);
        final SObjectField sObjectField3 = createField("CreatedDate", "Created", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField3);
        final SObjectField sObjectField4 = createField("LastModifiedById", "Last Modified By ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField4);
        final SObjectField sObjectField5 = createField("LastModifiedDate", "Last Modified Date", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField5);
        final SObjectField sObjectField6 = createField("IsArchived", "Is Archived", "boolean", "xsd:boolean", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField6);
        final SObjectField sObjectField7 = createField("ArchivedById", "User ID", "reference", "tns:ID", 18, false, true, false, false, false, false, false);
        fields1.add(sObjectField7);
        final SObjectField sObjectField8 = createField("ArchivedDate", "Archived Date", "date", "xsd:date", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField8);
        final SObjectField sObjectField9 = createField("IsDeleted", "Is Deleted", "boolean", "xsd:boolean", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField9);
        final SObjectField sObjectField10 = createField("OwnerId", "Owner ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField10);
        final SObjectField sObjectField11 = createField("SystemModstamp", "System Modstamp", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField11);
        final SObjectField sObjectField12 = createField("Title", "Title", "string", "xsd:string", 255, false, false, true, false, false, false, true);
        fields1.add(sObjectField12);
        final SObjectField sObjectField13 = createField("PublishStatus", "Publish Status", "picklist", "xsd:string", 40, false, false, false, false, false, false, false);
        fields1.add(sObjectField13);
        final SObjectField sObjectField14 = createField("LatestPublishedVersionId", "Latest Published Version ID", "reference", "tns:ID", 18, false, true, false, false, false, false, false);
        fields1.add(sObjectField14);
        final SObjectField sObjectField15 = createField("ParentId", "Parent ID", "reference", "tns:ID", 18, false, true, false, false, false, false, false);
        fields1.add(sObjectField15);
        final SObjectField sObjectField16 = createField("LastViewedDate", "Last Viewed Date", "datetime", "xsd:dateTime", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField16);
        final SObjectField sObjectField17 = createField("LastReferencedDate", "Last Referenced Date", "datetime", "xsd:dateTime", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField17);
        final SObjectField sObjectField18 = createField("Description", "Description", "textarea", "xsd:string", 1000, false, true, false, false, false, false, false);
        fields1.add(sObjectField18);
        final SObjectField sObjectField19 = createField("ContentSize", "Size", "int", "xsd:int", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField19);
        final SObjectField sObjectField20 = createField("FileType", "File Type", "string", "xsd:string", 20, false, true, false, false, false, false, false);
        fields1.add(sObjectField20);
        final SObjectField sObjectField21 = createField("FileExtension", "File Extension", "string", "xsd:string", 40, false, true, false, false, false, false, false);
        fields1.add(sObjectField21);
        final SObjectField sObjectField22 = createField("ContentModifiedDate", "Content Modified Date", "datetime", "xsd:dateTime", 0, false, true, false, false, false, false, false);
        fields1.add(sObjectField22);

        description.setLabel("Content Document");
        description.setLabelPlural("Content Documents");
        description.setName("ContentDocument");

        final SObjectDescriptionUrls sObjectDescriptionUrls1 = new SObjectDescriptionUrls();
        sObjectDescriptionUrls1.setCompactLayouts("/services/data/v34.0/sobjects/ContentDocument/describe/compactLayouts");
        sObjectDescriptionUrls1.setDescribe("/services/data/v34.0/sobjects/ContentDocument/describe");
        sObjectDescriptionUrls1.setLayouts("/services/data/v34.0/sobjects/ContentDocument/describe/layouts");
        sObjectDescriptionUrls1.setRowTemplate("/services/data/v34.0/sobjects/ContentDocument/{ID}");
        sObjectDescriptionUrls1.setSobject("/services/data/v34.0/sobjects/ContentDocument");
        sObjectDescriptionUrls1.setUiDetailTemplate("https://na172.salesforce.com/{ID}");
        sObjectDescriptionUrls1.setUiEditTemplate("https://na172.salesforce.com/{ID}/e");
        sObjectDescriptionUrls1.setUiNewRecord("https://na172.salesforce.com/069/e");
        description.setUrls(sObjectDescriptionUrls1);

        return description;
    }
}

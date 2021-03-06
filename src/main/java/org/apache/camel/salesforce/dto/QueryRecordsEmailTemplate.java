/*
 * Salesforce Query DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.apache.camel.component.salesforce.api.dto.AbstractQueryRecordsBase;

import java.util.List;
import javax.annotation.Generated;

/**
 * Salesforce QueryRecords DTO for type EmailTemplate
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public class QueryRecordsEmailTemplate extends AbstractQueryRecordsBase {

    @XStreamImplicit
    private List<EmailTemplate> records;

    public List<EmailTemplate> getRecords() {
        return records;
    }

    public void setRecords(List<EmailTemplate> records) {
        this.records = records;
    }
}

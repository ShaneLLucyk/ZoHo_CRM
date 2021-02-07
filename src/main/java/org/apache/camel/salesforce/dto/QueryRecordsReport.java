/*
 * Salesforce Query DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.apache.camel.component.salesforce.api.dto.AbstractQueryRecordsBase;

import java.util.List;
import javax.annotation.Generated;

/**
 * Salesforce QueryRecords DTO for type Report
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public class QueryRecordsReport extends AbstractQueryRecordsBase {

    @XStreamImplicit
    private List<Report> records;

    public List<Report> getRecords() {
        return records;
    }

    public void setRecords(List<Report> records) {
        this.records = records;
    }
}

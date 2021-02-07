/*
 * Salesforce Query DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.apache.camel.component.salesforce.api.dto.AbstractQueryRecordsBase;

import java.util.List;
import javax.annotation.Generated;

/**
 * Salesforce QueryRecords DTO for type Solution
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public class QueryRecordsSolution extends AbstractQueryRecordsBase {

    @XStreamImplicit
    private List<Solution> records;

    public List<Solution> getRecords() {
        return records;
    }

    public void setRecords(List<Solution> records) {
        this.records = records;
    }
}

/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Salesforce Enumeration DTO for picklist Format
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public enum Report_FormatEnum {

    // Matrix
    MATRIX("Matrix"),

    // MultiBlock
    MULTIBLOCK("MultiBlock"),

    // Summary
    SUMMARY("Summary"),

    // Tabular
    TABULAR("Tabular");


    final String value;

    private Report_FormatEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static Report_FormatEnum fromValue(String value) {
        for (Report_FormatEnum e : Report_FormatEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

}

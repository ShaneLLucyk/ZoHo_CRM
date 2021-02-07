/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Salesforce Enumeration DTO for picklist RecurrenceInstance
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public enum Task_RecurrenceInstanceEnum {

    // First
    FIRST("First"),

    // Fourth
    FOURTH("Fourth"),

    // Last
    LAST("Last"),

    // Second
    SECOND("Second"),

    // Third
    THIRD("Third");


    final String value;

    private Task_RecurrenceInstanceEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static Task_RecurrenceInstanceEnum fromValue(String value) {
        for (Task_RecurrenceInstanceEnum e : Task_RecurrenceInstanceEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

}

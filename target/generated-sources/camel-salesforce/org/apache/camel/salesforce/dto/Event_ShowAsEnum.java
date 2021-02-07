/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Salesforce Enumeration DTO for picklist ShowAs
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public enum Event_ShowAsEnum {

    // Busy
    BUSY("Busy"),

    // Free
    FREE("Free"),

    // OutOfOffice
    OUTOFOFFICE("OutOfOffice");


    final String value;

    private Event_ShowAsEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static Event_ShowAsEnum fromValue(String value) {
        for (Event_ShowAsEnum e : Event_ShowAsEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

}
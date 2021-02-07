/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Salesforce Enumeration DTO for picklist DefaultGroupNotificationFrequency
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public enum User_DefaultGroupNotificationFrequencyEnum {

    // D
    D("D"),

    // N
    N("N"),

    // P
    P("P"),

    // W
    W("W");


    final String value;

    private User_DefaultGroupNotificationFrequencyEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static User_DefaultGroupNotificationFrequencyEnum fromValue(String value) {
        for (User_DefaultGroupNotificationFrequencyEnum e : User_DefaultGroupNotificationFrequencyEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

}

/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Salesforce Enumeration DTO for picklist UserType
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public enum Profile_UserTypeEnum {

    // CsnOnly
    CSNONLY("CsnOnly"),

    // CspLitePortal
    CSPLITEPORTAL("CspLitePortal"),

    // CustomerSuccess
    CUSTOMERSUCCESS("CustomerSuccess"),

    // Guest
    GUEST("Guest"),

    // PowerCustomerSuccess
    POWERCUSTOMERSUCCESS("PowerCustomerSuccess"),

    // PowerPartner
    POWERPARTNER("PowerPartner"),

    // SelfService
    SELFSERVICE("SelfService"),

    // Standard
    STANDARD("Standard");


    final String value;

    private Profile_UserTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static Profile_UserTypeEnum fromValue(String value) {
        for (Profile_UserTypeEnum e : Profile_UserTypeEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

}

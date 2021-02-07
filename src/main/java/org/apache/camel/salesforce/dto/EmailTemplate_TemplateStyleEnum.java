/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Salesforce Enumeration DTO for picklist TemplateStyle
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public enum EmailTemplate_TemplateStyleEnum {

    // formalLetter
    FORMALLETTER("formalLetter"),

    // freeForm
    FREEFORM("freeForm"),

    // newsletter
    NEWSLETTER("newsletter"),

    // none
    NONE("none"),

    // products
    PRODUCTS("products"),

    // promotionLeft
    PROMOTIONLEFT("promotionLeft"),

    // promotionRight
    PROMOTIONRIGHT("promotionRight");


    final String value;

    private EmailTemplate_TemplateStyleEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static EmailTemplate_TemplateStyleEnum fromValue(String value) {
        for (EmailTemplate_TemplateStyleEnum e : EmailTemplate_TemplateStyleEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

}

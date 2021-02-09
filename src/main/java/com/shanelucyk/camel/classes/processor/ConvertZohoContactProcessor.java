package com.shanelucyk.camel.classes.processor;

import com.shanelucyk.camel.classes.config.ZohoConfig;
import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.Account;
import org.apache.camel.salesforce.dto.Contact;
import org.apache.camel.salesforce.dto.Contact_SalutationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.SimpleFormatter;

@Slf4j
@Configuration
public class ConvertZohoContactProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Process: Starting Contact Transformation");
        ArrayList<Contact> newContacts = new ArrayList<>();

        HashMap<String, String> accountMap = exchange.getProperty("accountZMap", HashMap.class);
        boolean update = exchange.getProperty("updateFlag", Boolean.class);
        HashMap<String, String> contactMap = null;
        if(update){
            contactMap = exchange.getProperty("contactZMap", HashMap.class);
        }

        ArrayList<String> errors = exchange.getProperty("errorList", ArrayList.class);
        if(errors == null)
            errors = new ArrayList<>();


        //Assertions:
        assert(contactMap != null);
        assert(accountMap != null);
        assert(errors != null);


        for(ZCRMRecord zohoContact: (ArrayList<ZCRMRecord>) exchange.getProperty("processList", ArrayList.class)){
            try{
                log.debug("Contact Full Name: {}", zohoContact.getFieldValue("Full_Name"));

                //Build Mappings for ZOHO Contact to Salesforce Contact

                Contact newContact = new Contact();


                newContact.setFieldsToNull(generateNullSet());

                newContact.setZohoContactID__c(zohoContact.getEntityId().toString());


                if(update){
                    newContact.setId(contactMap.get(zohoContact.getEntityId().toString()));
                }
                //Job Info
                newContact.setDescription((String) zohoContact.getFieldValue("Description"));
                newContact.setTitle((String) zohoContact.getFieldValue("Title"));
                newContact.setAssistantName((String) zohoContact.getFieldValue("Assistant"));
                newContact.setDepartment((String) zohoContact.getFieldValue("Department"));

                if(((ZCRMRecord) zohoContact.getFieldValue("Account_Name")) == null){
                    log.warn("No Account found for Contact: {}", zohoContact.getFieldValue("Full_Name"));
                }else{
                    String accountID = ((ZCRMRecord) zohoContact.getFieldValue("Account_Name")).getEntityId().toString();
                    log.debug("Zoho Account ID for this contact: {}", accountID);
                    log.debug("Salesforce Account ID for this Contact: {}", accountMap.get(accountID));
                    newContact.setAccountId(accountMap.get(accountID));
                }

                //Personal Info
                newContact.setLastName((String) zohoContact.getFieldValue("Last_Name"));
                newContact.setFirstName((String) zohoContact.getFieldValue("First_Name"));
                newContact.setEmail((String) zohoContact.getFieldValue("Email"));


                if((String) zohoContact.getFieldValue("Salutation") != null){
                    Contact_SalutationEnum sal = Contact_SalutationEnum.fromValue((String) zohoContact.getFieldValue("Salutation"));
                    newContact.setSalutation(sal);
                }
                if((String)zohoContact.getFieldValue("Date_of_Birth") != null){
                    LocalDate bday = LocalDate.parse((String) zohoContact.getFieldValue("Date_of_Birth"));
                    newContact.setBirthdate(bday);
                }

                //Address
                newContact.setMailingStreet((String) zohoContact.getFieldValue("Mailing_Street"));
                newContact.setMailingCity((String) zohoContact.getFieldValue("Mailing_City"));
                newContact.setMailingState((String) zohoContact.getFieldValue("Mailing_State"));
                newContact.setMailingCountry((String) zohoContact.getFieldValue("Mailing_Country"));
                newContact.setMailingPostalCode((String) zohoContact.getFieldValue("Mailing_Zip"));

                //Other Addresses:
                newContact.setOtherStreet((String) zohoContact.getFieldValue("Other_Street"));
                newContact.setOtherCity((String) zohoContact.getFieldValue("Other_City"));
                newContact.setOtherState((String) zohoContact.getFieldValue("Other_State"));
                newContact.setOtherCountry((String) zohoContact.getFieldValue("Other_Country"));
                newContact.setOtherPostalCode((String) zohoContact.getFieldValue("Other_Zip"));

                //Phone Numbers
                newContact.setHomePhone((String) zohoContact.getFieldValue("Home_Phone"));
                newContact.setPhone((String) zohoContact.getFieldValue("Phone"));
                newContact.setOtherPhone((String) zohoContact.getFieldValue("Other_Phone"));
                newContact.setMobilePhone((String) zohoContact.getFieldValue("Mobile"));
                newContact.setFax((String) zohoContact.getFieldValue("Fax"));

                newContacts.add(newContact);
            }catch (Exception e){
                errors.add((String) zohoContact.getFieldValue("Full_Name") + "_" + "Type=Contact, Error=" + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        log.info("SizeOf Errors: {}, Size of Process List: {}", errors.size(), newContacts.size());
        exchange.setProperty("errorList", errors);
        exchange.setProperty("processList", newContacts);
    }

    public Set generateNullSet(){
        Set<String> s = new HashSet();
        s.add("AccountId");

        s.add("Description");
        s.add("Title");
        s.add("AssistantName");
        s.add("Department");

        s.add("LastName");
        s.add("FirstName");
        s.add("Email");
        s.add("Salutation");
        s.add("Birthdate");

//        s.add("MailingAddress");
        s.add("MailingStreet");
        s.add("MailingCity");
        s.add("MailingState");
        s.add("MailingCountry");
        s.add("MailingPostalCode");

        s.add("OtherStreet");
        s.add("OtherCity");
        s.add("OtherState");
        s.add("OtherCountry");
        s.add("OtherPostalCode");

        s.add("HomePhone");
        s.add("Fax");
        s.add("Phone");
        s.add("OtherPhone");
        s.add("MobilePhone");

        return s;
    }


}

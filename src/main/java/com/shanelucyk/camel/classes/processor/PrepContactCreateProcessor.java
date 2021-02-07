package com.shanelucyk.camel.classes.processor;

import com.shanelucyk.camel.classes.config.ZohoConfig;
import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.Contact;
import org.apache.camel.salesforce.dto.Contact_SalutationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.SimpleFormatter;

@Slf4j
@Configuration
public class PrepContactCreateProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Process: Starting Contact Transformation");
        ArrayList<Contact> newContacts = new ArrayList<>();
        log.info("Converting ZOHO Contacts to Salesforce");
        // NOTES For account handling
        //Build list of Account Name to ID strings. PRE Contact Load.
        //Use to populate new Account field.
        HashMap<String, String> accountMap = exchange.getProperty("accountMap", HashMap.class);


        for(ZCRMRecord zohoContact: (ArrayList<ZCRMRecord>) exchange.getProperty("createList", ArrayList.class)){
            log.info("Contact Full Name: {}", zohoContact.getFieldValue("Full_Name"));

            //Build Mappings for ZOHO Contact to Salesforce Contact

            Contact newContact = new Contact();

            //Job Info
            newContact.setDescription((String) zohoContact.getFieldValue("Description"));
            newContact.setTitle((String) zohoContact.getFieldValue("Title"));
            newContact.setAssistantName((String) zohoContact.getFieldValue("Assistant"));
            newContact.setDepartment((String) zohoContact.getFieldValue("Department"));
            newContact.setAccountId(accountMap.get(((ZCRMRecord) zohoContact.getFieldValue("Account_Name")).getLookupLabel()));


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


//            if(newContact.getFirstName().equalsIgnoreCase("Kris"))

            newContacts.add(newContact);
        }

        exchange.setProperty("createList", newContacts);
    }
}

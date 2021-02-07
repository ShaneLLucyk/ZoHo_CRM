package com.shanelucyk.camel.classes.processor;

import com.shanelucyk.camel.classes.config.ZohoConfig;
import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Slf4j
@Configuration
public class SeperateContactProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        ArrayList<ZCRMRecord> contacts = exchange.getProperty("Contacts", ArrayList.class);
        ArrayList<ZCRMRecord> createList = new ArrayList<>();
        ArrayList<ZCRMRecord> updateList = new ArrayList<>();
        ArrayList<String> emails = exchange.getProperty("SalesforceContactEmails", ArrayList.class);

        for(ZCRMRecord contact: contacts){
            String contactEmail = contact.getFieldValue("Email").toString();
            log.info("Current User: {}", contactEmail);
            if(emails.contains(contactEmail)){
                log.info("User Exists, Updating");
                updateList.add(contact);
            }else{
                log.info("User does not exist, Creating");
                createList.add(contact);
            }
        }

        exchange.setProperty("createList", createList);
        exchange.setProperty("updateList", updateList);
    }
}

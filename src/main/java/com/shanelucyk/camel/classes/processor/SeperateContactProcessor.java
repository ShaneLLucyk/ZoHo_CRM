package com.shanelucyk.camel.classes.processor;

import com.shanelucyk.camel.classes.config.ZohoConfig;
import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class SeperateContactProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Process: Seperate Contacts");

        ArrayList<ZCRMRecord> contacts = exchange.getProperty("Contacts", ArrayList.class);
        ArrayList<ZCRMRecord> createList = new ArrayList<>();
        ArrayList<ZCRMRecord> updateList = new ArrayList<>();
//        ArrayList<String> emails = exchange.getProperty("SalesforceContactEmails", ArrayList.class);
        ArrayList<String> zids = (ArrayList<String>) (exchange.getProperty("contactZMap", HashMap.class).keySet().stream().collect(Collectors.toList()));


        for(ZCRMRecord contact: contacts){
            String contactId = contact.getEntityId().toString();
            log.info("Current User: {}", contactId);
            if(zids.contains(contactId)){
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

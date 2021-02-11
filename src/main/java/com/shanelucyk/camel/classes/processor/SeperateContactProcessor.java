package com.shanelucyk.camel.classes.processor;

import com.shanelucyk.camel.classes.config.ZohoConfig;
import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SeperateContactProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Process: Separate Contacts");

        ArrayList<ZCRMRecord> contacts = exchange.getProperty("Contacts", ArrayList.class);
        ArrayList<ZCRMRecord> createList = new ArrayList<>();
        ArrayList<ZCRMRecord> updateList = new ArrayList<>();
        ArrayList<String> zids = (ArrayList<String>) (exchange.getProperty("contactZMap", HashMap.class).keySet().stream().collect(Collectors.toList()));

        for(ZCRMRecord contact: contacts){
            String contactId = contact.getEntityId().toString();
            log.debug("Current Contact: {}", contactId);
            if(zids.contains(contactId)){
                log.debug("Contact Exists, Updating");
                updateList.add(contact);
            }else{
                log.debug("Contact does not exist, Creating");
                createList.add(contact);
            }
        }
        exchange.removeProperty("Contacts");
        exchange.setProperty("createList", createList);
        exchange.setProperty("updateList", updateList);
        exchange.setProperty("conSuccessList", new ArrayList<>());

        log.info("Contact Create Size: {}, Update Size: {}", createList.size(), updateList.size());
    }
}

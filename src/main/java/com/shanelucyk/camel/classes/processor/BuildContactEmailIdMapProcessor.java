package com.shanelucyk.camel.classes.processor;


import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.Account;
import org.apache.camel.salesforce.dto.Contact;
import org.apache.camel.salesforce.dto.QueryRecordsAccount;
import org.apache.camel.salesforce.dto.QueryRecordsContact;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class BuildContactEmailIdMapProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        //TODO possibly add Accumulation logic incase more than page number acocunts are returned.
        QueryRecordsContact contacts = exchange.getIn().getBody(QueryRecordsContact.class);
        HashMap<String, String> contactMap = new HashMap();
        for(Contact a: contacts.getRecords()){
            log.debug("Adding {}:{}", a.getEmail(), a.getId());
            contactMap.put(a.getEmail(), a.getId());
        }
        exchange.setProperty("contactMap", contactMap);

    }
}

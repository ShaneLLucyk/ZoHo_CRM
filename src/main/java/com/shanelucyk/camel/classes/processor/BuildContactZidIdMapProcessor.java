package com.shanelucyk.camel.classes.processor;


import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.Account;
import org.apache.camel.salesforce.dto.Contact;
import org.apache.camel.salesforce.dto.QueryRecordsAccount;
import org.apache.camel.salesforce.dto.QueryRecordsContact;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Slf4j
@Configuration
public class BuildContactZidIdMapProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        //TODO possibly add Accumulation logic incase more than page number acocunts are returned.
        QueryRecordsContact contacts = exchange.getIn().getBody(QueryRecordsContact.class);
        HashMap<String, String> accountMap = new HashMap();
        for(Contact a: contacts.getRecords()){
            if(a.getZohoContactID__c() == null)

                log.info("Adding {}:{} to zidMap", a.getZohoContactID__c(), a.getId());
            accountMap.put(a.getZohoContactID__c(), a.getId());
        }
        exchange.setProperty("contactZMap", accountMap);

    }
}

package com.shanelucyk.camel.classes.processor;


import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.Account;
import org.apache.camel.salesforce.dto.QueryRecordsAccount;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Slf4j
@Configuration
public class BuildAccountZidIdMapProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        //TODO possibly add Accumulation logic incase more than page number acocunts are returned.
        QueryRecordsAccount accounts = exchange.getIn().getBody(QueryRecordsAccount.class);
        HashMap<String, String> accountMap = new HashMap();
        for(Account a: accounts.getRecords()){
            if(a.getZohoAccountID__c() == null)

                log.info("Adding {}:{} to zidMap", a.getZohoAccountID__c(), a.getId());
            accountMap.put(a.getZohoAccountID__c(), a.getId());
        }
        exchange.setProperty("accountZMap", accountMap);

    }
}

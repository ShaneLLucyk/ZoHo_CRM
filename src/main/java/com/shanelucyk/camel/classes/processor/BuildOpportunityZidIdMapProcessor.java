package com.shanelucyk.camel.classes.processor;


import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.Contact;
import org.apache.camel.salesforce.dto.Opportunity;
import org.apache.camel.salesforce.dto.QueryRecordsContact;
import org.apache.camel.salesforce.dto.QueryRecordsOpportunity;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class BuildOpportunityZidIdMapProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        //TODO possibly add Accumulation logic incase more than page number acocunts are returned.
        QueryRecordsOpportunity opportunities = exchange.getIn().getBody(QueryRecordsOpportunity.class);
        HashMap<String, String> accountMap = new HashMap();
        for(Opportunity a: opportunities.getRecords()){
            if(a.getZohoOpportunityID__c() != null)
                accountMap.put(a.getZohoOpportunityID__c(), a.getId());
        }
        exchange.setProperty("opportunityZMap", accountMap);

    }
}

package com.shanelucyk.camel.classes.processor;

import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SeperateOpportunityProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        log.debug("Process: Seperate Opportunities");
        ArrayList<ZCRMRecord> opportunties = exchange.getProperty("Opportunities", ArrayList.class);
        ArrayList<ZCRMRecord> createList = new ArrayList<>();
        ArrayList<ZCRMRecord> updateList = new ArrayList<>();
        ArrayList<String> zids = (ArrayList<String>) (exchange.getProperty("opportunityZMap", HashMap.class).keySet().stream().collect(Collectors.toList()));

        for(ZCRMRecord opportunity: opportunties){
            String accountZid = opportunity.getEntityId().toString();
            log.debug("Current Zoho Opportunity ID: {}", accountZid);
            if(zids.contains(accountZid)){
                log.debug("Opportunity Exists In Salesforce, Updating");
                updateList.add(opportunity);
            }else{
                log.debug("Opportunity does not exist in Salesforce, Creating");
                createList.add(opportunity);
            }
        }
        exchange.removeProperty("Opportunities");
        exchange.setProperty("createList", createList);
        exchange.setProperty("updateList", updateList);
        exchange.setProperty("oppSuccessList", new ArrayList<>());
        log.debug("Opportunity Create Size: {}, Update Size: {}", createList.size(), updateList.size());
    }
}

package com.shanelucyk.camel.classes.processor;

import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class SeperateAccountProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Process: Seperate Accounts");
        ArrayList<ZCRMRecord> accounts = exchange.getProperty("Accounts", ArrayList.class);
        ArrayList<ZCRMRecord> createList = new ArrayList<>();
        ArrayList<ZCRMRecord> updateList = new ArrayList<>();
//        ArrayList<String> accountNames = (ArrayList<String>) (exchange.getProperty("accountMap", HashMap.class).keySet().stream().collect(Collectors.toList()));
        ArrayList<String> zids = (ArrayList<String>) (exchange.getProperty("accountZMap", HashMap.class).keySet().stream().collect(Collectors.toList()));

        for(ZCRMRecord account: accounts){
            String accountZid = account.getEntityId().toString();
            log.info("Current Zoho Account ID: {}", accountZid);
            if(zids.contains(accountZid)){
                log.info("Account Exists In Salesforce, Updating");
                updateList.add(account);
            }else{
                log.info("Account does not exist in Salesforce, Creating");
                createList.add(account);
            }
        }

        exchange.setProperty("createList", createList);
        exchange.setProperty("updateList", updateList);
        log.info("Create Size: {}, Update Size: {}", createList.size(), updateList.size());
    }
}

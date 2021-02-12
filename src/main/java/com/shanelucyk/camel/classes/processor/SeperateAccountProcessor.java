package com.shanelucyk.camel.classes.processor;

import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SeperateAccountProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        log.debug("Process: Seperate Accounts");
        ArrayList<ZCRMRecord> accounts = exchange.getProperty("Accounts", ArrayList.class);
        ArrayList<ZCRMRecord> createList = new ArrayList<>();
        ArrayList<ZCRMRecord> updateList = new ArrayList<>();
        ArrayList<String> zids = (ArrayList<String>) (exchange.getProperty("accountZMap", HashMap.class).keySet().stream().collect(Collectors.toList()));

        for(ZCRMRecord account: accounts){
            String accountZid = account.getEntityId().toString();
            log.debug("Current Zoho Account ID: {}", accountZid);
            if(zids.contains(accountZid)){
                log.debug("Account Exists In Salesforce, Updating");
                updateList.add(account);
            }else{
                log.debug("Account does not exist in Salesforce, Creating");
                createList.add(account);
            }
        }
        exchange.removeProperty("Accounts");
        exchange.setProperty("createList", createList);
        exchange.setProperty("updateList", updateList);

        exchange.setProperty("accSuccessList", new ArrayList<>());
        log.info("Account Create Size: {}, Update Size: {}", createList.size(), updateList.size());
    }
}

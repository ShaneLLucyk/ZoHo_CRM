package com.shanelucyk.camel.classes.processor;

import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Slf4j
@Configuration
public class SeperateAccountProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        ArrayList<ZCRMRecord> accounts = exchange.getProperty("Accounts", ArrayList.class);
        ArrayList<ZCRMRecord> createList = new ArrayList<>();
        ArrayList<ZCRMRecord> updateList = new ArrayList<>();
        ArrayList<String> accountNames = exchange.getProperty("accountNames", ArrayList.class);

        for(ZCRMRecord account: accounts){
            String accountName = account.getFieldValue("Account_Name").toString();
            log.info("Current AccountName: {}", accountName);
            if(accountNames.contains(accountName)){
                log.info("Account Exists Exists, Updating");
                updateList.add(account);
            }else{
                log.info("Account does not exist, Creating");
                createList.add(account);
            }
        }

        exchange.setProperty("createList", createList);
        exchange.setProperty("updateList", updateList);
        log.info("Create Size: {}, Update Size: {}", createList.size(), updateList.size());
    }
}

package com.shanelucyk.camel.classes.processor;

import com.shanelucyk.camel.classes.config.ZohoConfig;
import com.zoho.crm.library.api.response.BulkAPIResponse;
import com.zoho.crm.library.crud.ZCRMModule;
import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Slf4j
@Configuration
public class RetrieveZohoContactsProcessor implements Processor {
    @Autowired
    ZohoConfig zohoConfig;

    @Override
    public void process(Exchange exchange) throws Exception {

        log.info("Retrieving Accounts from Zoho");
        ZCRMModule mod = zohoConfig.getZohoClient().getModuleInstance("contacts");
        log.info("After Module Retrieval");
        BulkAPIResponse records = mod.getRecords();
        log.info("After Contact Records Retrieval");
        log.info(records.getResponseJSON().toString());
        ArrayList<ZCRMRecord> contacts = (ArrayList<ZCRMRecord>) records.getData();
        exchange.setProperty("Contacts", contacts);
    }
}

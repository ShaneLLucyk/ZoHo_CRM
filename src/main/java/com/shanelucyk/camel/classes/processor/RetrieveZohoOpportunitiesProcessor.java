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
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
public class RetrieveZohoOpportunitiesProcessor implements Processor {
    @Autowired
    ZohoConfig zohoConfig;

    @Override
    public void process(Exchange exchange) throws Exception {

        log.info("Retrieving Opportunities from Zoho");
        ZCRMModule mod = zohoConfig.getZohoClient().getModuleInstance("deals");
        log.debug("After Module Retrieval");
        BulkAPIResponse records = mod.getRecords();
        log.debug("After Opportunity Records Retrieval");
        log.info("{} opportunities retrieved", records.getData().size());
        ArrayList<ZCRMRecord> deals = (ArrayList<ZCRMRecord>) records.getData();
        exchange.setProperty("Opportunities", deals);
    }
}

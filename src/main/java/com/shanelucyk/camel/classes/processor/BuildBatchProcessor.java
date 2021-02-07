package com.shanelucyk.camel.classes.processor;

import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.salesforce.api.dto.composite.SObjectBatch;
import org.apache.camel.salesforce.dto.Contact;
import org.apache.camel.salesforce.dto.Contact_SalutationEnum;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;

@Slf4j
@Configuration
public class BuildBatchProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Process: Starting Batch Creation");
        ArrayList<Contact> newContacts = exchange.getProperty("createList", ArrayList.class);
        final SObjectBatch batch = new SObjectBatch("34.0");
        batch.addCreate(newContacts.get(0));
//        for(Contact c: newContacts){
//            log.info("Adding {} to batch", c.getName());
//            batch.addCreate(c);
//        }
        exchange.getIn().setBody(batch);
    }
}

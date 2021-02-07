package com.shanelucyk.camel.classes.processor;

import com.shanelucyk.camel.classes.config.ZohoConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.Contact;
import org.apache.camel.salesforce.dto.QueryRecordsContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Slf4j
@Configuration
public class GetSalesforceContactEmailsProcessor implements Processor {
    @Autowired
    ZohoConfig zohoConfig;

    @Override
    public void process(Exchange exchange) throws Exception {
        //TODO possibly add Accumulation logic incase more than page number contacts are returned.

        QueryRecordsContact contacts = exchange.getIn().getBody(QueryRecordsContact.class);
        ArrayList<String> emails = new ArrayList<>();
        for(Contact c: contacts.getRecords()){
//                                log.info(c.getEmail());
            emails.add(c.getEmail());
        }
        exchange.setProperty("SalesforceContactEmails", emails);
    }
}

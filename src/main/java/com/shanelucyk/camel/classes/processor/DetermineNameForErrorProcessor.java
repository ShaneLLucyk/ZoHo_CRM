package com.shanelucyk.camel.classes.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.Account;
import org.apache.camel.salesforce.dto.Contact;
import org.apache.camel.salesforce.dto.Opportunity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DetermineNameForErrorProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String type = exchange.getProperty("runType", String.class);
        String name = "";
        if(type.equalsIgnoreCase("OPPORTUNITY")){
            Opportunity opt = exchange.getIn().getBody(Opportunity.class);
            name = opt.getName();
        }else if (type.equalsIgnoreCase("CONTACT")){
            Contact con = exchange.getIn().getBody(Contact.class);
            name = con.getFirstName() + " " + con.getLastName();
        }else{
            Account acc = exchange.getIn().getBody(Account.class);
            name = acc.getName();
        }
        log.debug("Entity Name: {}", name);
        exchange.setProperty("entityName", name);
    }
}

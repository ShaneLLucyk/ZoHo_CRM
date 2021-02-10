package com.shanelucyk.camel.classes.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.Account;
import org.apache.camel.salesforce.dto.Contact;
import org.apache.camel.salesforce.dto.Opportunity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
public class UpdateSuccessListProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String type = exchange.getProperty("runType", String.class);
        String route = exchange.getProperty("routingType", String.class);

        String name = exchange.getProperty("entityName", String.class);

        if(type.equalsIgnoreCase("OPPORTUNITY")){
            ArrayList<String> opportunitySuccess = exchange.getProperty("oppSuccessList", ArrayList.class);
            opportunitySuccess.add(name + "-" + route);
            exchange.setProperty("oppSuccessList", opportunitySuccess);
        }else if (type.equalsIgnoreCase("CONTACT")){
            ArrayList<String> contactSuccess = exchange.getProperty("conSuccessList", ArrayList.class);
            contactSuccess.add(name + "-" + route);
            exchange.setProperty("conSuccessList", contactSuccess);
        }else{
            ArrayList<String> accountSuccess = exchange.getProperty("accSuccessList", ArrayList.class);
            accountSuccess.add(name + "-" + route);
            exchange.setProperty("accSuccessList", accountSuccess);
        }
    }
}

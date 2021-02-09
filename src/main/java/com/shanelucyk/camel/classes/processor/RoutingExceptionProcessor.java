package com.shanelucyk.camel.classes.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.salesforce.api.SalesforceException;
import org.apache.camel.component.salesforce.api.dto.RestError;
import org.apache.camel.salesforce.dto.Opportunity;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Slf4j
@Configuration
public class RoutingExceptionProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        ArrayList<String> errors = exchange.getProperty("errorList", ArrayList.class);

        String type = exchange.getProperty("routingErrorType", String.class);
        log.warn("Routing Type: {}", type);
        if(errors == null){
            errors = new ArrayList<>();
        }
        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        if(e instanceof SalesforceException){
            SalesforceException se = (SalesforceException) e;
            for(RestError re: se.getErrors()){
                re.getMessage();
                errors.add("ROUTING-" + type + "-EXCEPTION" +"_"  + e.getClass().getSimpleName() + ": " + re.getMessage());
            }
        }else{
            errors.add("ROUTING-"+type+"-EXCEPTION" +"_"  + e.getClass().getSimpleName() + ": " + e.getMessage());

        }
        exchange.setProperty("errorList", errors);
    }
}

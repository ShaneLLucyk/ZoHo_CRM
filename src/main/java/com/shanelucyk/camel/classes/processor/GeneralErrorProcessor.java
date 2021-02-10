package com.shanelucyk.camel.classes.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
public class GeneralErrorProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String errorBody = "The following errors occured during regular processing (Non-Critical):";
        ArrayList<String> errors = exchange.getProperty("errorList", ArrayList.class) ;
        assert (errors != null);

        for(String e: errors){
            errorBody += "\n" + "- Name=" +e.split("_")[0] + ", " + e.split("_")[1];

        }
        log.info("Error Body: {}", errorBody);
        exchange.getIn().setBody(errorBody);

    }
}

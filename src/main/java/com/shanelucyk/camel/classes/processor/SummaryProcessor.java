package com.shanelucyk.camel.classes.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
@Component
public class SummaryProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        ArrayList<String> opportunitySuccess = exchange.getProperty("oppSuccessList", ArrayList.class);
        ArrayList<String> contactSuccess = exchange.getProperty("conSuccessList", ArrayList.class);
        ArrayList<String> accountSuccess = exchange.getProperty("accSuccessList", ArrayList.class);

        StringBuilder accountSync = new StringBuilder();
        StringBuilder accountCreate = new StringBuilder();

        StringBuilder contactSync = new StringBuilder();
        StringBuilder contactCreate =  new StringBuilder();

        StringBuilder opportunitySync =  new StringBuilder();
        StringBuilder opportunityCreate =  new StringBuilder();


        for(String s: accountSuccess){
            String type = s.split("-")[1];
            String name = s.split("-")[0];
            if(type.equalsIgnoreCase("UPDATE")){
                accountSync.append("\n        " + name);
            }else{
                accountCreate.append("\n        " + name);
            }
        }

        for(String s: contactSuccess){
            String type = s.split("-")[1];
            String name = s.split("-")[0];
            if(type.equalsIgnoreCase("UPDATE")){
                contactSync.append("\n        " + name);
            }else{
                contactCreate.append("\n        " + name);
            }
        }

        for(String s: opportunitySuccess){
            String type = s.split("-")[1];
            String name = s.split("-")[0];
            if(type.equalsIgnoreCase("UPDATE")){
                opportunitySync.append("\n        " + name);
            }else{
                opportunityCreate.append("\n        " + name);
            }
        }

        String body = String.format(
                "\n************************************************************\n" +
                        "Synchronization Complete at: %s\n" +
                        "Accounts Successfully Processed: \n    Created: %s\n    Syncrhonized: %s\n" +
                        "Contacts Successfully Processed: \n    Created: %s\n    Syncrhonized: %s\n" +
                        "Opportunities Successfully Processed: \n    Created: %s\n    Syncrhonized: %s" +
                "\n************************************************************\n",
                LocalDateTime.now(),
                (accountCreate.toString().isEmpty()? "No Successful Creates": accountCreate.toString()), (accountSync.toString().isEmpty()? "No Successful Creates": accountSync.toString()),
                (contactCreate.toString().isEmpty()? "No Successful Creates": contactCreate.toString()), (contactSync.toString().isEmpty()? "No Successful Creates": contactSync.toString()),
                (opportunityCreate.toString().isEmpty()? "No Successful Creates": opportunityCreate.toString()), (opportunitySync.toString().isEmpty()? "No Successful Creates": opportunitySync.toString())
                );
        exchange.getIn().setBody(body);
    }
}

package com.shanelucyk.camel.classes.processor;

import com.shanelucyk.camel.classes.config.ZohoConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Component("retrieveContactProcessor")
public class RetrieveContactProcessor implements Processor {

    @Autowired
    ZohoConfig zohoConfig;

    @Override
    public void process(Exchange exchange) throws Exception {

    }
}

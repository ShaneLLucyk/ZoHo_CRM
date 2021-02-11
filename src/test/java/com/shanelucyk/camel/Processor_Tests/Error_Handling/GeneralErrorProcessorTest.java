package com.shanelucyk.camel.Processor_Tests.Error_Handling;

import com.shanelucyk.camel.DemoApplication;
import com.shanelucyk.camel.classes.processor.GeneralErrorProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = {DemoApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class GeneralErrorProcessorTest {

    @Autowired
    GeneralErrorProcessor generalErrorProcessor;

    @Test
    public void GeneralErrorSummary() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<String> errors = new ArrayList<>();
        errors.add("TestEntity1ForSFException" + "_Issue=" + "UPDATE" + ", Type=" + "Opportunity" + ",  Error="  + "SalesforceException" + ": " + "Unable to Update/Create fields: Expected Revenue. Please check security setings");
        errors.add("TestEntity2ForSFException" + "_Issue=" + "CREATE" + ", Type=" + "Opportunity" + ",  Error="  + "SalesforceException" + ": " + "Unable to Update/Create fields: Expected Revenue. Please check security setings");
        errors.add("TestEntity3ForInConvertError_" + "Type=" + "Opportunity" + ",  Error="  + "NullPointerException" + ": " + "null");
        exchange.setProperty("errorList", errors) ;
        generalErrorProcessor.process(exchange);
        String body = exchange.getIn().getBody(String.class);
        log.info("Body: ", body);
        assertEquals("The following errors occured during regular processing (Non-Critical):\n" +
                "- Name=TestEntity1ForSFException, Issue=UPDATE, Type=Opportunity,  Error=SalesforceException: Unable to Update/Create fields: Expected Revenue. Please check security setings\n" +
                "- Name=TestEntity2ForSFException, Issue=CREATE, Type=Opportunity,  Error=SalesforceException: Unable to Update/Create fields: Expected Revenue. Please check security setings\n" +
                "- Name=TestEntity3ForInConvertError, Type=Opportunity,  Error=NullPointerException: null", body);
    }
}

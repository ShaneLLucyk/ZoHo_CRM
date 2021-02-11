package com.shanelucyk.camel.Processor_Tests.Summary;

import com.shanelucyk.camel.DemoApplication;
import com.shanelucyk.camel.classes.processor.UpdateSuccessListProcessor;
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
public class UpdateSuccessListProcessorTest {

    @Autowired
    UpdateSuccessListProcessor updateSuccessListProcessor;

    @Test
    public void testOpportunity_Update() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);
        String type = "OPPORTUNITY";
        String route = "Update";
        String name = "TestOpportunity";

        exchange.setProperty("runType", type);
        exchange.setProperty("routingType", route);
        exchange.setProperty("entityName", name);
        exchange.setProperty("oppSuccessList", new ArrayList<>());
        updateSuccessListProcessor.process(exchange);
        ArrayList<String> test = exchange.getProperty("oppSuccessList", ArrayList.class);
        assertEquals(1, test.size());
        assertEquals("TestOpportunity-Update", test.get(0));
    }

    @Test
    public void testOpportunity_Create() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);
        String type = "OPPORTUNITY";
        String route = "Create";
        String name = "TestOpportunity";

        exchange.setProperty("runType", type);
        exchange.setProperty("routingType", route);
        exchange.setProperty("entityName", name);
        exchange.setProperty("oppSuccessList", new ArrayList<>());
        updateSuccessListProcessor.process(exchange);
        ArrayList<String> test = exchange.getProperty("oppSuccessList", ArrayList.class);
        assertEquals(1, test.size());
        assertEquals("TestOpportunity-Create", test.get(0));
    }



    @Test
    public void testAccount_Update() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);
        String type = "ACCOUNT";
        String route = "Update";
        String name = "testACCOUNT";

        exchange.setProperty("runType", type);
        exchange.setProperty("routingType", route);
        exchange.setProperty("entityName", name);
        exchange.setProperty("accSuccessList", new ArrayList<>());
        updateSuccessListProcessor.process(exchange);
        ArrayList<String> test = exchange.getProperty("accSuccessList", ArrayList.class);
        assertEquals(1, test.size());
        assertEquals("testACCOUNT-Update", test.get(0));
    }

    @Test
    public void testAccount_Create() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);
        String type = "ACCOUNT";
        String route = "Create";
        String name = "testACCOUNT";

        exchange.setProperty("runType", type);
        exchange.setProperty("routingType", route);
        exchange.setProperty("entityName", name);
        exchange.setProperty("accSuccessList", new ArrayList<>());
        updateSuccessListProcessor.process(exchange);
        ArrayList<String> test = exchange.getProperty("accSuccessList", ArrayList.class);
        assertEquals(1, test.size());
        assertEquals("testACCOUNT-Create", test.get(0));
    }




    @Test
    public void testContact_Update() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);
        String type = "CONTACT";
        String route = "Update";
        String name = "testContact";

        exchange.setProperty("runType", type);
        exchange.setProperty("routingType", route);
        exchange.setProperty("entityName", name);
        exchange.setProperty("conSuccessList", new ArrayList<>());
        updateSuccessListProcessor.process(exchange);
        ArrayList<String> test = exchange.getProperty("conSuccessList", ArrayList.class);
        assertEquals(1, test.size());
        assertEquals("testContact-Update", test.get(0));
    }

    @Test
    public void testContact_Create() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);
        String type = "CONTACT";
        String route = "Create";
        String name = "testContact";

        exchange.setProperty("runType", type);
        exchange.setProperty("routingType", route);
        exchange.setProperty("entityName", name);
        exchange.setProperty("conSuccessList", new ArrayList<>());
        updateSuccessListProcessor.process(exchange);
        ArrayList<String> test = exchange.getProperty("conSuccessList", ArrayList.class);
        assertEquals(1, test.size());
        assertEquals("testContact-Create", test.get(0));
    }

}

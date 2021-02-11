package com.shanelucyk.camel.Processor_Tests.Summary;

import com.shanelucyk.camel.DemoApplication;
import com.shanelucyk.camel.classes.processor.SummaryProcessor;
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
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = {DemoApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class SummaryProcessorTest {

    @Autowired
    SummaryProcessor summaryProcessor;

    @Test
    public void SummaryTest_Both_All() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<String> opportunitySuccess = new ArrayList<String>();
        opportunitySuccess.add("TestOp1-Update");
        opportunitySuccess.add("TestOp2-Create");
        opportunitySuccess.add("TestOp3-Update");
        opportunitySuccess.add("TestOp4-Create");

        ArrayList<String> contactSuccess = new ArrayList<String>();
        contactSuccess.add("TestContact1-Update");
        contactSuccess.add("TestContact2-Update");
        contactSuccess.add("TestContact3-Create");
        contactSuccess.add("TestContact4-Create");

        ArrayList<String> accountSuccess = new ArrayList<String>();
        accountSuccess.add("TestAccount1-Update");
        accountSuccess.add("TestAccount2-Update");
        accountSuccess.add("TestAccount3-Create");
        accountSuccess.add("TestAccount4-Create");

        exchange.setProperty("oppSuccessList", opportunitySuccess);
        exchange.setProperty("accSuccessList", accountSuccess);
        exchange.setProperty("conSuccessList", contactSuccess);

        summaryProcessor.process(exchange);
        String body =  exchange.getIn().getBody(String.class);
        log.info("Body: {}",body);
        assertEquals("\n" +
                "************************************************************\n" +
                "Synchronization Complete at: X\n" +
                "Accounts Successfully Processed: \n" +
                "    Created: \n" +
                "        TestAccount3\n" +
                "        TestAccount4\n" +
                "    Syncrhonized: \n" +
                "        TestAccount1\n" +
                "        TestAccount2\n" +
                "Contacts Successfully Processed: \n" +
                "    Created: \n" +
                "        TestContact3\n" +
                "        TestContact4\n" +
                "    Syncrhonized: \n" +
                "        TestContact1\n" +
                "        TestContact2\n" +
                "Opportunities Successfully Processed: \n" +
                "    Created: \n" +
                "        TestOp2\n" +
                "        TestOp4\n" +
                "    Syncrhonized: \n" +
                "        TestOp1\n" +
                "        TestOp3\n" +
                "************************************************************\n",body.replaceAll("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d.\\d\\d\\d", "X"));
    }




    @Test
    public void SummaryTest_CreateOnly_All() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<String> opportunitySuccess = new ArrayList<String>();
        opportunitySuccess.add("TestOp1-Create");
        opportunitySuccess.add("TestOp2-Create");

        ArrayList<String> contactSuccess = new ArrayList<String>();

        contactSuccess.add("TestContact1-Create");
        contactSuccess.add("TestContact2-Create");

        ArrayList<String> accountSuccess = new ArrayList<String>();

        accountSuccess.add("TestAccount1-Create");
        accountSuccess.add("TestAccount2-Create");

        exchange.setProperty("oppSuccessList", opportunitySuccess);
        exchange.setProperty("accSuccessList", accountSuccess);
        exchange.setProperty("conSuccessList", contactSuccess);

        summaryProcessor.process(exchange);
        String body = exchange.getIn().getBody(String.class);
        log.info("Body: {}", body);
        assertEquals("\n" +
                "************************************************************\n" +
                "Synchronization Complete at: X\n" +
                "Accounts Successfully Processed: \n" +
                "    Created: \n" +
                "        TestAccount1\n" +
                "        TestAccount2\n" +
                "    Syncrhonized: No Successful Creates\n" +
                "Contacts Successfully Processed: \n" +
                "    Created: \n" +
                "        TestContact1\n" +
                "        TestContact2\n" +
                "    Syncrhonized: No Successful Creates\n" +
                "Opportunities Successfully Processed: \n" +
                "    Created: \n" +
                "        TestOp1\n" +
                "        TestOp2\n" +
                "    Syncrhonized: No Successful Creates\n" +
                "************************************************************\n",body.replaceAll("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d.\\d\\d\\d", "X"));
    }

    @Test
    public void SummaryTest_UpdateOnly_All() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<String> opportunitySuccess = new ArrayList<String>();
        opportunitySuccess.add("TestOp1-Update");
        opportunitySuccess.add("TestOp2-Update");

        ArrayList<String> contactSuccess = new ArrayList<String>();

        contactSuccess.add("TestContact1-Update");
        contactSuccess.add("TestContact2-Update");

        ArrayList<String> accountSuccess = new ArrayList<String>();

        accountSuccess.add("TestAccount1-Update");
        accountSuccess.add("TestAccount2-Update");

        exchange.setProperty("oppSuccessList", opportunitySuccess);
        exchange.setProperty("accSuccessList", accountSuccess);
        exchange.setProperty("conSuccessList", contactSuccess);

        summaryProcessor.process(exchange);
        String body = exchange.getIn().getBody(String.class);
        log.info("Body: {}", body);
        assertEquals("\n" +
                "************************************************************\n" +
                "Synchronization Complete at: X\n" +
                "Accounts Successfully Processed: \n" +
                "    Created: No Successful Creates\n" +
                "    Syncrhonized: \n" +
                "        TestAccount1\n" +
                "        TestAccount2\n" +
                "Contacts Successfully Processed: \n" +
                "    Created: No Successful Creates\n" +
                "    Syncrhonized: \n" +
                "        TestContact1\n" +
                "        TestContact2\n" +
                "Opportunities Successfully Processed: \n" +
                "    Created: No Successful Creates\n" +
                "    Syncrhonized: \n" +
                "        TestOp1\n" +
                "        TestOp2\n" +
                "************************************************************\n",body.replaceAll("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d.\\d\\d\\d", "X"));
    }


    @Test
    public void SummaryTest_Nothing_All() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<String> opportunitySuccess = new ArrayList<String>();
        ArrayList<String> contactSuccess = new ArrayList<String>();
        ArrayList<String> accountSuccess = new ArrayList<String>();

        exchange.setProperty("oppSuccessList", opportunitySuccess);
        exchange.setProperty("accSuccessList", accountSuccess);
        exchange.setProperty("conSuccessList", contactSuccess);

        summaryProcessor.process(exchange);
        String body = exchange.getIn().getBody(String.class);
        log.info("Body: {}", body);
        assertEquals("\n" +
                "************************************************************\n" +
                "Synchronization Complete at: X\n" +
                "Accounts Successfully Processed: \n" +
                "    Created: No Successful Creates\n" +
                "    Syncrhonized: No Successful Creates\n" +
                "Contacts Successfully Processed: \n" +
                "    Created: No Successful Creates\n" +
                "    Syncrhonized: No Successful Creates\n" +
                "Opportunities Successfully Processed: \n" +
                "    Created: No Successful Creates\n" +
                "    Syncrhonized: No Successful Creates\n" +
                "************************************************************\n", body.replaceAll("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d.\\d\\d\\d", "X"));
    }
}

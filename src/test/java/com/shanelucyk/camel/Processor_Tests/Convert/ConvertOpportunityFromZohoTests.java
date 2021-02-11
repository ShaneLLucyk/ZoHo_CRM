package com.shanelucyk.camel.Processor_Tests.Convert;

import com.shanelucyk.camel.DemoApplication;
import com.shanelucyk.camel.classes.processor.ConvertZohoOpportunityProcessor;
import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.salesforce.dto.Opportunity;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = {DemoApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ConvertOpportunityFromZohoTests {

    @Autowired
    ConvertZohoOpportunityProcessor convertZohoOpportunityProcessor;

    @Test
    public void testOpportunityConvert_Create_noError() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateOpportunity("",3462057000000304137l , 112233445566778899l));
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("3462057000000304137","testAccountId");
        exchange.setProperty("processList", test);
        exchange.setProperty("accountZMap", accountMap);
        exchange.setProperty("updateFlag",false);
        convertZohoOpportunityProcessor.process(exchange);
        ArrayList<Opportunity> convertedOpportunitys = exchange.getProperty("processList", ArrayList.class);
        ArrayList<String> err = exchange.getProperty("errorList", ArrayList.class);
        assertEquals(convertedOpportunitys.size(), 1);
        Opportunity testOpportunity = convertedOpportunitys.get(0);

        assertEquals(err.size(), 0);

        assertEquals("testAccountId", testOpportunity.getAccountId());
        assertEquals("112233445566778899", testOpportunity.getZohoOpportunityID__c());
    }

    @Test
    public void testOpportunityConvert_Create_Error() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        ZCRMRecord c = generateOpportunity("",3462057000000304137l , 112233445566778899l);
        c.setFieldValue("Closing_Date", "nully");
        test.add(c);
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("3462057000000304137","testAccountId");
        HashMap<String, String> OpportunityMap = new HashMap<>();
        exchange.setProperty("processList", test);
        exchange.setProperty("accountZMap", accountMap);
        exchange.setProperty("updateFlag",false);
        convertZohoOpportunityProcessor.process(exchange);

        ArrayList<Opportunity> convertedOpportunitys = exchange.getProperty("processList", ArrayList.class);
        ArrayList<String> err = exchange.getProperty("errorList", ArrayList.class);

        assertEquals(convertedOpportunitys.size(), 0);
        assertEquals(err.size(), 1);
        String errMessage = err.get(0);
        assertTrue(errMessage.toUpperCase().contains("Opportunity".toUpperCase()));
        assertTrue(errMessage.toUpperCase().contains(("testDeal_Name").toUpperCase()));
    }


    @Test
    public void testOpportunityConvert_Update_noError() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateOpportunity("",3462057000000304137l , 112233445566778899l));
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("3462057000000304137","testAccountId");
        HashMap<String, String> OpportunityMap = new HashMap<>();
        OpportunityMap.put("112233445566778899", "testOpportunityId");
        exchange.setProperty("processList", test);
        exchange.setProperty("accountZMap", accountMap);
        exchange.setProperty("opportunityZMap", OpportunityMap);
        exchange.setProperty("updateFlag", true);
        convertZohoOpportunityProcessor.process(exchange);
        ArrayList<Opportunity> convertedOpportunitys = exchange.getProperty("processList", ArrayList.class);
        ArrayList<String> err = exchange.getProperty("errorList", ArrayList.class);
        assertEquals(convertedOpportunitys.size(), 1);
        assertEquals(err.size(), 0);

        Opportunity testOpportunity = convertedOpportunitys.get(0);
        assertEquals("testAccountId", testOpportunity.getAccountId());
        assertEquals("testOpportunityId", testOpportunity.getId());
        assertEquals("112233445566778899", testOpportunity.getZohoOpportunityID__c());
        log.info("After Conversion");
    }




    public ZCRMRecord generateOpportunity(String modifier, Long accountID, Long OpportunityID){
        ZCRMRecord testOpportunity = new ZCRMRecord("Opportunity");

        testOpportunity.setFieldValue("Description", "TestDescription");
        testOpportunity.setFieldValue("Campaign_Source", null);
        testOpportunity.setFieldValue("Closing_Date", "2021-01-01");
        testOpportunity.setFieldValue("Deal_Name", "TestDeal_Name");
        testOpportunity.setFieldValue("Stage", "Qualification");
        testOpportunity.setFieldValue("Amount", 115);
        testOpportunity.setFieldValue("Probability", 11);
        testOpportunity.setFieldValue("Next_Step", "TestNext_Step");
        testOpportunity.setFieldValue("Contact_Name", "TestContact_Name");
        testOpportunity.setFieldValue("Type", "Existing Business");
        testOpportunity.setFieldValue("Lead_Source", "Web Download");
        testOpportunity.setFieldValue("Tag", "TestTag");



        ZCRMRecord acc = new ZCRMRecord("Account");
        acc.setEntityId(accountID);

        testOpportunity.setFieldValue("Account_Name", acc);

        testOpportunity.setEntityId(OpportunityID);
        return testOpportunity;
    }
}

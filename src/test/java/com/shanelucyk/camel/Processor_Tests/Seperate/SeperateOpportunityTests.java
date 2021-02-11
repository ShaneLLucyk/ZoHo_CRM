package com.shanelucyk.camel.Processor_Tests.Seperate;

import com.shanelucyk.camel.DemoApplication;
import com.shanelucyk.camel.classes.processor.SeperateAccountProcessor;
import com.shanelucyk.camel.classes.processor.SeperateOpportunityProcessor;
import com.zoho.crm.library.crud.ZCRMRecord;
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
import java.util.HashMap;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = {DemoApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class SeperateOpportunityTests {

    @Autowired
    SeperateOpportunityProcessor seperateOpportunityProcessor;


    @Test
    public void testOpportunitySeperate_Update() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateOpportunity("A",3462057000000304137l , 112233445566778899l));
        test.add(generateOpportunity("B",3462057000000304138l , 112233445566778898l));
        test.add(generateOpportunity("C",3462057000000304139l , 112233445566778897l));
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("112233445566778899","testAccountId");
        accountMap.put("112233445566778898","testAccountId2");
        accountMap.put("112233445566778897","testAccountId3");
        exchange.setProperty("Opportunities", test);
        exchange.setProperty("opportunityZMap", accountMap);
        seperateOpportunityProcessor.process(exchange);
        ArrayList<ZCRMRecord> create = exchange.getProperty("createList", ArrayList.class);;
        ArrayList<ZCRMRecord> update = exchange.getProperty("updateList", ArrayList.class);;
        assertEquals(0, create.size());
        assertEquals(3, update.size());
    }


    @Test
    public void testOpportunitySeperate_Create() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateOpportunity("A",3462057000000304137l , 112233445566778899l));
        test.add(generateOpportunity("B",3462057000000304138l , 112233445566778898l));
        test.add(generateOpportunity("C",3462057000000304139l , 112233445566778897l));
        HashMap<String, String> accountMap = new HashMap<>();
//        accountMap.put("3462057000000304137","testAccountId");
//        accountMap.put("3462057000000304138","testAccountId2");
//        accountMap.put("3462057000000304139","testAccountId3");
        exchange.setProperty("Opportunities", test);
        exchange.setProperty("opportunityZMap", accountMap);
        seperateOpportunityProcessor.process(exchange);
        ArrayList<ZCRMRecord> create = exchange.getProperty("createList", ArrayList.class);;
        ArrayList<ZCRMRecord> update = exchange.getProperty("updateList", ArrayList.class);;
        assertEquals(3, create.size());
        assertEquals(0, update.size());
    }


    @Test
    public void testOpportunitySeperate_Mixed() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateOpportunity("A",3462057000000304137l , 112233445566778899l));
        test.add(generateOpportunity("B",3462057000000304138l , 112233445566778898l));
        test.add(generateOpportunity("C",3462057000000304139l , 112233445566778897l));
        HashMap<String, String> opportunityMap = new HashMap<>();
//        accountMap.put("3462057000000304137","testAccountId");
        opportunityMap.put("112233445566778899","testAccountId2");
//        accountMap.put("3462057000000304139","testAccountId3");
        exchange.setProperty("Opportunities", test);
        exchange.setProperty("opportunityZMap", opportunityMap);
        seperateOpportunityProcessor.process(exchange);
        ArrayList<ZCRMRecord> create = exchange.getProperty("createList", ArrayList.class);;
        ArrayList<ZCRMRecord> update = exchange.getProperty("updateList", ArrayList.class);;
        assertEquals(2, create.size());
        assertEquals(1, update.size());
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

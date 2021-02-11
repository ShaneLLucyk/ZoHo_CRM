package com.shanelucyk.camel.Processor_Tests.Seperate;

import com.shanelucyk.camel.DemoApplication;
import com.shanelucyk.camel.classes.processor.SeperateAccountProcessor;
import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.salesforce.dto.Account;
import org.apache.camel.salesforce.dto.Opportunity;
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
public class SeperateAccountTests {

    @Autowired
    SeperateAccountProcessor seperateAccountProcessor;


    @Test
    public void testAccountSeperate_Update() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateAccount("A",3462057000000304137l , 112233445566778899l));
        test.add(generateAccount("B",3462057000000304138l , 112233445566778899l));
        test.add(generateAccount("C",3462057000000304139l , 112233445566778899l));
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("3462057000000304137","testAccountId");
        accountMap.put("3462057000000304138","testAccountId2");
        accountMap.put("3462057000000304139","testAccountId3");
        exchange.setProperty("Accounts", test);
        exchange.setProperty("accountZMap", accountMap);
        seperateAccountProcessor.process(exchange);
        ArrayList<ZCRMRecord> create = exchange.getProperty("createList", ArrayList.class);;
        ArrayList<ZCRMRecord> update = exchange.getProperty("updateList", ArrayList.class);;
        assertEquals(0, create.size());
        assertEquals(3, update.size());
    }


    @Test
    public void testAccountSeperate_Create() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateAccount("A",3462057000000304137l , 112233445566778899l));
        test.add(generateAccount("B",3462057000000304138l , 112233445566778899l));
        test.add(generateAccount("C",3462057000000304139l , 112233445566778899l));
        HashMap<String, String> accountMap = new HashMap<>();
//        accountMap.put("3462057000000304137","testAccountId");
//        accountMap.put("3462057000000304138","testAccountId2");
//        accountMap.put("3462057000000304139","testAccountId3");
        exchange.setProperty("Accounts", test);
        exchange.setProperty("accountZMap", accountMap);
        seperateAccountProcessor.process(exchange);
        ArrayList<ZCRMRecord> create = exchange.getProperty("createList", ArrayList.class);;
        ArrayList<ZCRMRecord> update = exchange.getProperty("updateList", ArrayList.class);;
        assertEquals(3, create.size());
        assertEquals(0, update.size());
    }


    @Test
    public void testAccountSeperate_Mixed() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateAccount("A",3462057000000304137l , 112233445566778899l));
        test.add(generateAccount("B",3462057000000304138l , 112233445566778899l));
        test.add(generateAccount("C",3462057000000304139l , 112233445566778899l));
        HashMap<String, String> accountMap = new HashMap<>();
//        accountMap.put("3462057000000304137","testAccountId");
        accountMap.put("3462057000000304138","testAccountId2");
//        accountMap.put("3462057000000304139","testAccountId3");
        exchange.setProperty("Accounts", test);
        exchange.setProperty("accountZMap", accountMap);
        seperateAccountProcessor.process(exchange);
        ArrayList<ZCRMRecord> create = exchange.getProperty("createList", ArrayList.class);;
        ArrayList<ZCRMRecord> update = exchange.getProperty("updateList", ArrayList.class);;
        assertEquals(2, create.size());
        assertEquals(1, update.size());
    }


    public ZCRMRecord generateAccount(String modifier, Long accountID, Long contactID){
        ZCRMRecord testAccount = new ZCRMRecord("Contact");


        testAccount.setFieldValue("Ownership", "Public");
        testAccount.setFieldValue("Description", "testDescription" + modifier);
        testAccount.setFieldValue("Account_Type", "Customer");
        testAccount.setFieldValue("SIC_Code", 123);
        testAccount.setFieldValue("Shipping_State", "testShipping_State" + modifier);
        testAccount.setFieldValue("Website", "testWebsite" + modifier);
        testAccount.setFieldValue("Employees", 777);
        testAccount.setFieldValue("Industry", "Communications");
        testAccount.setFieldValue("Account_Site", "testAccount_Site" + modifier);
        testAccount.setFieldValue("Phone", "testPhone");
        testAccount.setFieldValue("Billing_Country", "testBilling_Country" + modifier);

        testAccount.setFieldValue("Account_Name", "testAccount_Name");
        testAccount.setFieldValue("Account_Number", "testAccount_Number");
        testAccount.setFieldValue("Ticker_Symbol", "testTicker_Symbol");

        testAccount.setFieldValue("Billing_Street", "testBilling_Street" + modifier);
        testAccount.setFieldValue("Billing_Code", "testBilling_Code" + modifier);

        testAccount.setFieldValue("Shipping_City", "testShipping_City" + modifier);
        testAccount.setFieldValue("Shipping_Country", "testShipping_Country" + modifier);
        testAccount.setFieldValue("Shipping_Code", "testShipping_Code" + modifier);

        testAccount.setFieldValue("Billing_City", "testBilling_City" + modifier);
        testAccount.setFieldValue("Billing_State", "testBilling_State" + modifier);

        testAccount.setFieldValue("Tag", "testTag" + modifier);
        testAccount.setFieldValue("Fax", "testFax" + modifier);
        testAccount.setFieldValue("Annual_Revenue", 85000);
        testAccount.setFieldValue("Shipping_Street", "testShipping_Street" + modifier);
        testAccount.setEntityId(accountID);
        return testAccount;
    }

}

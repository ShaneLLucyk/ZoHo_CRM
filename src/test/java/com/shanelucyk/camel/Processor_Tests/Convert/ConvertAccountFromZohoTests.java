package com.shanelucyk.camel.Processor_Tests.Convert;

import com.shanelucyk.camel.DemoApplication;
import com.shanelucyk.camel.classes.processor.ConvertZohoAccountProcessor;
import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.salesforce.dto.Account;
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
public class ConvertAccountFromZohoTests {

    @Autowired
    ConvertZohoAccountProcessor convertZohoAccountProcessor;

    @Test
    public void testAccountConvert_Create_noError() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateAccount("",3462057000000304137l , 112233445566778899l));
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("3462057000000304137","testAccountId");
        HashMap<String, String> contactMap = new HashMap<>();
        exchange.setProperty("processList", test);
        exchange.setProperty("accountZMap", accountMap);
        exchange.setProperty("updateFlag",false);
        convertZohoAccountProcessor.process(exchange);
        ArrayList<Account> convertedContacts = exchange.getProperty("processList", ArrayList.class);
        ArrayList<String> err = exchange.getProperty("errorList", ArrayList.class);
        Account testContact = convertedContacts.get(0);

        assertEquals(convertedContacts.size(), 1);
        assertEquals(err.size(), 0);

        assertEquals("3462057000000304137", testContact.getZohoAccountID__c());
    }


    @Test
    public void testAccountConvert_Create_Error() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        ZCRMRecord a = generateAccount("",3462057000000304137l , 112233445566778899l);
        a.setFieldValue("Employees", "412fte");
        test.add(a);
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("3462057000000304137","testAccountId");
        HashMap<String, String> contactMap = new HashMap<>();
        exchange.setProperty("processList", test);
        exchange.setProperty("accountZMap", accountMap);
        exchange.setProperty("updateFlag",false);
        convertZohoAccountProcessor.process(exchange);
        ArrayList<Account> convertedContacts = exchange.getProperty("processList", ArrayList.class);
        ArrayList<String> err = exchange.getProperty("errorList", ArrayList.class);
//        Account testContact = convertedContacts.get(0);

        assertEquals(convertedContacts.size(), 0);
        assertEquals(err.size(), 1);
        String errMessage = err.get(0);
        assertTrue(errMessage.toUpperCase().contains("ACCOUNT"));
        assertTrue(errMessage.toUpperCase().contains(("testAccount_Name").toUpperCase()));

    }


    @Test
    public void testAccountConvert_Update_noError() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        ZCRMRecord a = generateAccount("",3462057000000304137l , null);
        test.add(a);
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("3462057000000304137","testAccountId");
        HashMap<String, String> contactMap = new HashMap<>();
        exchange.setProperty("processList", test);
        exchange.setProperty("accountZMap", accountMap);
        exchange.setProperty("updateFlag",true);
        convertZohoAccountProcessor.process(exchange);
        ArrayList<Account> convertedContacts = exchange.getProperty("processList", ArrayList.class);
        ArrayList<String> err = exchange.getProperty("errorList", ArrayList.class);
        Account testContact = convertedContacts.get(0);

        assertEquals(convertedContacts.size(), 1);
        assertEquals(err.size(), 0);
        assertEquals(testContact.getId(), "testAccountId");
        assertEquals("3462057000000304137", testContact.getZohoAccountID__c());
    }






    public ZCRMRecord generateAccount(String modifier, Long accountID, Long contactID){
        ZCRMRecord testAccount = new ZCRMRecord("Contact");


        testAccount.setFieldValue("Ownership", "Public");
        testAccount.setFieldValue("Description", "testDescription");
        testAccount.setFieldValue("Account_Type", "Customer");
        testAccount.setFieldValue("SIC_Code", 123);
        testAccount.setFieldValue("Shipping_State", "testShipping_State");
        testAccount.setFieldValue("Website", "testWebsite");
        testAccount.setFieldValue("Employees", 777);
        testAccount.setFieldValue("Industry", "Communications");
        testAccount.setFieldValue("Account_Site", "testAccount_Site");
        testAccount.setFieldValue("Phone", "testPhone");
        testAccount.setFieldValue("Billing_Country", "testBilling_Country");

        testAccount.setFieldValue("Account_Name", "testAccount_Name");
        testAccount.setFieldValue("Account_Number", "testAccount_Number");
        testAccount.setFieldValue("Ticker_Symbol", "testTicker_Symbol");

        testAccount.setFieldValue("Billing_Street", "testBilling_Street");
        testAccount.setFieldValue("Billing_Code", "testBilling_Code");

        testAccount.setFieldValue("Shipping_City", "testShipping_City");
        testAccount.setFieldValue("Shipping_Country", "testShipping_Country");
        testAccount.setFieldValue("Shipping_Code", "testShipping_Code");

        testAccount.setFieldValue("Billing_City", "testBilling_City");
        testAccount.setFieldValue("Billing_State", "testBilling_State");

        testAccount.setFieldValue("Tag", "testTag");
        testAccount.setFieldValue("Fax", "testFax");
        testAccount.setFieldValue("Annual_Revenue", 85000);
        testAccount.setFieldValue("Shipping_Street", "testShipping_Street");
        testAccount.setEntityId(accountID);
        return testAccount;
    }
}

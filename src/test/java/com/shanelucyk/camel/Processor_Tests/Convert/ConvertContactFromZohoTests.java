package com.shanelucyk.camel.Processor_Tests.Convert;

import com.shanelucyk.camel.DemoApplication;
import com.shanelucyk.camel.classes.processor.ConvertZohoContactProcessor;
import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.salesforce.dto.Contact;
import org.apache.camel.support.DefaultExchange;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = {DemoApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ConvertContactFromZohoTests {



    @Autowired
    ConvertZohoContactProcessor convertZohoContactProcessor;

    @Test
    public void testContactConvert_Create_noError() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateContact("",3462057000000304137l , 112233445566778899l));
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("3462057000000304137","testAccountId");
        HashMap<String, String> contactMap = new HashMap<>();
        exchange.setProperty("processList", test);
        exchange.setProperty("accountZMap", accountMap);
        exchange.setProperty("updateFlag",false);
        convertZohoContactProcessor.process(exchange);
        ArrayList<Contact> convertedContacts = exchange.getProperty("processList", ArrayList.class);
        ArrayList<String> err = exchange.getProperty("errorList", ArrayList.class);
        Contact testContact = convertedContacts.get(0);

        assertEquals(convertedContacts.size(), 1);
        assertEquals(err.size(), 0);

        assertEquals("testAccountId", testContact.getAccountId());
        assertEquals("112233445566778899", testContact.getZohoContactID__c());
    }

    @Test
    public void testContactConvert_Create_Error() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        ZCRMRecord c = generateContact("",3462057000000304137l , 112233445566778899l);
        c.setFieldValue("Date_of_Birth", "nully");
        test.add(c);
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("3462057000000304137","testAccountId");
        HashMap<String, String> contactMap = new HashMap<>();
        exchange.setProperty("processList", test);
        exchange.setProperty("accountZMap", accountMap);
        exchange.setProperty("updateFlag",false);
        convertZohoContactProcessor.process(exchange);

        ArrayList<Contact> convertedContacts = exchange.getProperty("processList", ArrayList.class);
        ArrayList<String> err = exchange.getProperty("errorList", ArrayList.class);

        assertEquals(convertedContacts.size(), 0);
        assertEquals(err.size(), 1);
        String errMessage = err.get(0);
        assertTrue(errMessage.toUpperCase().contains("CONTACT"));
        assertTrue(errMessage.toUpperCase().contains(("testFirst_Name testLast_Name").toUpperCase()));
    }


    @Test
    public void testContactConvert_Update_noError() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateContact("",3462057000000304137l , 112233445566778899l));
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("3462057000000304137","testAccountId");
        HashMap<String, String> contactMap = new HashMap<>();
        contactMap.put("112233445566778899", "testContactId");


        exchange.setProperty("processList", test);
        exchange.setProperty("accountZMap", accountMap);
        exchange.setProperty("contactZMap", contactMap);
        exchange.setProperty("updateFlag", true);
        convertZohoContactProcessor.process(exchange);
        ArrayList<Contact> convertedContacts = exchange.getProperty("processList", ArrayList.class);
        ArrayList<String> err = exchange.getProperty("errorList", ArrayList.class);
        assertEquals(convertedContacts.size(), 1);
        assertEquals(err.size(), 0);

        Contact testContact = convertedContacts.get(0);
        assertEquals("testAccountId", testContact.getAccountId());
        assertEquals("testContactId", testContact.getId());
        assertEquals("112233445566778899", testContact.getZohoContactID__c());
        log.info("After Conversion");
    }




    public ZCRMRecord generateContact(String modifier, Long accountID, Long contactID){
        ZCRMRecord testContact = new ZCRMRecord("Contact");

        testContact.setFieldValue("Email", "testEmail");
        testContact.setFieldValue("Other_Phone", "testOther_Phone");
        testContact.setFieldValue("Mailing_State", "testMailing_State");
        testContact.setFieldValue("Other_State", "testOther_State");
        testContact.setFieldValue("Other_Country", "testOther_Country");
        testContact.setFieldValue("Department", "testDepartment");
        testContact.setFieldValue("Assistant", "testAssistant");
        testContact.setFieldValue("Mailing_Country", "testMailing_Country");
        testContact.setFieldValue("Other_City", "testOther_City");
        testContact.setFieldValue("Home_Phone", "testHome_Phone");
        testContact.setFieldValue("Secondary_Email", "testSecondary_Email");
        testContact.setFieldValue("Description", "testDescription");
        testContact.setFieldValue("Vendor_Name", "testVendor_Name");
        testContact.setFieldValue("Mailing_Zip", "testMailing_Zip");
        testContact.setFieldValue("Other_Zip", "testOther_Zip");
        testContact.setFieldValue("Mailing_Street", "testMailing_Street");
        testContact.setFieldValue("Salutation", "Mr.");
        testContact.setFieldValue("First_Name", "testFirst_Name");
        testContact.setFieldValue("Asst_Phone", "testAsst_Phone");
        testContact.setFieldValue("Record_Image", "testRecord_Image");
        testContact.setFieldValue("Modified_By", "testModified_By");
        testContact.setFieldValue("Skype_ID", "testSkype_ID");
        testContact.setFieldValue("Phone", "testPhone");

        ZCRMRecord acc = new ZCRMRecord("Account");
        acc.setEntityId(accountID);

        testContact.setFieldValue("Account_Name", acc);
        testContact.setFieldValue("Date_of_Birth", "2020-01-01");
        testContact.setFieldValue("Mailing_City", "testMailing_City");
        testContact.setFieldValue("Title", "testTitle");
        testContact.setFieldValue("Other_Street", "testOther_Street");
        testContact.setFieldValue("Mobile", "testMobile");
        testContact.setFieldValue("Last_Name", "testLast_Name");
        testContact.setFieldValue("Full_Name", "testFirst_Name testLast_Name");
        testContact.setFieldValue("Tag", "testTag");
        testContact.setFieldValue("Fax", "testFax");
        testContact.setEntityId(contactID);
        return testContact;
    }
}

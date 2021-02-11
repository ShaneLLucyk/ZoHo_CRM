package com.shanelucyk.camel.Processor_Tests.Seperate;

import com.shanelucyk.camel.DemoApplication;
import com.shanelucyk.camel.classes.processor.SeperateAccountProcessor;
import com.shanelucyk.camel.classes.processor.SeperateContactProcessor;
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
public class SeperateContactTests {

    @Autowired
    SeperateContactProcessor seperateContactProcessor;


    @Test
    public void testContactSeperate_Update() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateContact("A",3462057000000304137l , 112233445566778899l));
        test.add(generateContact("B",3462057000000304138l , 112233445566778898l));
        test.add(generateContact("C",3462057000000304139l , 112233445566778897l));
        HashMap<String, String> contactZMap = new HashMap<>();
        contactZMap.put("112233445566778899","testAccountId");
        contactZMap.put("112233445566778898","testAccountId2");
        contactZMap.put("112233445566778897","testAccountId3");
        exchange.setProperty("Contacts", test);
        exchange.setProperty("contactZMap", contactZMap);
        seperateContactProcessor.process(exchange);
        ArrayList<ZCRMRecord> create = exchange.getProperty("createList", ArrayList.class);;
        ArrayList<ZCRMRecord> update = exchange.getProperty("updateList", ArrayList.class);;
        assertEquals(0, create.size());
        assertEquals(3, update.size());
    }


    @Test
    public void testContactSeperate_Create() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateContact("A",3462057000000304137l , 112233445566778899l));
        test.add(generateContact("B",3462057000000304138l , 112233445566778899l));
        test.add(generateContact("C",3462057000000304139l , 112233445566778899l));
        HashMap<String, String> contactZMap = new HashMap<>();

        exchange.setProperty("Contacts", test);
        exchange.setProperty("contactZMap", contactZMap);
        seperateContactProcessor.process(exchange);
        ArrayList<ZCRMRecord> create = exchange.getProperty("createList", ArrayList.class);;
        ArrayList<ZCRMRecord> update = exchange.getProperty("updateList", ArrayList.class);;
        assertEquals(3, create.size());
        assertEquals(0, update.size());
    }


    @Test
    public void testContactSeperate_Mixed() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ArrayList<ZCRMRecord> test = new ArrayList<>();
        test.add(generateContact("A",3462057000000304137l , 112233445566778899l));
        test.add(generateContact("B",3462057000000304138l , 112233445566778898l));
        test.add(generateContact("C",3462057000000304139l , 112233445566778897l));
        HashMap<String, String> contactZMap = new HashMap<>();
//        accountMap.put("3462057000000304137","testAccountId");
        contactZMap.put("112233445566778899","testAccountId2");
//        accountMap.put("3462057000000304139","testAccountId3");
        exchange.setProperty("Contacts", test);
        exchange.setProperty("contactZMap", contactZMap);
        seperateContactProcessor.process(exchange);
        ArrayList<ZCRMRecord> create = exchange.getProperty("createList", ArrayList.class);;
        ArrayList<ZCRMRecord> update = exchange.getProperty("updateList", ArrayList.class);;
        assertEquals(2, create.size());
        assertEquals(1, update.size());
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

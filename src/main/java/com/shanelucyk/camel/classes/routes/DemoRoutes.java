package com.shanelucyk.camel.classes.routes;

import com.shanelucyk.camel.classes.context.SalesforceContext;
import com.shanelucyk.camel.classes.processor.*;
import com.shanelucyk.camel.classes.config.ZohoConfig;
import com.zoho.crm.library.api.response.BulkAPIResponse;
import com.zoho.crm.library.crud.ZCRMModule;
import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.salesforce.api.dto.SObjectField;
import org.apache.camel.component.salesforce.api.utils.QueryHelper;
import org.apache.camel.salesforce.dto.Account;
import org.apache.camel.salesforce.dto.Contact;
import org.apache.camel.salesforce.dto.QueryRecordsAccount;
import org.apache.camel.salesforce.dto.QueryRecordsContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.apache.camel.component.salesforce.SalesforceEndpointConfig.*;

@Component("demoRoutes")
@Slf4j
public class DemoRoutes extends RouteBuilder {

    @Autowired
    SalesforceContext salesforceContext;

    @Autowired
    ZohoConfig zohoConfig;

    @Autowired
    RetrieveZohoContactsProcessor retrieveZohoContactsProcessor;

    @Autowired
    GetSalesforceContactEmailsProcessor getSalesforceContactEmailsProcessor;

    @Autowired
    SeperateContactProcessor seperateContactProcessor;

    @Autowired
    PrepContactCreateProcessor prepContactCreateProcessor;

    @Autowired
    BuildBatchProcessor buildBatchProcessor;

    @Autowired
    BuildAccountNameIdMapProcessor buildAccountNameIdMapProcessor;

    @Autowired
    RetrieveZohoAccountProcessor retrieveZohoAccountProcessor;

    @Autowired
    GetSalesforceAccountNamesProcessor getSalesforceAccountNamesProcessor;

    @Autowired
    SeperateAccountProcessor seperateAccountProcessor;

    @Override
    public void configure() throws Exception {
        log.info("Starting RestRoutes");
        CamelContext camelContext = getContext();
        camelContext.setUseMDCLogging(true);
        camelContext.setUseBreadcrumb(true);
        camelContext.setStreamCaching(true);

//        from("quartz://myGroup/testScheduler?trigger.repeatInterval=1&trigger.repeatCount=0&startDelayedSeconds=5")
//        .to("direct:getAllSFContacts");


        from("direct:createContactTest").routeId("testContactCreate") //To Create an Contact. Required Field: Last Name
                .log("Before Create Contact")
                .process(exchange -> {
                    Contact contact = new Contact();
                    contact.setFirstName("Shane");
                    contact.setLastName("Test2");
                    contact.setDescription("123456789" + "_" + "Description");
//                    contact.setAccountId("123456789");
                    exchange.getIn().setBody(contact);
                })
                .to("salesforce:createSObject")
                .log("After Contact Creation");


        from("direct:getAllSFContacts")
                .process(exchange -> {
                    String allCustomFieldsQuery = QueryHelper.queryToFetchFilteredFieldsOf(new Account(), SObjectField::isCustom);
                    log.info("AllCustomFieldsQueryString: {}", allCustomFieldsQuery);
                    String queryString = "SELECT Name FROM Contact";
                    exchange.setProperty("queryString", queryString);
                })
                .setHeader("sObjectQuery", simple("SELECT Name FROM Contact"))
                .to("salesforce:query?sObjectQuery=&sObjectClass=" + QueryRecordsContact.class.getName())
                .log("${body}")
                .process(exchange -> {
                    QueryRecordsContact contacts = exchange.getIn().getBody(QueryRecordsContact.class);
                    log.info(contacts.getRecords().get(0).getName());
                });

        from("direct:createAccountTest").routeId("Test") //To Create an Account. Required Field: NAME, Desc, Number
                .log("Before Create Object")
                .to("salesforce:createSObject")
                .log("After Account Creation")
        ;



//        from("quartz://myGroup/contactScheduler?cron=0+50+*+?+*+MON-FRI")
        from("quartz://myGroup/contactScheduler?trigger.repeatInterval=1&trigger.repeatCount=0&startDelayedSeconds=5")
                .routeId("QuartzAccountSchedulerRoute").log("QuartsScheduler Triggered")
                //Accounts Processing:
                .process(retrieveZohoAccountProcessor)
                .log("Zoho Accounts Retrieved")
                .to("direct:getSFAccountNames")
                .process(seperateAccountProcessor)
//                .log("Zoho Contacts Retrieved")
//                .to("direct:getSalesforceContactEmails")
//                .to("salesforce:createSObject")
                .log("After CAccount")
        ;

//                //Contacts Processing:
//                .process(retrieveZohoContactsProcessor)
//                .log("Zoho Contacts Retrieved")
//                .to("direct:getSalesforceContactEmails")
//                .to("direct:getAccountNameIdMap")
//                .process(seperateContactProcessor);
//                .to("direct:createContacts");

        from("direct:getSFAccountNames")
                .setHeader("sObjectQuery", simple("SELECT Name FROM Account"))
                .to("salesforce:query?sObjectQuery=&sObjectClass=" + QueryRecordsAccount.class.getName())
                .process(getSalesforceAccountNamesProcessor);

        from("direct:getSalesforceContactEmails")
                .setHeader("sObjectQuery", simple("SELECT Email FROM Contact"))
                .to("salesforce:query?sObjectQuery=&sObjectClass=" + QueryRecordsContact.class.getName())
                .process(getSalesforceContactEmailsProcessor);

        from("direct:getAccountNameIdMap")
                .setHeader("sObjectQuery", simple("SELECT Id, Name FROM Account"))
                .to("salesforce:query?sObjectQuery=&sObjectClass=" + QueryRecordsAccount.class.getName())
                .process(buildAccountNameIdMapProcessor);





        /*
        * Account Creation Logic
        *
        *
        *
        * */





        from("direct:createContacts").routeId("CreateNewContactRoutes")
                .log("Creating New Contacts")
                .process(prepContactCreateProcessor)
                .log("REPLACE ME WITH ACCOUNT LOOKUP")
//                .setBody(simple("${exchangeProperty.createList}"))
                .loop(simple("${exchangeProperty.createList.size()}")).copy()
                    .log("Index: ${exchangeProperty.CamelLoopIndex}")
                    .setBody(simple("${exchangeProperty.createList[${exchangeProperty.CamelLoopIndex}]}"))
                    .to("direct:createAccountTest")
                .end()
                .log("After Creation")
//                .process(buildBatchProcessor)
//                .to("salesforce:composite-batch?format=JSON")
//                .log("AFter Coimposite Batch")
                .end();

//                .loop().jsonpath("$.length()").copy()
//
//                .process(exchange -> {
//                    ArrayList<ZCRMRecord> createList = exchange.;
//
//                    Account acc = new Account();
//                    acc.setAccountNumber("123456789");
//                    acc.setDescription("TestAccount");
//                    acc.setName("ShanesTestAccount1");
//                    exchange.getIn().setBody(acc);
//                })
//                .to("salesforce:createSObject");


        from("quartz://myGroup/AccountScheduler?cron=0+35+*+?+*+MON-FRI")
//        from("quartz://myGroup/contactScheduler?trigger.repeatInterval=1&trigger.repeatCount=0&startDelayedSeconds=5")
                .routeId("QuartzContactSchedulerRoute").log("QuartsContactScheduler Triggered")
                .process(exchange -> {

                    log.info("After Client Init");
                    ZCRMModule mod = zohoConfig.getZohoClient().getModuleInstance("contacts");
                    log.info("After Module Retrieval");
                    BulkAPIResponse records = mod.getRecords();
                    log.info("After Contact Records Retrieval");
                    log.info(records.getResponseJSON().toString());
                    ArrayList<ZCRMRecord> contacts = (ArrayList<ZCRMRecord>) records.getData();
//        for(ZCRMRecord rec: contacts){
//            String accountNameObj = (String) rec.getFieldValue("Full_Name");
//            log.info("Full Name: {}", accountNameObj);
//        }

                    exchange.setProperty("Contacts", contacts);
                })
                .log("Contacts Retrieved");


        from("direct:logString")
            .convertBodyTo(String.class)
            .to("log:demoRoute?level=INFO&showBody=true&multiline=true");

        from("direct:queryById")
            .to("log:queryById?level=INFO&showBody=true&showHeaders=true&multiline=true")
            .toD("salesforce:getSObject" +
                    "?" + FORMAT + "=" + salesforceContext.getSalesforceResponseFormat() +
                    "&" + SOBJECT_CLASS  + "=${header."+SOBJECT_CLASS+"}" +
                    "&" + SOBJECT_NAME   + "=${header."+SOBJECT_NAME+"}" +
                    "&" + SOBJECT_FIELDS + "=${header."+SOBJECT_FIELDS+"}"
            );

        from("direct:getWithId")
            .to("log:getWithId?level=INFO&showBody=true&showHeaders=true&multiline=true")
            .toD("salesforce:getSObjectWithId" +
                    "?" + FORMAT + "=" + salesforceContext.getSalesforceResponseFormat() +
                    "&" + SOBJECT_NAME + "=${header."+SOBJECT_NAME+"}" +
                    "&" + SOBJECT_EXT_ID_NAME + "=${header."+SOBJECT_EXT_ID_NAME+"}"
            );
    }
}

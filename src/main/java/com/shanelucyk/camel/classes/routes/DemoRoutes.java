package com.shanelucyk.camel.classes.routes;

import com.shanelucyk.camel.classes.context.SalesforceContext;
import com.shanelucyk.camel.classes.processor.*;
import com.shanelucyk.camel.classes.config.ZohoConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.salesforce.dto.QueryRecordsAccount;
import org.apache.camel.salesforce.dto.QueryRecordsContact;
import org.apache.camel.salesforce.dto.QueryRecordsOpportunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component("demoRoutes")
@Slf4j
public class DemoRoutes extends RouteBuilder {
//
//    @Autowired
//    SalesforceContext salesforceContext;
//
//    @Autowired
//    ZohoConfig zohoConfig;


    //ZOHO Retrieval Processors
    @Autowired
    RetrieveZohoContactsProcessor retrieveZohoContactsProcessor;

    @Autowired
    RetrieveZohoAccountProcessor retrieveZohoAccountProcessor;

    @Autowired
    RetrieveZohoOpportunitiesProcessor retrieveZohoOpportunitiesProcessor;


    //List Splitters
    @Autowired
    SeperateAccountProcessor seperateAccountProcessor;

    @Autowired
    SeperateContactProcessor seperateContactProcessor;

    @Autowired
    SeperateOpportunityProcessor seperateOpportunityProcessor;


    //Convert Zoho to Salesforce
    @Autowired
    ConvertZohoContactProcessor convertZohoContactProcessor;

    @Autowired
    ConvertZohoAccountProcessor convertZohoAccountProcessor;

    @Autowired
    ConvertZohoOpportunityProcessor convertZohoOpportunityProcessor;


    //Query Processors
    @Autowired
    BuildAccountZidIdMapProcessor buildAccountZidIdMapProcessor;

    @Autowired
    BuildContactZidIdMapProcessor buildContactZidIdMapProcessor;

    @Autowired
    BuildOpportunityZidIdMapProcessor buildOpportunityZidIdMapProcessor;

    @Override
    public void configure() throws Exception {
        log.info("Starting RestRoutes");
        CamelContext camelContext = getContext();
        camelContext.setUseMDCLogging(true);
        camelContext.setUseBreadcrumb(true);
        camelContext.setStreamCaching(true);


        onException()
                .routeId("Default Error Handler")
                .log("Default Error Handler: ${body}")
                .redeliveryDelay(5000)
                .maximumRedeliveries(3)
                .retryAttemptedLogLevel(LoggingLevel.ERROR)
                .backOffMultiplier(1)
                .maximumRedeliveryDelay(120000)
                .process(exchange -> {
                    Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                    if( e != null){
                        log.warn("Global Exception Handler: {}", e);
                        exchange.getIn().setBody(e.getClass().getSimpleName() + ": " + e.getMessage());
                    }else{
                        log.error("Global Exception Handler: No Exception in Exchange?");
                        exchange.getIn().setBody("Exception handler hit but no exception found in exchange. This is not expected behaviour. See Log Messages.");
                    }

                })
                .to("direct:sendSlackWarningMessage");


//        from("quartz://myGroup/contactScheduler?cron=0+50+*+?+*+MON-FRI")
        from("quartz://myGroup/contactScheduler?trigger.repeatInterval=1&trigger.repeatCount=0&startDelayedSeconds=5")
                .routeId("QuartzAccountSchedulerRoute").log("QuartsScheduler Triggered")
                .to("direct:AccountProcess")
                .to("direct:PropertyCleanup")
                .to("direct:ContactProcess")
                .to("direct:PropertyCleanup")
                .to("direct:OpportunityProcess")

                .choice()
                    .when(simple("${exchangeProperty.errorList.size} != 0"))
                        .to("direct:processIndividualErrors")
                    .otherwise().log("No Errors Found")
                .end()
                .log("End of System Processing");



        from("direct:AccountProcess").routeId("AccountProcessRoute")
                // Accounts Processing
                .process(retrieveZohoAccountProcessor)
                .log("Zoho Accounts Retrieved")
                .to("direct:getAccountZIdIdMap")
                .process(seperateAccountProcessor)
                .choice()
                    .when(simple("${exchangeProperty.createList.size} != 0"))
                        .to("direct:createAccounts")
                    .otherwise().log("No Accounts to Create")
                .end()

                .choice()
                    .when(simple("${exchangeProperty.updateList.size} != 0"))
                        .to("direct:updateAccounts")
                    .otherwise().log("No Accounts to Update")
                .end()
                .setBody(simple("General Processing Successful. Accounts Syncronized from Zoho to Salesforce. Creates: ${exchangeProperty.createList.size}"))
                .to("direct:sendSlackGeneralMessage")
                .log("After Accounts Are Finished");


        from("direct:ContactProcess").routeId("ContactProcessRoute")
                //Contact Processing
                .process(retrieveZohoContactsProcessor)
                .log("Zoho Contacts Retrieved")
                .to("direct:getAccountZIdIdMap")
                .to("direct:getContactZIdIdMap")
                .process(seperateContactProcessor)
                .choice()
                    .when(simple("${exchangeProperty.createList.size} != 0"))
                        .to("direct:createContacts")
                    .otherwise().log("No Contacts to Create")
                .end()

                .choice()
                    .when(simple("${exchangeProperty.updateList.size} != 0"))
                        .to("direct:updateContacts")
                    .otherwise().log("No Contacts to Update")
                .end()
                .log("After Contact Creation");

        from("direct:OpportunityProcess").routeId("OpportunityProcessRoute")
                .log("Processing Opportunities")
                .process(retrieveZohoOpportunitiesProcessor)
                .log("${exchangeProperty.Opportunities.size}")
                .to("direct:getOpportunityZidMap")
                .to("direct:getAccountZIdIdMap")
                .to("direct:getContactZIdIdMap")
                .log("${exchangeProperty.opportunityZMap}")
                .process(seperateOpportunityProcessor)
                .choice()
                    .when(simple("${exchangeProperty.createList.size} != 0"))
                        .to("direct:createOpportunity")
                    .otherwise().log("No Opportunities to Create")
                .end()
                .choice()
                    .when(simple("${exchangeProperty.updateList.size} != 0"))
                        .to("direct:updateOpportunity")
                    .otherwise().log("No Opportunities to Update")
                .end()

                .end();


        //################################################################################
        //###################### Account Logic ###########################################
        //################################################################################
        from("direct:createAccounts").routeId("CreateNewAccountsRoutes")
                .log("Creating New Accounts")
                .setProperty("updateFlag", constant(false))
                .setProperty("processList", simple("${exchangeProperty.createList}"))
                .process(convertZohoAccountProcessor)
                .loop(simple("${exchangeProperty.processList.size()}")).copy()
                    .setBody(simple("${exchangeProperty.processList[${exchangeProperty.CamelLoopIndex}]}"))
                    .to("salesforce:createSObject")
                .end()
            .end();

        from("direct:updateAccounts").routeId("UpdateAccountsRoutes")
                .log("Updating Accounts")
                .setProperty("processList", simple("${exchangeProperty.updateList}"))
                .setProperty("updateFlag", constant(true))
                .process(convertZohoAccountProcessor)
                .loop(simple("${exchangeProperty.processList.size()}")).copy()
                    .setBody(simple("${exchangeProperty.processList[${exchangeProperty.CamelLoopIndex}]}"))
                    .to("salesforce:upsertSObject?sObjectIdName=Id")
                .end()
            .end();


        //################################################################################
        //###################### Contact Logic ###########################################
        //################################################################################
        from("direct:createContacts").routeId("CreateNewContactRoutes")
                .log("Creating New Contacts")
                .setProperty("updateFlag", constant(false))
                .setProperty("processList", simple("${exchangeProperty.createList}"))
                .process(convertZohoContactProcessor)
                .loop(simple("${exchangeProperty.processList.size()}")).copy()
                    .setBody(simple("${exchangeProperty.processList[${exchangeProperty.CamelLoopIndex}]}"))
                    .to("salesforce:createSObject")
                .end()
            .end();

        from("direct:updateContacts").routeId("UpdateContactRoutes")
                .log("Updating Contacts")
                .setProperty("updateFlag", constant(true))
                .setProperty("processList", simple("${exchangeProperty.updateList}"))
                .process(convertZohoContactProcessor)
                .loop(simple("${exchangeProperty.processList.size()}")).copy()
                    .setBody(simple("${exchangeProperty.processList[${exchangeProperty.CamelLoopIndex}]}"))
                    .to("salesforce:upsertSObject?sObjectIdName=Id")
                .end()
            .end();

        //################################################################################
        //###################### Opportunity Logic #######################################
        //################################################################################
        from("direct:createOpportunity").routeId("CreateNewOpportunitiesRoutes")
                .log("Creating New Accounts")
                .setProperty("updateFlag", constant(false))
                .setProperty("processList", simple("${exchangeProperty.createList}"))
                .process(convertZohoOpportunityProcessor)
                .loop(simple("${exchangeProperty.processList.size()}")).copy()
                    .setBody(simple("${exchangeProperty.processList[${exchangeProperty.CamelLoopIndex}]}"))
                    .to("salesforce:createSObject")
                .end()
            .end();

        from("direct:updateOpportunity").routeId("UpdateOpportunitiesRoutes")
                .log("Updating Opportunities")
                .setProperty("processList", simple("${exchangeProperty.updateList}"))
                .setProperty("updateFlag", constant(true))
                .process(convertZohoOpportunityProcessor)
                .loop(simple("${exchangeProperty.processList.size()}")).copy()
                    .setBody(simple("${exchangeProperty.processList[${exchangeProperty.CamelLoopIndex}]}"))
                    .to("salesforce:upsertSObject?sObjectIdName=Id")
                    .end()
                .end();





        //################################################################################
        //###################### Cleanup Logic ###########################################
        //################################################################################
        from("direct:PropertyCleanup").routeId("PropertyCleanupRoute")
                .removeProperty("accountNames")
                .removeProperty("accountMap")
                .removeProperty("zipMap")
                .removeProperty("createList")
                .removeProperty("updateList")
                .removeProperty("processList")
                .setBody(simple(""))
                .log(LoggingLevel.DEBUG, "After Property Cleanup");



        //################################################################################
        //###################### Query Logic #############################################
        //################################################################################
        //Get Zoho ID to Salesforce ID for Accounts
        from("direct:getAccountZIdIdMap")
                .setHeader("sObjectQuery", simple("SELECT Id, ZohoAccountID__c FROM Account where ZohoAccountID__c != null"))
                .to("salesforce:query?sObjectQuery=&sObjectClass=" + QueryRecordsAccount.class.getName())
                .process(buildAccountZidIdMapProcessor);

        //Get Zoho ID to Salesforce ID for Contacts
        from("direct:getContactZIdIdMap")
                .setHeader("sObjectQuery", simple("SELECT ZohoContactID__C , Id from Contact where ZohoContactID__C != null"))
                .to("salesforce:query?sObjectQuery=&sObjectClass=" + QueryRecordsContact.class.getName())
                .process(buildContactZidIdMapProcessor);

        //Get Zoho ID to Salesforce ID for Contacts
        from("direct:getOpportunityZidMap")
                .setHeader("sObjectQuery", simple("SELECT ZohoOpportunityID__C, Id from Opportunity Where ZohoOpportunityID__C != null"))
                .to("salesforce:query?sObjectQuery=&sObjectClass=" + QueryRecordsOpportunity.class.getName())
                .process(buildOpportunityZidIdMapProcessor);



        //################################################################################
        //###################### Slack Logic ############################################
        //################################################################################
        from("direct:processIndividualErrors").routeId("IndividualErrorHandlingRoute")
                .process(exchange -> {
                    String errorBody = "The Following Errors occured during regular processing:";
                   HashMap<String, String> errors = exchange.getProperty("errorList", HashMap.class) ;
                   assert (errors != null);
                   for(Map.Entry<String, String> s: errors.entrySet()){
                       errorBody += "\n" + "- Name=" + s.getKey() + ", " + s.getValue();
                   }
                   log.info("Error Body: {}", errorBody);
                   exchange.getIn().setBody(errorBody);
                })
                .to("direct:sendSlackWarningMessage");

        from("direct:sendSlackGeneralMessage").routeId("SlackGeneralMessageRoute")
                .log("Sending Message to Slack: General: ${body}")
                .setBody(simple("General: ${body}"))
                .to("slack:cameldemo");

        from("direct:sendSlackWarningMessage").routeId("SlackWarningMessageRoute")
                .log("Sending Message to Slack: Warning: ${body}")
                .setBody(simple("Warning: ${body}"))
                .to("slack:warning");



//
//        from("direct:queryById")
//            .to("log:queryById?level=INFO&showBody=true&showHeaders=true&multiline=true")
//            .toD("salesforce:getSObject" +
//                    "?" + FORMAT + "=" + salesforceContext.getSalesforceResponseFormat() +
//                    "&" + SOBJECT_CLASS  + "=${header."+SOBJECT_CLASS+"}" +
//                    "&" + SOBJECT_NAME   + "=${header."+SOBJECT_NAME+"}" +
//                    "&" + SOBJECT_FIELDS + "=${header."+SOBJECT_FIELDS+"}"
//            );
//
//        from("direct:getWithId")
//            .to("log:getWithId?level=INFO&showBody=true&showHeaders=true&multiline=true")
//            .toD("salesforce:getSObjectWithId" +
//                    "?" + FORMAT + "=" + salesforceContext.getSalesforceResponseFormat() +
//                    "&" + SOBJECT_NAME + "=${header."+SOBJECT_NAME+"}" +
//                    "&" + SOBJECT_EXT_ID_NAME + "=${header."+SOBJECT_EXT_ID_NAME+"}"
//            );
    }
}

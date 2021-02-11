package com.shanelucyk.camel.classes.routes;

import com.shanelucyk.camel.classes.config.ApplicationConfig;
import com.shanelucyk.camel.classes.context.SlackContext;
import com.shanelucyk.camel.classes.processor.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.salesforce.api.SalesforceException;
import org.apache.camel.component.salesforce.api.dto.RestError;
import org.apache.camel.salesforce.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component("demoRoutes")
@Slf4j
public class DemoRoutes extends RouteBuilder {

    @Autowired
    ApplicationConfig config;

    @Autowired
    SlackContext slackContext;


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


    //Summary Handling
    @Autowired
    UpdateSuccessListProcessor updateSuccessListProcessor;

    @Autowired
    SummaryProcessor summaryProcessor;

    //Error Handling
    @Autowired
    RoutingExceptionProcessor routingExceptionProcessor;

    @Autowired
    GeneralErrorProcessor generalErrorProcessor;

    @Autowired
    DetermineNameForErrorProcessor determineNameForErrorProcessor;

    @Override
    public void configure() throws Exception {
        log.info("Starting RestRoutes");
        CamelContext camelContext = getContext();
        camelContext.setUseMDCLogging(true);
        camelContext.setUseBreadcrumb(true);
        camelContext.setStreamCaching(true);

        onException()
                .routeId("Default Error Handler")
                .log("Default Error Handler")
                .redeliveryDelay(config.getRetryDelay())
                .maximumRedeliveries(config.getRetries())
                .retryAttemptedLogLevel(LoggingLevel.ERROR)
                .backOffMultiplier(config.getBackoff())
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





//        from("quartz://myGroup/contactScheduler?cron=" + config.getCronSchedule().replace(" ", "+"))
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
                        .process(summaryProcessor)
                        .to("direct:sendSlackGeneralMessage")
                .otherwise().log("No Errors Found")
                        .process(summaryProcessor)
                        .to("direct:sendSlackGeneralMessage")
                .end()
                .log("End of System Processing");



        from("direct:AccountProcess").routeId("AccountProcessRoute")
                // Accounts Processing
                .setProperty("runType", simple("ACCOUNT"))
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
                .log("After Accounts Are Finished");


        from("direct:ContactProcess").routeId("ContactProcessRoute")
                //Contact Processing
                .setProperty("runType", simple("CONTACT"))
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
                //Opportunity Processing
                .setProperty("runType", simple("OPPORTUNITY"))
                .process(retrieveZohoOpportunitiesProcessor)
                .to("direct:getOpportunityZidMap")
                .to("direct:getAccountZIdIdMap")
                .to("direct:getContactZIdIdMap")
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
                    .process(determineNameForErrorProcessor)
                    .to("direct:salesforceCreate")
                .end()
            .end();

        from("direct:updateAccounts").routeId("UpdateAccountsRoutes")
                .log("Updating Accounts")
                .setProperty("processList", simple("${exchangeProperty.updateList}"))
                .setProperty("updateFlag", constant(true))
                .process(convertZohoAccountProcessor)
                .loop(simple("${exchangeProperty.processList.size()}")).copy()
                    .setBody(simple("${exchangeProperty.processList[${exchangeProperty.CamelLoopIndex}]}"))
                    .process(determineNameForErrorProcessor)
                    .to("direct:salesforceUpdate")
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
                    .process(determineNameForErrorProcessor)
                    .to("direct:salesforceCreate")
                .end()
            .end();

        from("direct:updateContacts").routeId("UpdateContactRoutes")
                .log("Updating Contacts")
                .setProperty("updateFlag", constant(true))
                .setProperty("processList", simple("${exchangeProperty.updateList}"))
                .process(convertZohoContactProcessor)
                .loop(simple("${exchangeProperty.processList.size()}")).copy()
                    .setBody(simple("${exchangeProperty.processList[${exchangeProperty.CamelLoopIndex}]}"))
                    .process(determineNameForErrorProcessor)
                    .to("direct:salesforceUpdate")

                .end()
            .end();


        //################################################################################
        //###################### Opportunity Logic #######################################
        //################################################################################
        from("direct:createOpportunity").routeId("CreateNewOpportunitiesRoutes")
                .log("Creating New Opportunities")
                .setProperty("updateFlag", constant(false))
                .setProperty("processList", simple("${exchangeProperty.createList}"))
                .process(convertZohoOpportunityProcessor)
                .loop(simple("${exchangeProperty.processList.size()}")).copy()
                    .setBody(simple("${exchangeProperty.processList[${exchangeProperty.CamelLoopIndex}]}"))
                    .process(determineNameForErrorProcessor)
                    .to("direct:salesforceCreate")
                    .process(updateSuccessListProcessor)
                .end()
            .end();

        from("direct:updateOpportunity").routeId("UpdateOpportunitiesRoutes")
                .log("Updating Opportunities")
                .setProperty("processList", simple("${exchangeProperty.updateList}"))
                .setProperty("updateFlag", constant(true))
                .process(convertZohoOpportunityProcessor)
                .loop(simple("${exchangeProperty.processList.size()}")).copy()
                    .setBody(simple("${exchangeProperty.processList[${exchangeProperty.CamelLoopIndex}]}"))
                    .process(determineNameForErrorProcessor)
                    .to("direct:salesforceUpdate")
                .end()
            .end();



        //################################################################################
        //###################### Salesforce Builders #####################################
        //################################################################################
        from("direct:salesforceCreate").routeId("SalesforceCreateRoute")
                .setProperty("routingType", simple("CREATE"))
                .doTry()
                    .to("salesforce:createSObject")
                    .process(updateSuccessListProcessor)

                .doCatch(Exception.class)
                    .log("Catching Salesforce Create Exception")
                    .setProperty("routingErrorType", simple("CREATE"))
                    .process(routingExceptionProcessor)
                .end();

        from("direct:salesforceUpdate").routeId("SalesforceUpdateRoute")
                .setProperty("routingType", simple("UPDATE"))
                .doTry()
                    .to("salesforce:upsertSObject?sObjectIdName=Id")
                    .process(updateSuccessListProcessor)
                .doCatch(Exception.class)
                    .log("Catching Salesforce Update Exception")
                    .process(routingExceptionProcessor)
                .end();



        //################################################################################
        //###################### Cleanup Logic ###########################################
        //################################################################################
        from("direct:PropertyCleanup").routeId("PropertyCleanupRoute")
                .removeProperty("Contacts")
                .removeProperty("Accounts")
                .removeProperty("Opportunities")
                .removeProperty("accountNames")
                .removeProperty("accountMap")
                .removeProperty("opportunityZMap")
                .removeProperty("contactZMap")
                .removeProperty("accountZMap")
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
                .process(generalErrorProcessor)
                .to("direct:sendSlackWarningMessage");

        from("direct:sendSlackGeneralMessage").routeId("SlackGeneralMessageRoute")
                .log("Sending Message to Slack: General: ${body}")
                .setBody(simple("General: ${body}"))
                .to("slack:" + slackContext.getGeneralChannel());

        from("direct:sendSlackWarningMessage").routeId("SlackWarningMessageRoute")
                .log("Sending Message to Slack: Warning: ${body}")
                .setBody(simple("Warning: ${body}"))
                .to("slack:" + slackContext.getWarningChannel());
    }
}

package com.shanelucyk.camel.classes.processor;

import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.*;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Configuration
public class ConvertZohoOpportunityProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Process: Starting Opportunity Transformation");
        ArrayList<Opportunity> newOpportunities = new ArrayList<>();
        boolean update = exchange.getProperty("updateFlag", Boolean.class);

        HashMap<String, String> accountMap = exchange.getProperty("accountZMap", HashMap.class);


        HashMap<String, String> opportunityMap = null;
        if(update){
            opportunityMap = (exchange.getProperty("opportunityZMap", HashMap.class));
        }

        //Error Handling Map
        HashMap<String, String> errors = exchange.getProperty("errorList", HashMap.class);
        if(errors == null)
            errors = new HashMap<>();

        //Assertions
        assert(opportunityMap != null);
        assert(accountMap != null);
        assert(errors != null);


        for(ZCRMRecord zohoOpportunity: (ArrayList<ZCRMRecord>) exchange.getProperty("processList", ArrayList.class)){
            try{
                log.debug("Opportunity Name: {}", (String) zohoOpportunity.getFieldValue("Deal_Name"));

                //Build Mappings for ZOHO Account to Salesforce Account
                Opportunity newOpportunity = new Opportunity();

                if(update){
                    newOpportunity.setId(opportunityMap.get(zohoOpportunity.getEntityId().toString()));
                }

                //Text Values
                newOpportunity.setZohoOpportunityID__c(zohoOpportunity.getEntityId().toString());
                newOpportunity.setName((String) zohoOpportunity.getFieldValue("Deal_Name"));
                newOpportunity.setDescription((String) zohoOpportunity.getFieldValue("Description"));
                newOpportunity.setNextStep((String) zohoOpportunity.getFieldValue("Next_Step"));



                //Certian Fields can not be UPDATED:
                if(!update) {
                    if (zohoOpportunity.getFieldValue("Expected_Revenue") != null) {
                        int expectedRevenue = (int) zohoOpportunity.getFieldValue("Expected_Revenue");
                        newOpportunity.setExpectedRevenue((double) expectedRevenue);
                    }
                }


                //Set Doubles
                if(zohoOpportunity.getFieldValue("Amount") != null){
                    int amount = (int) zohoOpportunity.getFieldValue("Amount");
                    newOpportunity.setAmount((double) amount);
                }
                if(zohoOpportunity.getFieldValue("Probability") != null){
                    int probability = (int) zohoOpportunity.getFieldValue("Probability");
                    newOpportunity.setProbability((double) probability);
                }

                //Set Dates
                LocalDate dt = LocalDate.parse((String) zohoOpportunity.getFieldValue("Closing_Date"));
                newOpportunity.setCloseDate(dt); //Format yyyy-MM-dd


                //Account Logic:
                if(((ZCRMRecord) zohoOpportunity.getFieldValue("Account_Name")) == null){
                    log.warn("No Account found for Opportunity: {}", zohoOpportunity.getFieldValue("Deal_Name"));
                }else{
                    String accountID = ((ZCRMRecord) zohoOpportunity.getFieldValue("Account_Name")).getEntityId().toString();
                    log.debug("Zoho Account ID for this Opportunity: {}", accountID);
                    log.debug("Salesforce Account ID for this Opportunity: {}", accountMap.get(accountID));
                    newOpportunity.setAccountId(accountMap.get(accountID));
                }

                //Picklists:

                populateType((String) zohoOpportunity.getFieldValue("Type"), newOpportunity);
                populateStage((String) zohoOpportunity.getFieldValue("Stage"), newOpportunity);
                populateLeadSource((String) zohoOpportunity.getFieldValue("Lead_Source"), newOpportunity);
                newOpportunities.add(newOpportunity);
            }catch (Exception e){
                errors.put((String) zohoOpportunity.getFieldValue("Deal_Name"), "Type=Account, Error=" + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }

        log.debug("SizeOf Errors: {}, Size of Process List: {}", errors.size(), newOpportunities.size());
        exchange.setProperty("errorList", errors);
        exchange.setProperty("processList", newOpportunities);
    }


    /*
    * Picklist Population Functions for the following Lists:
    * Type
    * Stage
    * Lead Source
    */
    public void populateType(String zohoString, Opportunity newOpportunity){
        if(zohoString != null) {
            Opportunity_TypeEnum opportunityTypeEnum; //Government, Other
            if (zohoString.equalsIgnoreCase("Existing Business")) {
                opportunityTypeEnum = Opportunity_TypeEnum.EXISTING_CUSTOMER___REPLACEMENT;
            } else {
                opportunityTypeEnum = Opportunity_TypeEnum.EXISTING_CUSTOMER___REPLACEMENT;
            }
            newOpportunity.setType(opportunityTypeEnum);
        }else{
            newOpportunity.setType(null);
        }
    }

    public void populateStage(String zohoString, Opportunity newOpportunity){
        if(zohoString != null) {
            Opportunity_StageNameEnum opportunityStageNameEnum = null; //Analyst, Competitor, Distributor, Integrator, Investor, Other, Press, Supplier
            if (zohoString.equalsIgnoreCase("Qualification")) {
                opportunityStageNameEnum = Opportunity_StageNameEnum.QUALIFICATION;
            } else if (zohoString.equalsIgnoreCase("Needs Analysis")) {
                opportunityStageNameEnum = Opportunity_StageNameEnum.NEEDS_ANALYSIS;
            } else if (zohoString.equalsIgnoreCase("Value Proposition")) {
                opportunityStageNameEnum = Opportunity_StageNameEnum.VALUE_PROPOSITION;
            } else if (zohoString.equalsIgnoreCase("Identify Decision Makers")) {
                opportunityStageNameEnum = Opportunity_StageNameEnum.ID__DECISION_MAKERS;
            } else if (zohoString.equalsIgnoreCase("Proposal/Price Quote")) {
                opportunityStageNameEnum = Opportunity_StageNameEnum.PROPOSAL_PRICE_QUOTE;
            } else if (zohoString.equalsIgnoreCase("Negotiation/Review")) {
                opportunityStageNameEnum = Opportunity_StageNameEnum.NEGOTIATION_REVIEW;
            } else if (zohoString.equalsIgnoreCase("Closed Won")) {
                opportunityStageNameEnum = Opportunity_StageNameEnum.CLOSED_WON;
            } else {
                opportunityStageNameEnum = Opportunity_StageNameEnum.CLOSED_LOST;
            }
            newOpportunity.setStageName(opportunityStageNameEnum);
        }else{
            newOpportunity.setStageName(null);
        }
    }

    public void populateLeadSource(String zohoString, Opportunity newOpportunity){

        Opportunity_LeadSourceEnum opportunityLeadSourceEnum = Opportunity_LeadSourceEnum.OTHER; //Analyst, Competitor, Distributor, Integrator, Investor, Other, Press, Supplier
        if(zohoString != null){
            if(zohoString.equalsIgnoreCase("Web Download") || zohoString.equalsIgnoreCase("Web Research")){
                opportunityLeadSourceEnum = Opportunity_LeadSourceEnum.WEB;
            } else if(zohoString.equalsIgnoreCase("Cold Call") ){
                opportunityLeadSourceEnum = Opportunity_LeadSourceEnum.PHONE_INQUIRY;
            } else if(zohoString.equalsIgnoreCase("Employee Referral") || zohoString.equalsIgnoreCase("External Referral") || zohoString.equalsIgnoreCase("Partner")){
                opportunityLeadSourceEnum = Opportunity_LeadSourceEnum.PARTNER_REFERRAL;
            }
            newOpportunity.setLeadSource(opportunityLeadSourceEnum);
        }else{
            newOpportunity.setLeadSource(null);
        }
    }
}

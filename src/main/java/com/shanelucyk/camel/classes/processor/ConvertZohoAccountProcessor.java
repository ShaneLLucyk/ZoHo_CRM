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
public class ConvertZohoAccountProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Process: Starting Contact Transformation");
        ArrayList<Account> newAccounts = new ArrayList<>();
        boolean update = exchange.getProperty("updateFlag", Boolean.class);
        HashMap<String, String> accountMap = null;
        if(update){
            accountMap = (exchange.getProperty("accountZMap", HashMap.class));
        }

        //Error Handling Map
        HashMap<String, String> errors = exchange.getProperty("errorMap", HashMap.class);
        if(errors == null)
            errors = new HashMap<>();

        //Assertions
        assert(accountMap != null);
        assert(errors != null);


        for(ZCRMRecord zohoAccount: (ArrayList<ZCRMRecord>) exchange.getProperty("processList", ArrayList.class)){
            try{
                log.debug("Contact Full Name: {}", (String) zohoAccount.getFieldValue("Account_Name"));

                //Build Mappings for ZOHO Account to Salesforce Account
                Account newAccount = new Account();

                if(update){
                    newAccount.setId(accountMap.get(zohoAccount.getEntityId().toString()));
                }
                newAccount.setZohoAccountID__c(zohoAccount.getEntityId().toString());
                newAccount.setName((String) zohoAccount.getFieldValue("Account_Name"));
                newAccount.setDescription((String) zohoAccount.getFieldValue("Description"));
                newAccount.setPhone((String) zohoAccount.getFieldValue("Phone"));
                newAccount.setFax((String) zohoAccount.getFieldValue("Fax"));
                newAccount.setWebsite((String) zohoAccount.getFieldValue("Website"));
                newAccount.setTickerSymbol((String) zohoAccount.getFieldValue("Ticker_Symbol"));
                newAccount.setSic((String) zohoAccount.getFieldValue("SIC_Code"));
                newAccount.setAccountNumber((String) zohoAccount.getFieldValue("Account_Number"));
                newAccount.setSite((String) zohoAccount.getFieldValue("Account_Site"));
                newAccount.setNumberOfEmployees((int) zohoAccount.getFieldValue("Employees"));
                newAccount.setAnnualRevenue((double) (int) zohoAccount.getFieldValue("Annual_Revenue"));



                newAccount.setBillingStreet((String) zohoAccount.getFieldValue("Billing_Street"));
                newAccount.setBillingCity((String) zohoAccount.getFieldValue("Billing_City"));
                newAccount.setBillingState((String) zohoAccount.getFieldValue("Billing_State"));
                newAccount.setBillingCountry((String) zohoAccount.getFieldValue("Billing_Country"));


                newAccount.setShippingStreet((String) zohoAccount.getFieldValue("Shipping_Street"));
                newAccount.setShippingCity((String) zohoAccount.getFieldValue("Shipping_City"));
                newAccount.setShippingState((String) zohoAccount.getFieldValue("Shipping_State"));
                newAccount.setShippingCountry((String) zohoAccount.getFieldValue("Shipping_Country"));

                //Picklists:
                //Account Type
                String zohoType = (String) zohoAccount.getFieldValue("Account_Type");
                populateType(zohoType, newAccount);

                //Industry
                String zohoIndustry = (String) zohoAccount.getFieldValue("Industry");
                populateIndustry(zohoIndustry, newAccount);

                //Ownership
                String zohoOwnership = (String) zohoAccount.getFieldValue("Ownership");
                populateOwnership(zohoOwnership, newAccount);



                newAccounts.add(newAccount);
            }catch (Exception e){
                errors.put((String) zohoAccount.getFieldValue("Account_Name"), "Type=Account, Error=" + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        log.debug("SizeOf Errors: {}, Size of Process List: {}", errors.size(), newAccounts.size());
        exchange.setProperty("errorList", errors);
        exchange.setProperty("processList", newAccounts);
    }


    /*
    * Picklist Population Functions for the following Lists:
    * Account Type
    * Account Ownership
    * Account Industry
    * */
    public void populateOwnership(String zohoString, Account newAccount){
        Account_OwnershipEnum accountOwnershipEnum = Account_OwnershipEnum.OTHER; //Government, Other
        if(zohoString.equalsIgnoreCase("Public") || zohoString.equalsIgnoreCase("Public Company")){
            accountOwnershipEnum = Account_OwnershipEnum.PUBLIC;
        } else if(zohoString.equalsIgnoreCase("Private") || zohoString.equalsIgnoreCase("Privately Held")){
            accountOwnershipEnum = Account_OwnershipEnum.PRIVATE;
        }else if(zohoString.equalsIgnoreCase("Subsidiary")){
            accountOwnershipEnum = Account_OwnershipEnum.SUBSIDIARY;
        }
        newAccount.setOwnership(accountOwnershipEnum);
    }

    public void populateType(String zohoString, Account newAccount){
        Account_TypeEnum accountTypeEnum = Account_TypeEnum.OTHER; //Analyst, Competitor, Distributor, Integrator, Investor, Other, Press, Supplier
        if(zohoString.equalsIgnoreCase("Prospect")){
            accountTypeEnum = Account_TypeEnum.PROSPECT;
        } else if(zohoString.equalsIgnoreCase("Customer")){
            accountTypeEnum = Account_TypeEnum.CUSTOMER___DIRECT;
        }else if(zohoString.equalsIgnoreCase("Reseller") || zohoString.equalsIgnoreCase("Partner")){
            accountTypeEnum = Account_TypeEnum.CHANNEL_PARTNER___RESELLER;
        }else if(zohoString.equalsIgnoreCase("Vendor")) {
            accountTypeEnum = Account_TypeEnum.CUSTOMER___CHANNEL;
        }
        newAccount.setType(accountTypeEnum);
    }


    public void populateIndustry(String zohoString, Account newAccount){
        Account_IndustryEnum accountIndustryEnum = Account_IndustryEnum.OTHER; //ERP (Enterprise Resource Planning), Large Enterprise, ManagementISV, MSP, Non-management ISV, , Small/Medium Enterprise, Real Estate, Other

        if(zohoString.equalsIgnoreCase("Communications")){
            accountIndustryEnum = Account_IndustryEnum.COMMUNICATIONS;
        } else if(zohoString.equalsIgnoreCase("Consulting")){
            accountIndustryEnum = Account_IndustryEnum.CONSULTING;
        } else if(zohoString.equalsIgnoreCase("Financial Services")){
            accountIndustryEnum = Account_IndustryEnum.FINANCE;
        } else if(zohoString.equalsIgnoreCase("Education")){
            accountIndustryEnum = Account_IndustryEnum.EDUCATION;
        } else if(zohoString.equalsIgnoreCase("Government/Military")){
            accountIndustryEnum = Account_IndustryEnum.GOVERNMENT;
        } else if(zohoString.equalsIgnoreCase("Manufacturing")){
            accountIndustryEnum = Account_IndustryEnum.MANUFACTURING;
        }else if(zohoString.equalsIgnoreCase("Network Equipment Enterprise") || zohoString.equalsIgnoreCase("Optical Networking")
                || zohoString.equalsIgnoreCase("Systems Integrator Networking") || zohoString.equalsIgnoreCase("Technology")){
            accountIndustryEnum = Account_IndustryEnum.TECHNOLOGY;
        }else if(zohoString.equalsIgnoreCase("Storage Service Provider") || zohoString.equalsIgnoreCase("Storage Equipment")
                || zohoString.equalsIgnoreCase("ASP (Application Service Provider)")){
            accountIndustryEnum = Account_IndustryEnum.UTILITIES;
        }else if(zohoString.equalsIgnoreCase("Data/Telcom OEM") || zohoString.equalsIgnoreCase("Service Provider")){
            accountIndustryEnum = Account_IndustryEnum.TELECOMMUNICATIONS;
        }
        newAccount.setIndustry(accountIndustryEnum);
    }
}

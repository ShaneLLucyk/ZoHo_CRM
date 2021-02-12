package com.shanelucyk.camel.classes.processor;

import com.zoho.crm.library.crud.ZCRMRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class ConvertZohoAccountProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        log.debug("Process: Starting Account Transformation");
        ArrayList<Account> newAccounts = new ArrayList<>();
        boolean update = exchange.getProperty("updateFlag", Boolean.class);
        HashMap<String, String> accountMap = null;
        if(update){
            accountMap = (exchange.getProperty("accountZMap", HashMap.class));
        }

        //Error Handling Map
        ArrayList<String> errors = exchange.getProperty("errorList", ArrayList.class);
        if(errors == null)
            errors = new ArrayList<>();

        //Assertions
        assert(errors != null);


        for(ZCRMRecord zohoAccount: (ArrayList<ZCRMRecord>) exchange.getProperty("processList", ArrayList.class)){
            try{
                //Build Mappings for ZOHO Account to Salesforce Account
                Account newAccount = new Account();

                if(update){
                    newAccount.setId(accountMap.get(zohoAccount.getEntityId().toString()));
                }
                newAccount.setZohoAccountID__c(zohoAccount.getEntityId().toString());


                newAccount.setName((String) zohoAccount.getFieldValue("Account_Name"));
                newAccount.setFieldsToNull(generateNullSet());

                newAccount.setDescription((String) zohoAccount.getFieldValue("Description"));
                newAccount.setPhone((String) zohoAccount.getFieldValue("Phone"));
                newAccount.setFax((String) zohoAccount.getFieldValue("Fax"));
                newAccount.setWebsite((String) zohoAccount.getFieldValue("Website"));
                newAccount.setAccountNumber((String) zohoAccount.getFieldValue("Account_Number"));
                newAccount.setSite((String) zohoAccount.getFieldValue("Account_Site"));
                log.debug("Basic Account Information Set");

                newAccount.setTickerSymbol((String) zohoAccount.getFieldValue("Ticker_Symbol"));

                if(zohoAccount.getFieldValue("SIC_Code") != null){
                    newAccount.setSic(String.valueOf((int)zohoAccount.getFieldValue("SIC_Code")));
                }

                if( zohoAccount.getFieldValue("Employees") != null){
                    newAccount.setNumberOfEmployees((int) zohoAccount.getFieldValue("Employees"));
                }
                if( zohoAccount.getFieldValue("Annual_Revenue") != null){
                    newAccount.setAnnualRevenue((double) (int) zohoAccount.getFieldValue("Annual_Revenue"));
                }
                log.debug("Business Information Set");


                newAccount.setBillingStreet((String) zohoAccount.getFieldValue("Billing_Street"));
                newAccount.setBillingCity((String) zohoAccount.getFieldValue("Billing_City"));
                newAccount.setBillingState((String) zohoAccount.getFieldValue("Billing_State"));
                newAccount.setBillingCountry((String) zohoAccount.getFieldValue("Billing_Country"));
                newAccount.setBillingPostalCode((String) zohoAccount.getFieldValue("Billing_Code"));
                log.debug("Billing Information Set");

                newAccount.setShippingStreet((String) zohoAccount.getFieldValue("Shipping_Street"));
                newAccount.setShippingCity((String) zohoAccount.getFieldValue("Shipping_City"));
                newAccount.setShippingState((String) zohoAccount.getFieldValue("Shipping_State"));
                newAccount.setShippingCountry((String) zohoAccount.getFieldValue("Shipping_Country"));
                newAccount.setShippingPostalCode((String) zohoAccount.getFieldValue("Shipping_Code"));
                log.debug("Shipping Information Set");

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
                log.debug("Picklists Set");



                newAccounts.add(newAccount);
            }catch (Exception e){
                log.warn("Record Failure in Account Conversion: {}\n{}",(String) zohoAccount.getFieldValue("Account_Name"), e.getClass().getSimpleName() + ": " + e.getMessage());
                errors.add((String) zohoAccount.getFieldValue("Account_Name") + "_" +  "Type=Account, Error=" + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        log.debug("SizeOf Errors: {}, Size of Process List: {}", errors.size(), newAccounts.size());
        exchange.setProperty("errorList", errors);
        exchange.setProperty("processList", newAccounts);
    }




    public Set generateNullSet(){
        Set<String> s = new HashSet();
        s.add("Description");
        s.add("Fax");
        s.add("Website");
        s.add("TickerSymbol");
        s.add("AccountNumber");
        s.add("Sic");
        s.add("Site");
        s.add("NumberOfEmployees");
        s.add("AnnualRevenue");
        s.add("Phone");
        s.add("Name");

        s.add("BillingStreet");
        s.add("BillingCity");
        s.add("BillingState");
        s.add("BillingCountry");
        s.add("BillingPostalCode");

        s.add("ShippingStreet");
        s.add("ShippingCity");
        s.add("ShippingState");
        s.add("ShippingCountry");
        s.add("ShippingPostalCode");

        s.add("Ownership");
        s.add("Type");
        s.add("Industry");
        return s;
    }

    /*
    * Picklist Population Functions for the following Lists:
    * Account Type
    * Account Ownership
    * Account Industry
    * */
    public void populateOwnership(String zohoString, Account newAccount){
        if(zohoString == null){

        }else{
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
    }

    public void populateType(String zohoString, Account newAccount){
        if(zohoString == null){

        }else{
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
    }


    public void populateIndustry(String zohoString, Account newAccount){
        if(zohoString == null){

        }else{
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
}

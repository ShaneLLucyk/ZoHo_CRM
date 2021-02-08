package com.shanelucyk.camel.classes.context;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@ToString
@Slf4j
public class ZohoContext {
    @Value("${zoho.minloglevel}")
    public String minLogLevel;

    @Value("${zoho.currentuseremail}")
    public String currentUserEmail;

    @Value("${zoho.clientid}")
    public String client_id;

    @Value("${zoho.clientsecret}")
    public String client_secret;

    @Value("${zoho.redirecturi}")
    public String redirect_uri;

    @Value("${zoho.persistence}")
    public String persistenceHandlerClass;

    @Value("${zoho.oathpath}")
    public String oauthTokensFilePath;

    @Value("${zoho.type}")
    public String type;

    @Value("${zoho.accesstype}")
    public String access_type;

    @Value("${zoho.baseurl}")
    public String apiBaseUrl;

    @Value("${zoho.iamurl}")
    public String iamURL;



}

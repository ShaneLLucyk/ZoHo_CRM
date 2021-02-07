package com.shanelucyk.camel.classes.config;

import com.zoho.crm.library.setup.restclient.ZCRMRestClient;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Configuration
@Slf4j
@Getter
public class ZohoConfig {

    private ZCRMRestClient zohoClient = null;

    @PostConstruct
    public void postConstruct() throws Exception {
        HashMap<String, String> zcrmConfigurations = new HashMap <String, String >();
        zcrmConfigurations.put("minLogLevel", "ALL");
        zcrmConfigurations.put("currentUserEmail","shane-lucyk@hotmail.com");
        zcrmConfigurations.put("client_id","1000.TER7TUM7777SFIOA2FBHX2U486PTFE");
        zcrmConfigurations.put("client_secret","57dcb39697fcd0ca73e8fa416d0dca47f90c503b04");
        zcrmConfigurations.put("redirect_uri","https://crm.zoho.com");
        zcrmConfigurations.put("persistence_handler_class","com.zoho.oauth.clientapp.ZohoOAuthFilePersistence");//for database. "com.zoho.oauth.clientapp.ZohoOAuthFilePersistence" for file, user can implement his own persistence and provide the path here
        zcrmConfigurations.put("oauth_tokens_file_path","./src/main/resources/oauthtokens.properties");//optional
        zcrmConfigurations.put("accessType","Development");//Production->www(default), Development->developer, Sandbox->sandbox(optional)
        zcrmConfigurations.put("access_type","offline");//optional
        zcrmConfigurations.put("apiBaseUrl","https://www.zohoapis.com");//optional
        zcrmConfigurations.put("iamURL","https://accounts.zoho.com");//optional
        ZCRMRestClient.initialize(zcrmConfigurations);//for initializing
        log.info("After Client Initialization");

        zohoClient = ZCRMRestClient.getInstance();

        //      LOGIC USED TO GENERATE INITIAL TOKEN FROM CODE. NOT NEEDED LONG TERM BUT DO NOT DELETE!!!
//            ZohoOAuthClient cli = ZohoOAuthClient.getInstance();
//            cli.getAccessToken("shane-lucyk@hotmail.com");
//            log.info("CLI instance Recieved");
//            log.info("Token: {}", cli.getAccessToken("shane-lucyk@hotmail.com"));
//
//
//            String grantToken = "1000.6ba0d85f927d6c7dee4167611d5f4d36.b688973a2a4ce48557d153868e894d2d";
//            ZohoOAuthTokens tokens = cli.generateAccessToken(grantToken);
//            log.info("Tokens Generated'");
//            String accessToken = tokens.getAccessToken();
//            String refreshToken = tokens.getRefreshToken();
//            System.out.println("access token = " + accessToken + " & refresh token = " + refreshToken);

    }


}

package com.shanelucyk.camel.classes.config;

import com.shanelucyk.camel.classes.context.ZohoContext;
import com.zoho.crm.library.setup.restclient.ZCRMRestClient;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Configuration
@Slf4j
@Getter
public class ZohoConfig {

    private ZCRMRestClient zohoClient = null;

    @Autowired
    ZohoContext zohoContext;


    @PostConstruct
    public void postConstruct() throws Exception {
        HashMap<String, String> zcrmConfigurations = new HashMap <String, String >();
        zcrmConfigurations.put("minLogLevel", zohoContext.getMinLogLevel());
        zcrmConfigurations.put("currentUserEmail",zohoContext.getCurrentUserEmail());
        zcrmConfigurations.put("client_id",zohoContext.getClient_id());
        zcrmConfigurations.put("client_secret",zohoContext.getClient_secret());
        zcrmConfigurations.put("redirect_uri",zohoContext.getRedirect_uri());
        zcrmConfigurations.put("persistence_handler_class",zohoContext.getPersistenceHandlerClass());//for database. "com.zoho.oauth.clientapp.ZohoOAuthFilePersistence" for file, user can implement his own persistence and provide the path here
        zcrmConfigurations.put("oauth_tokens_file_path",zohoContext.getOauthTokensFilePath());//optional
        zcrmConfigurations.put("accessType",zohoContext.getType());//Production->www(default), Development->developer, Sandbox->sandbox(optional)
        zcrmConfigurations.put("access_type",zohoContext.getAccess_type());//optional
        zcrmConfigurations.put("apiBaseUrl",zohoContext.getApiBaseUrl());//optional
        zcrmConfigurations.put("iamURL",zohoContext.getIamURL());//optional
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

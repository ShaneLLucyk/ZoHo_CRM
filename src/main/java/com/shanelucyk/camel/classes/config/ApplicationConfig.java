package com.shanelucyk.camel.classes.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.slack.SlackComponent;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@EnableAutoConfiguration
@EnableAsync
public class ApplicationConfig {
    @PostConstruct
    public void postConstruct(){
        log.info("AppConfig postConstruct");
    }

    @Bean(name = "jsonObjectMapper")
    @Primary
    public ObjectMapper jsonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper;
    }

    @Bean(name="slack")
    @Primary
   public SlackComponent slackComponent(){
        SlackComponent component = new SlackComponent();
        component.setWebhookUrl("https://hooks.slack.com/services/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");
        return component;
    }
}

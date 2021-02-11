package com.shanelucyk.camel.classes.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.shanelucyk.camel.classes.context.SlackContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.slack.SlackComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @Autowired
    SlackContext slackContext;

    @Getter
    @Value("${cron.schedule}")
    String cronSchedule;

    @Getter
    @Value("${error.delay}")
    int retryDelay;

    @Getter
    @Value("${error.retries}")
    int retries;

    @Getter
    @Value("${error.backoff}")
    int backoff;


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
        String webHookUrl = slackContext.getBaseURL() + "/" + slackContext.getPATH();
        component.setWebhookUrl(webHookUrl);
        return component;
    }
}

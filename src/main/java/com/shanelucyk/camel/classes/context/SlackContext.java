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
public class SlackContext {
    @Value("${slack.webhook.baseurl}")
    public String baseURL;

    @Value("${slack.webhook.path}")
    public String PATH;

    @Value("${slack.channel.warning}")
    public String warningChannel;

    @Value("${slack.channel.general}")
    public String generalChannel;

}

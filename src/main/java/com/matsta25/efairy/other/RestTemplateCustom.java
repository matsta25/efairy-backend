package com.matsta25.efairy.other;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateCustom {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}

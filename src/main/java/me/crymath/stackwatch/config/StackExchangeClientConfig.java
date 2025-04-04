package me.crymath.stackwatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

/** Configures a RestTemplate instance for accessing the StackExchange API. */
@Configuration
public class StackExchangeClientConfig {

    private final StackExchangeProperties properties;

    public StackExchangeClientConfig(StackExchangeProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RestTemplate stackExchangeRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Set the base URL for the API
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(properties.getApiBaseUrl()));
        // Add the interceptor to append query parameters automatically
        restTemplate.getInterceptors().add(new StackExchangeQueryParamInterceptor(properties));
        return restTemplate;
    }
}

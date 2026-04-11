package se.jg.magme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class AppConfiguration {
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}

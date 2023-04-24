package com.uysnon.codeanalyzer.webui.configuration;

import com.uysnon.codeanalyzer.auth.config.JwtBeansConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import(JwtBeansConfig.class)
public class GeneralConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

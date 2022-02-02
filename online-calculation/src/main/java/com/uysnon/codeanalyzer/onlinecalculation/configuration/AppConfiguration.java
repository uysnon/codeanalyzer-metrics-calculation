package com.uysnon.codeanalyzer.onlinecalculation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean("someString123")
    public String getMe(){
        return "alalal";
    }
}

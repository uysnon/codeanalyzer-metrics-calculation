package com.uysnon.codeanalyzer.auth.config;

import com.uysnon.codeanalyzer.auth.filter.JwtRequestFilterBefore;
import com.uysnon.codeanalyzer.auth.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtBeansConfig {
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }

    @Bean
    public JwtRequestFilterBefore jwtRequestFilterBefore(@Autowired JwtUtil jwtUtil) {
        return new JwtRequestFilterBefore(jwtUtil);
    }
}

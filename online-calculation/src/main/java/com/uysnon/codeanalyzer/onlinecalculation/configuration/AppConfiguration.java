package com.uysnon.codeanalyzer.onlinecalculation.configuration;

import jep.JepConfig;
import jep.MainInterpreter;
import jep.SharedInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class AppConfiguration {
    @Bean("someString123")
    public String getMe() {
        return "alalal";
    }

    @Bean
    public JepConfig jepConfig() {
        String pythonFolder = System.getenv("DYLD_LIBRARY_PATH");
        String jepPath = pythonFolder + "\\jep.dll";
//initialize the MainInterpreter
        MainInterpreter.setJepLibraryPath(jepPath);
        JepConfig jepConf = new JepConfig();
        jepConf.addIncludePaths("D:\\java\\projects\\codeanalyzer-metrics-calculation\\analyze-scripts");
        jepConf.addIncludePaths(pythonFolder);
        return jepConf;
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }

}

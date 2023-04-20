package com.uysnon.codeanalyzer.onlinecalculation.configuration;

import jep.JepConfig;
import jep.MainInterpreter;
import jep.SharedInterpreter;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class AppConfiguration {
    @Bean("someString123")
    public String getMe() {
        return "alalal";
    }

    @Bean
    public JepConfig jepConfig() {
        String pythonFolder = System.getenv("DYLD_LIBRARY_PATH");
        // "D:\\java\\projects\\codeanalyzer-metrics-calculation\\analyze-scripts"
        String analyzeScriptsPath = System.getenv("ANALYZE_SCRIPTS_PATH");
        String jepPath;
        if (SystemUtils.IS_OS_WINDOWS) {
            jepPath = pythonFolder + "\\jep.dll";
        } else {
            jepPath = pythonFolder + "/jep.cpython-38-darwin.so";
//            jepPath = pythonFolder + "/libjep.jnilib";
            if (!Files.exists(Path.of(jepPath))){
                jepPath = pythonFolder + "/libjep.so";
            }
        }
//initialize the MainInterpreter
        MainInterpreter.setJepLibraryPath(jepPath);
        JepConfig jepConf = new JepConfig();
        jepConf.addIncludePaths(analyzeScriptsPath);
//        jepConf.addIncludePaths(pythonFolder);
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

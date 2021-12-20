package com.uysnon.codeanalyzer.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artifact")
public class ArtifactController {

    @Value("${app.version:332}")
    private String version;

    @GetMapping("/version")
    public String getVersion(){
        return version;
    }
}

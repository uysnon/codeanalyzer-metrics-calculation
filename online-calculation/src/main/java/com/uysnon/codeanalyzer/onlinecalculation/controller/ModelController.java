package com.uysnon.codeanalyzer.onlinecalculation.controller;

import jep.SharedInterpreter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ModelController {

    @PostMapping("/model/calculate")
    public String calculate() {
        SharedInterpreter sharedInterpreter = new SharedInterpreter();
        sharedInterpreter.eval("a = 3");
        Object result =  sharedInterpreter.getValue("a");
        System.out.println(result);

        return "";
    }
}

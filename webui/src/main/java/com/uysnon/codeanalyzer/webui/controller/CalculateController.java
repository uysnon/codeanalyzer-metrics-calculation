package com.uysnon.codeanalyzer.webui.controller;

import com.uysnon.codeanalyzer.webui.service.CalculateProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;


@Controller
@RequestMapping("calculate")
public class CalculateController {
    private CalculateProjectService calculateProjectService;

    @Autowired
    public void setCalculateProjectService(CalculateProjectService calculateProjectService) {
        this.calculateProjectService = calculateProjectService;
    }

    @GetMapping("select")
    public String selectProject() {
        return "select_project";
    }


    @PostMapping("metrics")
    public String calculate(@RequestParam("projectFile") MultipartFile multipartFile) throws IOException {
        Long id = calculateProjectService.calculate(multipartFile);
        return String.format("redirect:/calculate/result/%d", id);
    }

    @GetMapping("result/{id}")
    public String result(@PathVariable("id") Long id, Model model) throws IOException {
        model.addAttribute("exportReport", calculateProjectService.getExportReport(id));
        model.addAttribute("numberFormat", "%3.1f");
        model.addAttribute("percentFormat", "%3.1f%%");
        return "result";
    }


    @ExceptionHandler({MissingServletRequestPartException.class})
    public String exceptionHandler() {
        return "redirect:/calculate/select";
    }


}

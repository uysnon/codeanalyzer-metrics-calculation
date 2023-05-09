package com.uysnon.codeanalyzer.teacherui.controller;

import com.github.javaparser.utils.Pair;
import com.uysnon.codeanalyzer.teacherui.service.CalculateProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private String select(
            @RequestParam("id") Long id,
            Model model) {
        model.addAttribute("id", id);
        return "select_project";
    }

    @PostMapping("metrics")
    public String calculate(
            @RequestParam("projectFile") MultipartFile multipartFile,
            @RequestParam("id") Long modelId
    ) throws IOException {
        Pair<Long, Long> ids = calculateProjectService.calculate(multipartFile);
        return String.format("redirect:/calculate/result?exportReport=%d&studentsLabModelCall=%d",
                ids.a,
                ids.b);
    }

    @GetMapping("result")
    public String result(@RequestParam("exportReport") Long idExportReport,
                         @RequestParam("studentsLabModelCall") Long idStudentsLabModelCall,
                         Model model) throws IOException {
        model.addAttribute("exportReport", calculateProjectService.getExportReport(idExportReport));
        model.addAttribute("studentsLabModelCall", calculateProjectService.getStudentsLabModelCall(idStudentsLabModelCall));
        model.addAttribute("numberFormat", "%3.1f");
        model.addAttribute("percentFormat", "%3.1f%%");
        return "result";
    }
}

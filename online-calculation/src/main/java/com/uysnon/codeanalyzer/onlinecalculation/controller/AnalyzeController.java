package com.uysnon.codeanalyzer.onlinecalculation.controller;

import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ExportReport;
import com.uysnon.codeanalyzer.onlinecalculation.service.AnalyzeService;
import com.uysnon.codeanalyzer.onlinecalculation.service.report.ReportUnitPacks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RestController
public class AnalyzeController {

    private AnalyzeService analyzeService;

    @Autowired
    public void setAnalyzeService(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @PostMapping("/analyze/units")
    public List<ExportUnit> calculateExportUnits(@RequestParam("file") MultipartFile file) throws IOException {
        return analyzeService.analyzeAndGetExportUnits(file);
    }

    @PostMapping("/analyze/report")
    public ExportReport createExportReport(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("POST /analyze/report" );
        Instant start = Instant.now();
        ExportReport exportReport= analyzeService.analyzeAndGetReport(file, ReportUnitPacks.FULL);
        Instant end = Instant.now();
        System.out.println("REPONSE /analyze/report, duration: " + Duration.between(start, end));
        return exportReport;
    }
}

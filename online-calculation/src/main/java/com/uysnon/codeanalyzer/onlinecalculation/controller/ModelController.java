package com.uysnon.codeanalyzer.onlinecalculation.controller;

import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ExportReport;
import com.uysnon.codeanalyzer.onlinecalculation.service.AnalyzeService;
import com.uysnon.codeanalyzer.onlinecalculation.service.report.ReportUnitPacks;
import jep.Interpreter;
import jep.JepConfig;
import jep.SharedInterpreter;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Locale;


@RestController
public class ModelController {
    private AnalyzeService analyzeService;

    private JepConfig jepConfig;

    @Autowired
    public void setJepConfig(JepConfig jepConfig) {
        this.jepConfig = jepConfig;
    }

    @Autowired
    public void setAnalyzeService(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @PostMapping("/model/labs/calculate")
    public String calculateLab(@RequestParam("file") MultipartFile file) throws IOException {
        String path = new ClassPathResource(
                "model_code_metrics_4.h5").getFile().getAbsolutePath();
        Interpreter sharedInterpreter = jepConfig.createSubInterpreter();
//        try (SharedInterpreter sharedInterpreter = new SharedInterpreter()) {
            sharedInterpreter.eval("from tensorflow import keras");
            sharedInterpreter.eval(String.format("model = keras.models.load_model(\"%s\")", StringEscapeUtils.escapeEcmaScript(path)));
            ExportReport exportReport = analyzeService.analyzeAndGetReport(file, ReportUnitPacks.ALL_PROJECTS_MODEl);
            sharedInterpreter.eval(String.format(Locale.US, "result = model.predict([[%8.6f,%8.6f,%8.6f,%8.6f,%8.6f,%8.6f]])[0][0]",
                    getDoublePropertyFromExportReport(exportReport, "AVG_METHOD_LENGTH") / 50.0,
                    getDoublePropertyFromExportReport(exportReport, "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_LESS_THAN_15"),
                    getDoublePropertyFromExportReport(exportReport, "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_15"),
                    getDoublePropertyFromExportReport(exportReport, "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_30"),
                    getDoublePropertyFromExportReport(exportReport, "PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_0_10"),
                    getDoublePropertyFromExportReport(exportReport, "PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_11_999")
            ));
            float result = ((float[]) sharedInterpreter.getValue("result"))[0];

            if (result >= 0.5) {
                return String.format(Locale.US, "GOOD", result);
            } else {
                return String.format(Locale.US, "BAD", result);
            }
//        }
    }

    @PostMapping("/model/calculate")
    public String calculate(@RequestParam("file") MultipartFile file) throws IOException {
        String pathModel = new ClassPathResource(
                "all_projects_model_final.h5").getFile().getAbsolutePath();
        try (SharedInterpreter sharedInterpreter = new SharedInterpreter()) {
            sharedInterpreter.eval("from tensorflow import keras");
            sharedInterpreter.eval(String.format("model = keras.models.load_model(\"%s\")", StringEscapeUtils.escapeEcmaScript(pathModel)));
            ExportReport exportReport = analyzeService.analyzeAndGetReport(file, ReportUnitPacks.ALL_PROJECTS_MODEl);
            sharedInterpreter.eval(String.format(Locale.US, "result = model.predict([[%8.6f,%8.6f,%8.6f,%8.6f]])[0][0]",
                    (getDoublePropertyFromExportReport(exportReport, "LOC") - 12216.50) / 41282.15,
                    (getDoublePropertyFromExportReport(exportReport, "CODE_SMELLS_COUNT") - 423.10) / 1812,
                    getDoublePropertyFromExportReport(exportReport, "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_LESS_THAN_15"),
                    getDoublePropertyFromExportReport(exportReport, "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_60")
            ));
            float result = ((float[]) sharedInterpreter.getValue("result"))[0];

            String pathScores = new ClassPathResource(
                    "all_scores_sorted").getFile().getAbsolutePath();
            sharedInterpreter.eval(String.format("import pandas as pd"));
            sharedInterpreter.eval(String.format("from scipy import stats"));
            sharedInterpreter.eval(String.format("data_from_file = pd.read_csv(r'%s')['score']", pathScores));
            System.out.println(String.format(Locale.US, "resultPercentil = stats.percentileofscore(data_from_file, %7.6f)", result));
            sharedInterpreter.eval(String.format(Locale.US, "resultPercentil = stats.percentileofscore(data_from_file, %7.6f)", result));
            Double resultPercentil = (Double) sharedInterpreter.getValue("resultPercentil");

            String answer = String.format("Result = %5.2f points, your project better than %5.2f%% other", result, resultPercentil);

            return answer;
        }
    }

    private Double getDoublePropertyFromExportReport(ExportReport exportReport, String code) {
        return Double.parseDouble(exportReport.getReportUnitByCode(code).getValue());
    }
}

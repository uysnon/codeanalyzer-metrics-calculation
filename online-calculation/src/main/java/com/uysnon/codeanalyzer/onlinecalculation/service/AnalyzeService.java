package com.uysnon.codeanalyzer.onlinecalculation.service;

import Designite.Designite;
import Designite.SourceModel.SM_Project;
import com.uysnon.codeanalyzer.core.adapters.TreeCreator;
import com.uysnon.codeanalyzer.core.syntaxelements.ProjectTree;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ModelCall;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ExportReport;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ReportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.util.CoreModelExporter;
import com.uysnon.codeanalyzer.onlinecalculation.model.file.ProjectLocation;
import com.uysnon.codeanalyzer.onlinecalculation.service.report.ReportUnitPacks;
import jep.SharedInterpreter;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
public class AnalyzeService {

    private ArchiveService archiveService;

    @Autowired
    public void setArchiveService(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    public List<ExportUnit> analyzeAndGetExportUnits(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String unzipLocation = "temporary/" + uuid + "/";
        ProjectLocation projectLocation = archiveService.unzipProject(
                uuid,
                unzipLocation,
                file);
        TreeCreator treeCreator = new TreeCreator();
        ProjectTree projectTree = treeCreator.create(projectLocation.getJavaFiles());
        return CoreModelExporter.mapFromProjectTree(projectTree);
    }

    public ExportReport analyzeAndGetReport(MultipartFile file,
                                            ReportUnitPacks pack) throws IOException {
        UUID uuid = UUID.randomUUID();
        String unzipLocation = "temporary/" + uuid + "/";
        ProjectLocation projectLocation = archiveService.unzipProject(
                uuid,
                unzipLocation,
                file);
        String projectName = getProjectName(projectLocation.getJavaFiles().get(0).toString());
        String projectDescription = projectLocation.getProjectUUID().toString();
        ExportReport exportReport = new ExportReport();
        exportReport.setTitle(projectName);
        exportReport.setDescription(projectDescription);
        exportReport.setReportUnits(new ArrayList<>());
        List<ReportUnit> reportUnits = analyzeAndGetExportUnits(projectLocation, pack);
        exportReport.setReportUnits(reportUnits);
        addModelCallResultToExportUnit(exportReport);

        FileUtils.forceDelete(new File(unzipLocation.substring(0, unzipLocation.length() - 1)));
        return exportReport;
    }

    private ExportReport addModelCallResultToExportUnit(ExportReport exportReport) throws IOException {
        ModelCall modelCall = new ModelCall();
        if (SystemUtils.IS_OS_WINDOWS) {
            try (SharedInterpreter sharedInterpreter = new SharedInterpreter()) {
                String pathModel = new ClassPathResource(
                        "all_projects_model_final.h5").getFile().getAbsolutePath();
                sharedInterpreter.eval("from tensorflow import keras");
                sharedInterpreter.eval(String.format("model = keras.models.load_model(\"%s\")", StringEscapeUtils.escapeEcmaScript(pathModel)));
                sharedInterpreter.eval(String.format(Locale.US, "result = model.predict([[%8.6f,%8.6f,%8.6f,%8.6f]])[0][0]",
                        (getDoublePropertyFromExportReport(exportReport, "LOC") - 12216.50) / 41282.15,
                        (getDoublePropertyFromExportReport(exportReport, "CODE_SMELLS_COUNT") - 423.10) / 1812,
                        getDoublePropertyFromExportReport(exportReport, "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_LESS_THAN_15"),
                        getDoublePropertyFromExportReport(exportReport, "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_60")
                ));
                float result = ((float[]) sharedInterpreter.getValue("result"))[0];
                modelCall.setScore(result * 100);

                String pathScores = new ClassPathResource(
                        "all_scores_sorted").getFile().getAbsolutePath();
                sharedInterpreter.eval(String.format("import pandas as pd"));
                sharedInterpreter.eval(String.format("from scipy import stats"));
                sharedInterpreter.eval(String.format("data_from_file = pd.read_csv(r'%s')['score']", pathScores));
                System.out.println(String.format(Locale.US, "resultPercentil = stats.percentileofscore(data_from_file, %7.6f)", result));
                sharedInterpreter.eval(String.format(Locale.US, "resultPercentil = stats.percentileofscore(data_from_file, %7.6f)", result));
                Double resultPercentil = (Double) sharedInterpreter.getValue("resultPercentil");
                modelCall.setPercentil(resultPercentil);
            }
        } else {
            modelCall.setScore(66.4);
            modelCall.setPercentil(59.1);
        }
        exportReport.setModelCall(modelCall);
        return exportReport;
    }


    private List<ReportUnit> analyzeAndGetExportUnits(ProjectLocation projectLocation, ReportUnitPacks pack) throws IOException {
        TreeCreator treeCreator = new TreeCreator();
        ProjectTree projectTree = treeCreator.create(projectLocation.getJavaFiles());
        List<ExportUnit> exportUnits = CoreModelExporter.mapFromProjectTree(projectTree);

        String[] designiteArgs = Stream.of(
                "--Input",
                projectLocation.getPathToProject(),
                "--Output",
                projectLocation.getPathToProject()).toArray(String[]::new);
        SM_Project designiteProject = Designite.submain(designiteArgs);
        return createReportUnits(projectLocation, exportUnits, designiteProject, pack);
    }

    private List<ReportUnit> createReportUnits(ProjectLocation projectLocation,
                                               List<ExportUnit> exportUnits,
                                               SM_Project designiteProject,
                                               ReportUnitPacks pack) {
        List<ReportUnit> reportUnits = pack.createPack();
        reportUnits.forEach(unit -> unit.fill(exportUnits, designiteProject));
        return reportUnits;
    }

    private String getProjectName(String pathToAnyJavaFile) {
        String regexp = "^temporary[\\/\\\\].+?[\\/\\\\](.+?)[\\/\\\\].+$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(pathToAnyJavaFile);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    private Double getDoublePropertyFromExportReport(ExportReport exportReport, String code) {
        return Double.parseDouble(exportReport.getReportUnitByCode(code).getValue());
    }
}

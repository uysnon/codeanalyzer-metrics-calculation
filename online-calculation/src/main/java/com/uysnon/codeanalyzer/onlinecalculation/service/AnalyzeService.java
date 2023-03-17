package com.uysnon.codeanalyzer.onlinecalculation.service;

import Designite.Designite;
import Designite.SourceModel.SM_Project;
import com.uysnon.codeanalyzer.core.adapters.TreeCreator;
import com.uysnon.codeanalyzer.core.syntaxelements.ProjectTree;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ExportReport;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ReportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.util.CoreModelExporter;
import com.uysnon.codeanalyzer.onlinecalculation.model.file.ProjectLocation;
import com.uysnon.codeanalyzer.onlinecalculation.service.report.ReportUnitPacks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
}

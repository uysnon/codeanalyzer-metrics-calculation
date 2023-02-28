package com.uysnon.codeanalyzer.onlinecalculation.service;

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

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public ExportReport analyzeAndGetReport(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String unzipLocation = "temporary/" + uuid + "/";
        ProjectLocation projectLocation = archiveService.unzipProject(
                uuid,
                unzipLocation,
                file);
        TreeCreator treeCreator = new TreeCreator();
        ProjectTree projectTree = treeCreator.create(projectLocation.getJavaFiles());
        List<ExportUnit> exportUnits = CoreModelExporter.mapFromProjectTree(projectTree);
        return createReport(projectLocation, exportUnits);
    }

    private ExportReport createReport(ProjectLocation projectLocation,
                                      List<ExportUnit> exportUnits) {
        List<ReportUnit> reportUnits = ReportUnitPacks.FULL.createPack();
        reportUnits.forEach(unit -> unit.fill(exportUnits));
        String projectName = getProjectName(projectLocation.getJavaFiles().get(0).toString());
        return new ExportReport(
                projectLocation.getProjectUUID().toString(),
                projectName,
                reportUnits);
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

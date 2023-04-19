package com.uysnon.codeanalyzer.onlinecalculation.model.export.report;

import Designite.SourceModel.SM_Project;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.units.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AvgDepthInheritanceUnit.class, name = "avi"),
        @JsonSubTypes.Type(value = AvgMethodLengthReportUnit.class, name = "avmlr"),
        @JsonSubTypes.Type(value = CountABCMetricForMethodsReportUnit.class, name = "cabcmfm"),
        @JsonSubTypes.Type(value = CountConstructorsCallingAbstractMethodReportUnit.class, name = "cccam") ,
        @JsonSubTypes.Type(value = CountCyclomaticComplexityMetricForMethodsReportUnit.class, name = "cccmfm") ,
        @JsonSubTypes.Type(value = LinesCodeReportUnit.class, name = "loc") ,
        @JsonSubTypes.Type(value = MaxDepthInheritanceUnit.class, name = "mdi") ,
        @JsonSubTypes.Type(value = PercentageABCMetricForMethodsReportUnit.class, name = "pabcmfm") ,
        @JsonSubTypes.Type(value = PercentageBigMethodsReportUnit.class, name = "pbm") ,
        @JsonSubTypes.Type(value = PercentageCyclomaticComplexityMetricForMethodsReportUnit.class, name = "pccmfm") ,
        @JsonSubTypes.Type(value = PercentageSmallMethodsReportUnit.class, name = "psm") ,
        @JsonSubTypes.Type(value = TotalCodeSmellsCountReportUnit.class, name = "tcmc")}
)
@Data
@NoArgsConstructor
public abstract class ReportUnit {
    private String code;
    private String description;
    private String value;
    private String type;
    private String meta;

    public abstract ReportUnit fill(List<ExportUnit> units, SM_Project designiteProject);

    protected List<ExportUnit> getRecursiveUnits(List<ExportUnit> units) {
        List<ExportUnit> result = new ArrayList<>();
        units.forEach(u -> innerRecursiveChildrenFind(u, result));
        return result;
    }

    private void innerRecursiveChildrenFind(ExportUnit exportUnit, List<ExportUnit> exportUnits) {
        exportUnits.add(exportUnit);
        exportUnit.getChildren().forEach(u -> {
            innerRecursiveChildrenFind(u, exportUnits);
        });
    }
}

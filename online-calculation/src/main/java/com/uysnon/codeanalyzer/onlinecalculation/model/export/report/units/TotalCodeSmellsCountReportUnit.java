package com.uysnon.codeanalyzer.onlinecalculation.model.export.report.units;

import Designite.SourceModel.SM_Method;
import Designite.SourceModel.SM_Package;
import Designite.SourceModel.SM_Project;
import Designite.SourceModel.SM_Type;
import Designite.metrics.TypeMetrics;
import Designite.smells.models.DesignCodeSmell;
import Designite.smells.models.ImplementationCodeSmell;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.DataTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ReportUnit;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TotalCodeSmellsCountReportUnit extends ReportUnit {
    public static final String CODE = "CODE_SMELLS_COUNT";
    public static final String DESCRIPTION = "Количество code smells";

    public final static List<String> SMELLS_NAMING = List.of(
            "Unnecessary Abstraction",
            "Deficient Encapsulation",
            "Unexploited Encapsulation",
            "Insufficient Modularization",
            "Broken Hierarchy",
            "Abstract Function Call From Constructor",
            "Empty catch clause",
            "Long Parameter List",
            "Magic Number",
            "Missing default"
    );

    public TotalCodeSmellsCountReportUnit() {
        setCode(CODE);
        setDescription(DESCRIPTION);
        setType(DataTypes.INTEGER.getCode());
    }

    @Override
    public ReportUnit fill(List<ExportUnit> units, SM_Project designiteProject) {
        int count = 0;


        for (SM_Package smPackage : designiteProject.getPackageList()) {
            smPackage.extractCodeSmells();
            for (Map.Entry<SM_Type, List<DesignCodeSmell>> designCodeSmellsMapping : smPackage.getDesignCodeSmellMapping().entrySet()) {
                for (DesignCodeSmell designCodeSmell : designCodeSmellsMapping.getValue()) {
                    if (SMELLS_NAMING.contains(designCodeSmell.getSmellName())) {
                        count++;
                    }
                }
                for (Map.Entry<SM_Method, List<ImplementationCodeSmell>> implementationCodeSmellsMapping :
                        designCodeSmellsMapping.getKey().getImplementationCodeSmellMapping().entrySet()) {
                    for (ImplementationCodeSmell implementationCodeSmell : implementationCodeSmellsMapping.getValue()) {
                        if (SMELLS_NAMING.contains(implementationCodeSmell.getSmellName())) {
                            count++;
                        }
                    }
                }
            }
        }
        this.setValue(Integer.toString(count));
        return this;
    }
}

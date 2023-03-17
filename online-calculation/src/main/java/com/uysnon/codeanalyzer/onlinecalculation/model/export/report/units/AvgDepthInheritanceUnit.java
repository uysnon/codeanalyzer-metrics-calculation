package com.uysnon.codeanalyzer.onlinecalculation.model.export.report.units;

import Designite.SourceModel.SM_Package;
import Designite.SourceModel.SM_Project;
import Designite.SourceModel.SM_Type;
import Designite.metrics.TypeMetrics;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.DataTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ReportUnit;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AvgDepthInheritanceUnit extends ReportUnit {
    public static final String CODE = "AVG_DEPENDENCY_TREE_DEPTH";
    public static final String DESCRIPTION = "Средняя глубина дерева наследования класса";

    public AvgDepthInheritanceUnit() {
        setCode(CODE);
        setDescription(DESCRIPTION);
        setType(DataTypes.DOUBLE.getCode());
    }

    @Override
    public ReportUnit fill(List<ExportUnit> units, SM_Project designiteProject) {
        int count = 0;
        int sum = 0;
        for (SM_Package smPackage : designiteProject.getPackageList()) {
            for (Map.Entry<SM_Type, TypeMetrics> metricsEntry : smPackage.getMetrics().entrySet()) {
                int depthOfInheritance = metricsEntry.getValue().getInheritanceDepth();
                sum += depthOfInheritance;
                count++;
            }
        }
        double result = ((double) sum) / count;
        this.setValue(String.format(Locale.US, "%5.2f", result).trim());
        return this;
    }
}

package com.uysnon.codeanalyzer.onlinecalculation.model.export.report.units;

import Designite.SourceModel.SM_Package;
import Designite.SourceModel.SM_Project;
import Designite.SourceModel.SM_Type;
import Designite.metrics.TypeMetrics;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.DataTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ReportUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class MaxDepthInheritanceUnit extends ReportUnit {
    public static final String CODE = "MAX_DEPENDENCY_TREE_DEPTH";
    public static final String DESCRIPTION = "Максимальная глубина дерева наследования класса";

    public MaxDepthInheritanceUnit() {
        setCode(CODE);
        setDescription(DESCRIPTION);
        setType(DataTypes.INTEGER.getCode());
    }

    @Override
    public ReportUnit fill(List<ExportUnit> units, SM_Project designiteProject) {
        int max = 0;
        List<String> classesWithMaxDepth = new ArrayList<>();
        for (SM_Package smPackage : designiteProject.getPackageList()) {
            for (Map.Entry<SM_Type, TypeMetrics> metricsEntry : smPackage.getMetrics().entrySet()) {
                int depthOfInheritance = metricsEntry.getValue().getInheritanceDepth();
                if (depthOfInheritance >= max) {
                    String className = metricsEntry.getKey().getParentPkg().getName() + "." + metricsEntry.getKey().getName();
                    if (depthOfInheritance == max) {
                        classesWithMaxDepth.add(className);
                    } else {
                        classesWithMaxDepth.clear();
                        classesWithMaxDepth.add(className);
                        max = depthOfInheritance;
                    }
                }
            }
        }
        this.setValue(String.format(Locale.US, "%d", max));
        this.setMeta(String.join(";", classesWithMaxDepth));
        return this;
    }
}

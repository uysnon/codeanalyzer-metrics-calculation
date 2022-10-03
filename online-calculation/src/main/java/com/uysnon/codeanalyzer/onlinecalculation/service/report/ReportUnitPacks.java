package com.uysnon.codeanalyzer.onlinecalculation.service.report;

import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ReportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.units.*;

import java.util.List;

public enum ReportUnitPacks {
    FULL {
        @Override
        public List<ReportUnit> createPack() {
            return List.of(
                    new LinesCodeReportUnit(),
                    new AvgMethodLengthReportUnit(),
                    new PercentageSmallMethodsReportUnit(15),
                    new PercentageBigMethodsReportUnit(15),
                    new PercentageBigMethodsReportUnit(30),
                    new PercentageBigMethodsReportUnit(60),
                    new CountCyclomaticComplexityMetricForMethods(0, 10),
                    new CountCyclomaticComplexityMetricForMethods(11, 999),
                    new CountCyclomaticComplexityMetricForMethods(21, 999),
                    new CountCyclomaticComplexityMetricForMethods(41, 999),
                    new PercentageCyclomaticComplexityMetricForMethods(0, 999),
                    new PercentageCyclomaticComplexityMetricForMethods(11, 999),
                    new PercentageCyclomaticComplexityMetricForMethods(21, 999),
                    new PercentageCyclomaticComplexityMetricForMethods(41, 999),
                    new CountABCMetricForMethods(0, 10),
                    new CountABCMetricForMethods(11, 999),
                    new CountABCMetricForMethods(21, 999),
                    new CountABCMetricForMethods(41, 999),
                    new CountABCMetricForMethods(61, 999),
                    new CountABCMetricForMethods(101, 999),
                    new PercentageABCMetricForMethods(0, 10),
                    new PercentageABCMetricForMethods(11, 999),
                    new PercentageABCMetricForMethods(21, 999),
                    new PercentageABCMetricForMethods(41, 999),
                    new PercentageABCMetricForMethods(61, 999),
                    new PercentageABCMetricForMethods(101, 999)
            );
        }
    }
    ;
    public abstract List<ReportUnit> createPack();
}

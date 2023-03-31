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
                    new AvgDepthInheritanceUnit(),
                    new MaxDepthInheritanceUnit(),
                    new TotalCodeSmellsCountReportUnit(),
                    new PercentageSmallMethodsReportUnit(15),
                    new PercentageBigMethodsReportUnit(15),
                    new PercentageBigMethodsReportUnit(30),
                    new PercentageBigMethodsReportUnit(60),
                    new PercentageCyclomaticComplexityMetricForMethodsReportUnit(0, 999),
                    new PercentageCyclomaticComplexityMetricForMethodsReportUnit(11, 999),
                    new PercentageCyclomaticComplexityMetricForMethodsReportUnit(21, 999),
                    new PercentageCyclomaticComplexityMetricForMethodsReportUnit(41, 999),
                    new PercentageABCMetricForMethodsReportUnit(0, 10),
                    new PercentageABCMetricForMethodsReportUnit(11, 999),
                    new PercentageABCMetricForMethodsReportUnit(21, 999),
                    new PercentageABCMetricForMethodsReportUnit(41, 999),
                    new PercentageABCMetricForMethodsReportUnit(61, 999),
                    new PercentageABCMetricForMethodsReportUnit(101, 999)
            );
        }
    },

    MODEL {
        @Override
        public List<ReportUnit> createPack() {
            return List.of(
                    new AvgMethodLengthReportUnit(),
                    new PercentageSmallMethodsReportUnit(15),
                    new PercentageBigMethodsReportUnit(15),
                    new PercentageBigMethodsReportUnit(30),
                    new PercentageABCMetricForMethodsReportUnit(0, 10),
                    new PercentageABCMetricForMethodsReportUnit(11, 999)
            );
        }
    },

    ALL_PROJECTS_MODEl {
        @Override
        public List<ReportUnit> createPack() {
            return List.of(
                    new LinesCodeReportUnit(),
                    new TotalCodeSmellsCountReportUnit(),
                    new PercentageSmallMethodsReportUnit(15),
                    new PercentageBigMethodsReportUnit(60)
            );
        }
    };

    public abstract List<ReportUnit> createPack();
}

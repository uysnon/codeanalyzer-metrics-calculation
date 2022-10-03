package com.uysnon.codeanalyzer.onlinecalculation.model.export.report.units;

import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportMetric;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.DataTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ReportUnit;

import java.util.List;

public class LinesCodeReportUnit extends ReportUnit {
    public static final String CODE = "LOC";
    public static final String DESCRIPTION = "Количество строк кода";

    public LinesCodeReportUnit() {
        setCode(CODE);
        setDescription(DESCRIPTION);
        setType(DataTypes.INTEGER.getCode());
    }

    @Override
    public ReportUnit fill(List<ExportUnit> units) {
        ExportMetric defaultMetric = new ExportMetric();
        defaultMetric.setValue("0");
        Integer totalLoc = units.stream()
                .map(u -> u.getMetrics().getOrDefault("LOC", defaultMetric))
                .mapToInt(m -> Integer.parseInt(m.getValue()))
                .sum();
        setValue(String.valueOf(totalLoc));
        return this;
    }
}

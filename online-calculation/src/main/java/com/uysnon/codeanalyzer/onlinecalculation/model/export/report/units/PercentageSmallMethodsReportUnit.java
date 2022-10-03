package com.uysnon.codeanalyzer.onlinecalculation.model.export.report.units;

import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportMetric;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnitTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.DataTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ReportUnit;

import java.util.List;

public class PercentageSmallMethodsReportUnit extends ReportUnit{
    public static final String CODE = "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_LESS_THAN_%d";
    public static final String DESCRIPTION =
            "Какой объем от всего кода занимают методы (конструкторы) с числом строк " +
                    " менее %d";

    private List<String> calculableUnitTypes;
    private Integer limit;

    public PercentageSmallMethodsReportUnit(Integer smallMethodLimitInLines) {
        limit = smallMethodLimitInLines;
        setCode(String.format(CODE, limit));
        setDescription(String.format(DESCRIPTION, limit));
        setType(DataTypes.PERCENT_DOUBLE.getCode());
        calculableUnitTypes = List.of(
                ExportUnitTypes.METHOD.getType(),
                ExportUnitTypes.CONSTRUCTOR.getType()
        );
    }

    @Override
    public ReportUnit fill(List<ExportUnit> units) {
        LinesCodeReportUnit linesCodeReportUnit = new LinesCodeReportUnit();
        Integer totalLoc = Integer.parseInt(linesCodeReportUnit.fill(units).getValue());
        ExportMetric defaultMetric = new ExportMetric();
        defaultMetric.setValue("0");
        List<ExportUnit> allUnits = getRecursiveUnits(units);
        Integer locOfAllSmallMethods = allUnits.stream()
                .filter(u -> (calculableUnitTypes.contains(u.getType())))
                .map(u -> u.getMetrics().get("LOC"))
                .mapToInt(m -> Integer.parseInt(m.getValue()))
                .filter(i -> i <= limit)
                .sum();
        Double percentageSmallMethods = ((double) locOfAllSmallMethods) / totalLoc;
        setValue(String.valueOf(percentageSmallMethods));
        return this;
    }
}
package com.uysnon.codeanalyzer.onlinecalculation.model.export.report.units;

import Designite.SourceModel.SM_Project;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportMetric;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnitTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.DataTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ReportUnit;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PercentageBigMethodsReportUnit extends ReportUnit {
    public static final String CODE = "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_%d";
    public static final String DESCRIPTION =
            "Какой объем от всего кода занимают методы (конструкторы) с числом строк " +
            " более %d";

    private List<String> calculableUnitTypes;
    private Integer limit;

    public PercentageBigMethodsReportUnit(Integer smallMethodLimitInLines) {
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
    public ReportUnit fill(List<ExportUnit> units, SM_Project designiteProject) {
        LinesCodeReportUnit linesCodeReportUnit = new LinesCodeReportUnit();
        Integer totalLoc = Integer.parseInt(linesCodeReportUnit.fill(units, designiteProject).getValue());
        ExportMetric defaultMetric = new ExportMetric();
        defaultMetric.setValue("0");
        List<ExportUnit> allUnits = getRecursiveUnits(units);
        Integer locOfAllSmallMethods = allUnits.stream()
                .filter(u -> (calculableUnitTypes.contains(u.getType())))
                .map(u -> u.getMetrics().get("LOC"))
                .mapToInt(m -> Integer.parseInt(m.getValue()))
                .filter(i -> i > limit)
                .sum();
        Double percentageSmallMethods = ((double) locOfAllSmallMethods) / totalLoc;
        setValue(String.valueOf(percentageSmallMethods));
        return this;
    }
}

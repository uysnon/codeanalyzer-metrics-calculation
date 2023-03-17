package com.uysnon.codeanalyzer.onlinecalculation.model.export.report.units;

import Designite.SourceModel.SM_Project;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.DataTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnitTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ReportUnit;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class PercentageABCMetricForMethodsReportUnit extends ReportUnit {
    public static final String CODE = "PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_%d_%d";
    public static final String DESCRIPTION =
            "Отношение строк кода методов (конструкторов) с ABC метрикой, лежащей в диапазоне" +
                    "[%d, %d] ко всем строкам кода в методах";

    private List<String> calculableUnitTypes;
    private Pair<Integer, Integer> bounds;

    public PercentageABCMetricForMethodsReportUnit(
            Integer lowerBound,
            Integer upperBound) {
        bounds = Pair.of(lowerBound, upperBound);
        setCode(String.format(CODE, bounds.getLeft(), bounds.getRight()));
        setType(DataTypes.PERCENT_DOUBLE.getCode());
        setDescription(String.format(DESCRIPTION, bounds.getLeft(), bounds.getRight()));
        calculableUnitTypes = List.of(
                ExportUnitTypes.METHOD.getType(),
                ExportUnitTypes.CONSTRUCTOR.getType()
        );
    }

    @Override
    public ReportUnit fill(List<ExportUnit> units, SM_Project designiteProject) {
        List<ExportUnit> allUnits = getRecursiveUnits(units);
        Integer methodsInBoundsRangeLOC = allUnits.stream()
                .filter(u -> (calculableUnitTypes.contains(u.getType())))
                .filter(u -> {
                    Integer complValue = Integer.parseInt(u.getMetrics().get("ABC").getValue());
                    return complValue >= bounds.getLeft() && complValue <= bounds.getRight();
                })
                .map(u -> u.getMetrics().get("LOC"))
                .mapToInt(m -> Integer.parseInt(m.getValue()))
                .sum();
        Integer totalMethodsLOC = allUnits.stream()
                .filter(u -> (calculableUnitTypes.contains(u.getType())))
                .map(u -> u.getMetrics().get("LOC"))
                .mapToInt(m -> Integer.parseInt(m.getValue()))
                .sum();
        setValue(String.valueOf((double) methodsInBoundsRangeLOC / totalMethodsLOC));
        return this;
    }
}

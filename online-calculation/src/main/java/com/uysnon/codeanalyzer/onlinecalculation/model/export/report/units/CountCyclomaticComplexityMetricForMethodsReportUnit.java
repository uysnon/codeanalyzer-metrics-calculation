package com.uysnon.codeanalyzer.onlinecalculation.model.export.report.units;

import Designite.SourceModel.SM_Project;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.DataTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnitTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ReportUnit;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@NoArgsConstructor
public class CountCyclomaticComplexityMetricForMethodsReportUnit extends ReportUnit {
    public static final String CODE = "COUNT_METHODS_WITH_CYCL_COMPL_IN_RANGE_%d_%d";
    public static final String DESCRIPTION =
            "Количество методов (конструкторов) с цикломатической сложностью, лежащей в диапазоне" +
                    "[%d, %d]";

    private List<String> calculableUnitTypes;
    private Pair<Integer, Integer> bounds;

    public CountCyclomaticComplexityMetricForMethodsReportUnit(
            Integer lowerBound,
            Integer upperBound) {
        bounds = Pair.of(lowerBound, upperBound);
        setCode(String.format(CODE, bounds.getLeft(), bounds.getRight()));
        setType(DataTypes.INTEGER.getCode());
        setDescription(String.format(DESCRIPTION, bounds.getLeft(), bounds.getRight()));
        calculableUnitTypes = List.of(
                ExportUnitTypes.METHOD.getType(),
                ExportUnitTypes.CONSTRUCTOR.getType()
        );
    }

    @Override
    public ReportUnit fill(List<ExportUnit> units, SM_Project designiteProject) {
        List<ExportUnit> allUnits = getRecursiveUnits(units);
        Integer countMethodsWithCCinBounds = (int) allUnits.stream()
                .filter(u -> (calculableUnitTypes.contains(u.getType())))
                .map(u -> u.getMetrics().get("CYCL_COMPL"))
                .mapToInt(m -> Integer.parseInt(m.getValue()))
                .filter(i -> (i >= bounds.getLeft() && i<= bounds.getRight()))
                .count();
        setValue(String.valueOf(countMethodsWithCCinBounds));
        return this;
    }
}

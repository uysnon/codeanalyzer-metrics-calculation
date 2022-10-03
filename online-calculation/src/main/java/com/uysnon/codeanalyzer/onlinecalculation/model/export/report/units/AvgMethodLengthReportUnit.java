package com.uysnon.codeanalyzer.onlinecalculation.model.export.report.units;

import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportMetric;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnitTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.DataTypes;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ReportUnit;

import java.util.List;

public class AvgMethodLengthReportUnit extends ReportUnit {
    public static final String CODE = "AVG_METHOD_LENGTH";
    public static final String DESCRIPTION = "Средняя длина метода (конструктора, блока инициализации)";

    private List<String> calculableUnitTypes;

    public AvgMethodLengthReportUnit() {
        setCode(CODE);
        setDescription(DESCRIPTION);
        setType(DataTypes.DOUBLE.getCode());
        calculableUnitTypes = List.of(
                ExportUnitTypes.METHOD.getType(),
                ExportUnitTypes.CONSTRUCTOR.getType(),
                ExportUnitTypes.INITIALIZER_DECLARATION_UNIT.getType()
        );
    }

    @Override
    public ReportUnit fill(List<ExportUnit> units) {
        ExportMetric defaultMetric = new ExportMetric();
        defaultMetric.setValue("0");
        List<ExportUnit> allUnits = getRecursiveUnits(units);
        Double avgMethodLength = allUnits.stream()
                .filter(u -> (calculableUnitTypes.contains(u.getType())))
                .map(u -> u.getMetrics().get("LOC"))
                .mapToInt(m -> Integer.parseInt(m.getValue()))
                .average()
                .orElseGet(() -> 0.0);
        setValue(String.valueOf(avgMethodLength));
        return this;
    }
}

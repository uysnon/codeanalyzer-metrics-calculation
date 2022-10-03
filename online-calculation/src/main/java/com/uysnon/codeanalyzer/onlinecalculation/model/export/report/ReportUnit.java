package com.uysnon.codeanalyzer.onlinecalculation.model.export.report;

import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class ReportUnit {
    private String code;
    private String description;
    private String value;
    private String type;

    public abstract ReportUnit fill(List<ExportUnit> units);

    protected List<ExportUnit> getRecursiveUnits(List<ExportUnit> units) {
        List<ExportUnit> result = new ArrayList<>();
        units.forEach(u -> innerRecursiveChildrenFind(u, result));
        return result;
    }

    private void innerRecursiveChildrenFind(ExportUnit exportUnit, List<ExportUnit> exportUnits) {
        exportUnits.add(exportUnit);
        exportUnit.getChildren().forEach(u -> {
            innerRecursiveChildrenFind(u, exportUnits);
        });
    }
}

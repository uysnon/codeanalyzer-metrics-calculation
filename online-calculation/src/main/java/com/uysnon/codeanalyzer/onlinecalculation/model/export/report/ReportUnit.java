package com.uysnon.codeanalyzer.onlinecalculation.model.export.report;

import Designite.SourceModel.SM_Project;
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
    private String meta;

    public abstract ReportUnit fill(List<ExportUnit> units, SM_Project designiteProject);

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

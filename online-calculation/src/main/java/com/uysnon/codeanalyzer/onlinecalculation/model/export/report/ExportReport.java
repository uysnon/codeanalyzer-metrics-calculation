package com.uysnon.codeanalyzer.onlinecalculation.model.export.report;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExportReport {
    private String title;
    private String description;
    private List<ReportUnit> reportUnits;

    public ReportUnit getReportUnitByCode(String code) {
        if (code == null) {
            return null;
        }
        return
                reportUnits.stream()
                        .filter(reportUnit -> code.equals(reportUnit.getCode()))
                        .findAny().get();
    }
}

package com.uysnon.codeanalyzer.onlinecalculation.model.export.report;

import com.uysnon.codeanalyzer.onlinecalculation.model.export.ModelCall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportReport {
    private String title;
    private String description;
    private List<ReportUnit> reportUnits;
    private ModelCall modelCall;

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

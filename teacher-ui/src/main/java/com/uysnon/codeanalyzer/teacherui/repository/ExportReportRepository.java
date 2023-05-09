package com.uysnon.codeanalyzer.teacherui.repository;

import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ExportReport;

public interface ExportReportRepository {
    long save(ExportReport report);
    ExportReport get(long id);
}

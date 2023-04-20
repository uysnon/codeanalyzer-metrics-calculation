package com.uysnon.codeanalyzer.webui.repository;

import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ExportReport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryExportReportRepositoryImp implements ExportReportRepository {
    private Long nextId;
    private Map<Long, ExportReport> exportReportMap;


    public InMemoryExportReportRepositoryImp() {
        nextId = 0L;
        exportReportMap = new HashMap<>();
    }

    @Override
    public long save(ExportReport exportReport) {
        exportReportMap.put(nextId, exportReport);
        nextId += 1;
        return nextId - 1;
    }

    @Override
    public ExportReport get(long id) {
        return exportReportMap.get(id);
    }
}

package com.uysnon.codeanalyzer.onlinecalculation.model.export;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Unit для экспорта между микросервисами
 * Хранит только информацию о типе юнита, например метод/класс и тд + метрики + название
 *
 */

@Data
public class ExportUnit {
    private String name;
    private String type;
    private Map<String, ExportMetric> metrics;
    private List<ExportUnit> children;

}

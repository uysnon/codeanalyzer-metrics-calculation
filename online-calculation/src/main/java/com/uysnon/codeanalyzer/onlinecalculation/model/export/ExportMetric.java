package com.uysnon.codeanalyzer.onlinecalculation.model.export;

import com.uysnon.codeanalyzer.core.metrics.Metric;
import lombok.Data;

@Data
public class ExportMetric {
    /**
     * Количественное значение метрики
     */
    private String value;
    /**
     * Специфичность метрики
     */
    private int specificityRate;
    /**
     * Название метрики
     */
    private String code;
    /**
     * Описание метрики
     */
    private String description;
    /**
     * Тип метрики
     */
    private String type;

    public static ExportMetric create(Metric metric) {
        ExportMetric exportMetric = new ExportMetric();
        exportMetric.setType("none");
        exportMetric.setDescription(metric.getDescription());
        exportMetric.setSpecificityRate(metric.getSpecificityRate());
        exportMetric.setCode(metric.getTitle());
        exportMetric.setValue(String.valueOf(metric.getCount()));
        return exportMetric;
    }
}

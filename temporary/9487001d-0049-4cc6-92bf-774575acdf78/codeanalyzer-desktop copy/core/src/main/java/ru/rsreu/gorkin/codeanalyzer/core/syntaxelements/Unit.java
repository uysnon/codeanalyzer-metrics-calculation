package ru.rsreu.gorkin.codeanalyzer.core.syntaxelements;

import ru.rsreu.gorkin.codeanalyzer.core.metrics.Metric;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.base.CodeLinesMetric;

import java.util.ArrayList;
import java.util.List;

/**
 * Синтаксический элемент
 */
public abstract class Unit {
    /**
     * Метрики программного кода
     */
    private List<Metric> metrics;

    public Unit() {
        this.metrics = new ArrayList<>();
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    public abstract void calculateMetrics();

    /**
     * Добавление всех метрик, свойственных элементу
     */
    public void addAllMetrics() {
        addBaseUnitMetrics();
        addCustomUnitMetrics();
    }

    /**
     * Добавление метрики в список метрик
     * @param metric метрика программного кода
     */
    protected void addMetric(Metric metric) {
        if (metrics != null) {
            metrics.add(metric);
        }
    }

    /**
     * Добавление метрик
     * @param metrics список метрик
     */
    protected void addMetrics(Metric... metrics) {
        for (Metric metric : metrics) {
            addMetric(metric);
        }
    }

    /**
     * Добавление свойственных конкретному синтаксическому элементу метрик
     */
    protected abstract void addCustomUnitMetrics();

    /**
     * Добавление общих метрик
     */
    protected void addBaseUnitMetrics() {
        addMetrics(new CodeLinesMetric());
    }
}

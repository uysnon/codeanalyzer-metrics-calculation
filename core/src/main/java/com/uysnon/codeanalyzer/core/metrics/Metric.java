package com.uysnon.codeanalyzer.core.metrics;

import com.github.javaparser.ast.Node;

import java.util.Objects;

/**
 * Метрика программного кода
 * @param <T> Тип анализируемой синтаксической единицы
 */
public abstract class Metric<T extends Node> {
    /**
     * Количественное значение метрики
     */
    private int count;
    /**
     * Специфичность метрики
     */
    private int specificityRate;
    /**
     * Название метрики
     */
    private String title;
    /**
     * Описание метрики
     */
    private String description;

    public Metric() {
        this.specificityRate = 0;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSpecificityRate() {
        return specificityRate;
    }

    public void setSpecificityRate(int specificityRate) {
        this.specificityRate = specificityRate;
    }

    /**
     * Процесс вычисления метрики
     * @param t параметр, на основе которого производятся вычисления
     */
    public abstract void process(T t);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Metric)) return false;
        Metric metric = (Metric) o;
        return count == metric.count &&
                Objects.equals(title, metric.title) &&
                Objects.equals(description, metric.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, title, description);
    }

    @Override
    public String toString() {
        return "Metrics{" +
                "count=" + count +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

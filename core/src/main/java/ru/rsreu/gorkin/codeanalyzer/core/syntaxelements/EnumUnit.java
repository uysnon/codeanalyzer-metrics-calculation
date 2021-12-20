package ru.rsreu.gorkin.codeanalyzer.core.syntaxelements;

import com.github.javaparser.ast.body.EnumDeclaration;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.Metric;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.enums.CountElementsEnumMetric;

public class EnumUnit extends BlockUnit {
    private EnumDeclaration enumDeclaration;

    public EnumUnit(EnumDeclaration enumDeclaration) {
        this.enumDeclaration = enumDeclaration;
        addAllMetrics();
    }

    public EnumDeclaration getEnumDeclaration() {
        return enumDeclaration;
    }

    public void setEnumDeclaration(EnumDeclaration enumDeclaration) {
        this.enumDeclaration = enumDeclaration;
    }

    @Override
    public void calculateMetrics() {
        for (Metric metric : getMetrics()) {
            metric.process(enumDeclaration);
        }
    }

    @Override
    protected void addCustomUnitMetrics() {
        addMetric(new CountElementsEnumMetric());
    }
}

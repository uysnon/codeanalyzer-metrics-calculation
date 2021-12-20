package ru.rsreu.gorkin.codeanalyzer.core.metrics.enums;

import com.github.javaparser.ast.body.EnumDeclaration;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.Metric;

public abstract class EnumMetric extends Metric<EnumDeclaration> {
    private static final int SPECIFICITY_RATE = 2;

    public EnumMetric() {
        setSpecificityRate(SPECIFICITY_RATE);
    }
}

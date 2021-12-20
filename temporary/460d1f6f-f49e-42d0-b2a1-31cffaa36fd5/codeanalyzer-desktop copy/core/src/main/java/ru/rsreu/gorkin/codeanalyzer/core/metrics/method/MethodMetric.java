package ru.rsreu.gorkin.codeanalyzer.core.metrics.method;

import com.github.javaparser.ast.body.MethodDeclaration;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.Metric;

public abstract class MethodMetric extends Metric<MethodDeclaration> {
    private static final int SPECIFICITY_RATE = 2;

    public MethodMetric() {
        setSpecificityRate(SPECIFICITY_RATE);
    }
}

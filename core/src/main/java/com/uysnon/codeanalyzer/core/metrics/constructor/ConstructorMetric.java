package com.uysnon.codeanalyzer.core.metrics.constructor;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.uysnon.codeanalyzer.core.metrics.Metric;

public abstract class ConstructorMetric extends Metric<ConstructorDeclaration> {
    private static final int SPECIFICITY_RATE = 2;

    public ConstructorMetric() {
        setSpecificityRate(SPECIFICITY_RATE);
    }
}

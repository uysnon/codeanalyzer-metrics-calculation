package com.uysnon.codeanalyzer.core.metrics.classorinterface;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.uysnon.codeanalyzer.core.metrics.Metric;

public abstract class ClassMetric extends Metric<ClassOrInterfaceDeclaration> {
    private static final int SPECIFICITY_RATE = 2;

    public ClassMetric() {
        setSpecificityRate(SPECIFICITY_RATE);
    }
}

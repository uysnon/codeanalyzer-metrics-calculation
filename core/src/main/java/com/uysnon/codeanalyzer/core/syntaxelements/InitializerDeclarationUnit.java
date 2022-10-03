package com.uysnon.codeanalyzer.core.syntaxelements;

import com.github.javaparser.ast.body.InitializerDeclaration;
import com.uysnon.codeanalyzer.core.metrics.Metric;

public class InitializerDeclarationUnit extends BlockUnit {
    private InitializerDeclaration initializerDeclaration;

    public InitializerDeclarationUnit(InitializerDeclaration initializerDeclaration) {
        this.initializerDeclaration = initializerDeclaration;
        addAllMetrics();
    }

    public InitializerDeclaration getInitializerDeclaration() {
        return initializerDeclaration;
    }

    public void setInitializerDeclaration(InitializerDeclaration initializerDeclaration) {
        this.initializerDeclaration = initializerDeclaration;
    }

    @Override
    public void calculateMetrics() {
        for (Metric metric : getMetrics()) {
            metric.process(initializerDeclaration);
        }
    }

    @Override
    protected void addCustomUnitMetrics() {
    }
}

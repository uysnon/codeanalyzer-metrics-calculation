package ru.rsreu.gorkin.codeanalyzer.core.syntaxelements;

import com.github.javaparser.ast.body.InitializerDeclaration;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.Metric;

public class InitializerDeclarationUnit extends BlockUnit {
    private InitializerDeclaration initializerDeclaration;

    public InitializerDeclarationUnit(InitializerDeclaration initializerDeclaration) {
        this.initializerDeclaration = initializerDeclaration;
        addAllMetrics();
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

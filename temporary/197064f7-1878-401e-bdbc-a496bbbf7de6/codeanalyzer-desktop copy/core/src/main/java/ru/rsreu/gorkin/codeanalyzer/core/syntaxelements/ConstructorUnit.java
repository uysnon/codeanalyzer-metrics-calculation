package ru.rsreu.gorkin.codeanalyzer.core.syntaxelements;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.Metric;

public class ConstructorUnit extends BlockUnit {
    private ConstructorDeclaration constructorDeclaration;

    public ConstructorUnit(ConstructorDeclaration constructorDeclaration) {
        this.constructorDeclaration = constructorDeclaration;
        addAllMetrics();
    }

    public ConstructorDeclaration getConstructorDeclaration() {
        return constructorDeclaration;
    }

    public void setConstructorDeclaration(ConstructorDeclaration constructorDeclaration) {
        this.constructorDeclaration = constructorDeclaration;
    }

    @Override
    public void calculateMetrics() {
        for (Metric metric : getMetrics()) {
            metric.process(constructorDeclaration);
        }
    }

    @Override
    protected void addCustomUnitMetrics() {

    }
}

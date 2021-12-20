package ru.rsreu.gorkin.codeanalyzer.core.syntaxelements;

import com.github.javaparser.ast.body.MethodDeclaration;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.Metric;

public class MethodUnit extends BlockUnit {
    private MethodDeclaration methodDeclaration;


    public MethodUnit(MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
        addAllMetrics();
    }

    public MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
    }

    @Override
    protected void addCustomUnitMetrics() {

    }

    @Override
    public void calculateMetrics() {
        for (Metric metric : getMetrics()) {
            metric.process(methodDeclaration);
        }
    }


}

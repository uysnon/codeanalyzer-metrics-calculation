package com.uysnon.codeanalyzer.core.syntaxelements;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.uysnon.codeanalyzer.core.metrics.classorinterface.FieldsClassMetric;
import com.uysnon.codeanalyzer.core.metrics.classorinterface.InitializerDeclarationsClassMetric;
import com.uysnon.codeanalyzer.core.metrics.classorinterface.MethodsClassMetric;
import com.uysnon.codeanalyzer.core.metrics.classorinterface.StaticInitializerDeclarationsClassMetric;

import java.util.ArrayList;
import java.util.List;

public class ClassOrInterfaceUnit extends BlockUnit {
    private ClassOrInterfaceDeclaration classOrInterfaceDeclaration;
    private List<MethodUnit> methodUnits;
    private List<InitializerDeclarationUnit> initializerDeclarationUnits;
    private List<ConstructorUnit> constructorUnits;
    private List<FieldUnit> fieldUnits;

    public ClassOrInterfaceUnit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        this.classOrInterfaceDeclaration = classOrInterfaceDeclaration;
        this.methodUnits = new ArrayList<>();
        this.initializerDeclarationUnits = new ArrayList<>();
        this.constructorUnits = new ArrayList<>();
        this.fieldUnits = new ArrayList<>();
        addAllMetrics();
    }

    public ClassOrInterfaceDeclaration getClassOrInterfaceDeclaration() {
        return classOrInterfaceDeclaration;
    }

    public void setClassOrInterfaceDeclaration(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        this.classOrInterfaceDeclaration = classOrInterfaceDeclaration;
    }

    public List<MethodUnit> getMethodUnits() {
        return methodUnits;
    }

    public void setMethodUnits(List<MethodUnit> methodUnits) {
        this.methodUnits = methodUnits;
    }

    public List<InitializerDeclarationUnit> getInitializerDeclarationUnits() {
        return initializerDeclarationUnits;
    }

    public void setInitializerDeclarationUnits(List<InitializerDeclarationUnit> initializerDeclarationUnits) {
        this.initializerDeclarationUnits = initializerDeclarationUnits;
    }

    public List<ConstructorUnit> getConstructorUnits() {
        return constructorUnits;
    }

    public void setConstructorUnits(List<ConstructorUnit> constructorUnits) {
        this.constructorUnits = constructorUnits;
    }

    public List<FieldUnit> getFieldUnits() {
        return fieldUnits;
    }

    public void setFieldUnits(List<FieldUnit> fieldUnits) {
        this.fieldUnits = fieldUnits;
    }

    @Override
    public void calculateMetrics() {
        getMetrics().forEach(metric -> metric.process(classOrInterfaceDeclaration));
        constructorUnits.forEach(ConstructorUnit::calculateMetrics);
        methodUnits.forEach(MethodUnit::calculateMetrics);
        fieldUnits.forEach(FieldUnit::calculateMetrics);
        initializerDeclarationUnits.forEach(InitializerDeclarationUnit::calculateMetrics);
    }

    @Override
    protected void addCustomUnitMetrics() {
        addMetrics(new FieldsClassMetric(),
                new MethodsClassMetric(),
                new InitializerDeclarationsClassMetric(),
                new StaticInitializerDeclarationsClassMetric());
    }
}

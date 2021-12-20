package ru.rsreu.gorkin.codeanalyzer.core.syntaxelements;

import com.github.javaparser.ast.CompilationUnit;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.Metric;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SourceCodeUnit extends BlockUnit {
    private String fileName;
    private CompilationUnit compilationUnit;
    private List<ClassOrInterfaceUnit> classOrInterfaceUnits;
    private List<EnumUnit> enumUnits;

    public SourceCodeUnit(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
        classOrInterfaceUnits = new ArrayList<>();
        enumUnits = new ArrayList<>();
        addAllMetrics();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    public void setCompilationUnit(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    public List<ClassOrInterfaceUnit> getClassOrInterfaceUnits() {
        return classOrInterfaceUnits;
    }

    public void setClassOrInterfaceUnits(List<ClassOrInterfaceUnit> classOrInterfaceUnits) {
        this.classOrInterfaceUnits = classOrInterfaceUnits;
    }

    public List<EnumUnit> getEnumUnits() {
        return enumUnits;
    }

    public void setEnumUnits(List<EnumUnit> enumUnits) {
        this.enumUnits = enumUnits;
    }

    @Override
    public void calculateMetrics() {
        for (Metric metric : getMetrics()) {
            metric.process(compilationUnit);
        }
        classOrInterfaceUnits.forEach(ClassOrInterfaceUnit::calculateMetrics);
        enumUnits.forEach(EnumUnit::calculateMetrics);
    }

    @Override
    protected void addCustomUnitMetrics() {

    }
}

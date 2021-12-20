package ru.rsreu.gorkin.codeanalyzer.core.metrics.enums;

import com.github.javaparser.ast.body.EnumDeclaration;

public class CountElementsEnumMetric extends EnumMetric {
    @Override
    public void process(EnumDeclaration enumDeclaration) {
        setCount(enumDeclaration.getEntries().size());
    }
}

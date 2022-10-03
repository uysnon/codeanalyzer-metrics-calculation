package com.uysnon.codeanalyzer.core.metrics.enums;

import com.github.javaparser.ast.body.EnumDeclaration;

public class CountElementsEnumMetric extends EnumMetric {

    private static final String TITLE = "ENUM_ELEMENTS_COUNT";
    private static final String DESCRIPTION = "Количество switch выражений";

    public CountElementsEnumMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    @Override
    public void process(EnumDeclaration enumDeclaration) {
        setCount(enumDeclaration.getEntries().size());
    }
}

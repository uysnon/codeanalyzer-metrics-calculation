package ru.rsreu.gorkin.codeanalyzer.core.metrics.base;

import com.github.javaparser.ast.Node;

public class RABCMetric extends ABCMetric {
    private static final String TITLE = "RABC метрика";
    private static final String DESCRIPTION = "ABC метрика приходящаяся на 1 строку кода * 100";

    public RABCMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    @Override
    public void process(Node node) {
        CodeLinesMetric codeLinesMetric = new CodeLinesMetric();
        codeLinesMetric.process(node);
        setCount((int) Math.round((getABCValue(node) / codeLinesMetric.getCount()) * 100));
    }
}

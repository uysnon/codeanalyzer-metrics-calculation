package com.uysnon.codeanalyzer.core.metrics.base;

import com.github.javaparser.Position;
import com.github.javaparser.Range;
import com.github.javaparser.ast.Node;

public class CodeLinesMetric extends BaseMetric {
    private static final String TITLE = "LOC";
    private static final String DESCRIPTION = "Количество строк кода, занимаемое элементом";

    public CodeLinesMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    @Override
    public void process(Node node) {
        setCount(node.getRange().orElse(
                new Range(
                        new Position(0, 0),
                        new Position(0, 0)))
                .getLineCount());
    }
}

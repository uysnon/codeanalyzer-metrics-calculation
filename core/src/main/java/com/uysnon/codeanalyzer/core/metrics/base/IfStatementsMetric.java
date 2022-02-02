package com.uysnon.codeanalyzer.core.metrics.base;

import com.github.javaparser.ast.Node;
import com.uysnon.codeanalyzer.core.metrics.utils.BlockStatementsCounter;

public class IfStatementsMetric extends BlockMetric {
    private static final String TITLE = "if выражения";
    private static final String DESCRIPTION = "Количество if выражений";

    public IfStatementsMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    @Override
    public void process(Node node) {
        BlockStatementsCounter blockStatementsCounter = new BlockStatementsCounter();
        setCount(blockStatementsCounter.calculateRecursiveIfStatements(node));
    }
}

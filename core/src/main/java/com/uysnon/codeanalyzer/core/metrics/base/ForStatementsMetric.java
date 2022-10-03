    package com.uysnon.codeanalyzer.core.metrics.base;

import com.github.javaparser.ast.Node;
import com.uysnon.codeanalyzer.core.metrics.utils.BlockStatementsCounter;

    public class ForStatementsMetric extends BlockMetric {
    private static final String TITLE = "FOR_COUNT";
    private static final String DESCRIPTION = "Количество циклов for";

    public ForStatementsMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    @Override
    public void process(Node node) {
        BlockStatementsCounter blockStatementsCounter = new BlockStatementsCounter();
        setCount(blockStatementsCounter.calculateRecursiveForStatements(node));
    }
}

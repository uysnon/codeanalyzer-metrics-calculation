package com.uysnon.codeanalyzer.core.metrics.base;

import com.github.javaparser.ast.Node;
import com.uysnon.codeanalyzer.core.metrics.utils.BlockStatementsCounter;

public class ForEachStatementsMetric extends BlockMetric {
    private static final String TITLE = "FOREACH_COUNT";
    private static final String DESCRIPTION = "Количество циклов foreach";

    public ForEachStatementsMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    @Override
    public void process(Node node) {
        BlockStatementsCounter blockStatementsCounter = new BlockStatementsCounter();
        setCount(blockStatementsCounter.calculateRecursiveForEachStatements(node));
    }
}

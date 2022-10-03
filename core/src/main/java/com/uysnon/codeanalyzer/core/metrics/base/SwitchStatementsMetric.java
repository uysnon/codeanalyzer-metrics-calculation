package com.uysnon.codeanalyzer.core.metrics.base;

import com.github.javaparser.ast.Node;
import com.uysnon.codeanalyzer.core.metrics.utils.BlockStatementsCounter;

public class SwitchStatementsMetric extends BlockMetric {
    private static final String TITLE = "SWITCH_COUNT";
    private static final String DESCRIPTION = "Количество switch выражений";

    public SwitchStatementsMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    @Override
    public void process(Node node) {
        BlockStatementsCounter blockStatementsCounter = new BlockStatementsCounter();
        setCount(blockStatementsCounter.calculateRecursiveSwitchStatements(node));
    }
}

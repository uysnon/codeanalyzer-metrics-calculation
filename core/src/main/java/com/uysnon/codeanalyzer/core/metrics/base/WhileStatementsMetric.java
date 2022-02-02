package com.uysnon.codeanalyzer.core.metrics.base;

import com.github.javaparser.ast.Node;
import com.uysnon.codeanalyzer.core.metrics.utils.BlockStatementsCounter;

public class WhileStatementsMetric extends BlockMetric {
    private static final String TITLE = "цикл while";
    private static final String DESCRIPTION = "Количество циклов while(statement){..}";

    public WhileStatementsMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    @Override
    public void process(Node node) {
        BlockStatementsCounter blockStatementsCounter = new BlockStatementsCounter();
        setCount(blockStatementsCounter.calculateRecursiveWhileStatements(node));
    }
}

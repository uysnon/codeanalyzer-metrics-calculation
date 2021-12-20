    package ru.rsreu.gorkin.codeanalyzer.core.metrics.base;

import com.github.javaparser.ast.Node;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.utils.BlockStatementsCounter;

public class ForStatementsMetric extends BlockMetric {
    private static final String TITLE = "цикл for";
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

package ru.rsreu.gorkin.codeanalyzer.core.metrics.base;

import com.github.javaparser.ast.Node;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.utils.BlockStatementsCounter;

public class SwitchStatementsMetric extends BlockMetric {
    private static final String TITLE = "switch выражения";
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

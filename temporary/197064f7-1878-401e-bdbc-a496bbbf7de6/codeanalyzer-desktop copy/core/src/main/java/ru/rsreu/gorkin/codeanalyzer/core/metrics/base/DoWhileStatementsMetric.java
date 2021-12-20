package ru.rsreu.gorkin.codeanalyzer.core.metrics.base;

import com.github.javaparser.ast.Node;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.utils.BlockStatementsCounter;

public class DoWhileStatementsMetric extends BlockMetric {
    private static final String TITLE = "цикл do-while";
    private static final String DESCRIPTION = "Количество циклов do{..}while(statement);";

    public DoWhileStatementsMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    @Override
    public void process(Node node) {
        BlockStatementsCounter blockStatementsCounter = new BlockStatementsCounter();
        setCount(blockStatementsCounter.calculateRecursiveDoWhileStatements(node));
    }
}

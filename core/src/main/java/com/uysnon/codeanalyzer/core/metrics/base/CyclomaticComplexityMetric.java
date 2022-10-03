package com.uysnon.codeanalyzer.core.metrics.base;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.stmt.*;

public class CyclomaticComplexityMetric extends BlockMetric {
    private static final String TITLE = "CYCL_COMPL";
    private static final String DESCRIPTION = "Цикломатическая сложность кода";

    public CyclomaticComplexityMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    @Override
    public void process(Node node) {
        int startValue = 0;
        setCount(calculate(node, startValue));
    }

    private int calculate(Node node, int value) {
        if (isConditionalConstruction(node)) {
            value++;
        }
        for (Node innerNode : node.getChildNodes()) {
            value = calculate(innerNode, value);
        }
        return value;
    }

    private boolean isConditionalConstruction(Node node) {
        boolean result =
                node instanceof ConditionalExpr
                        || node instanceof CatchClause
                        || node instanceof WhileStmt
                        || node instanceof DoStmt
                        || node instanceof ForEachStmt
                        || node instanceof ForStmt
                        || node instanceof SwitchStmt
                        || node instanceof SwitchEntry;
        if (result) {
            return result;
        }
        if (node instanceof BinaryExpr && (
                (((BinaryExpr) node).getOperator() == BinaryExpr.Operator.EQUALS)
                        || (((BinaryExpr) node).getOperator() == BinaryExpr.Operator.OR)
                        || (((BinaryExpr) node).getOperator() == BinaryExpr.Operator.AND)
                        || (((BinaryExpr) node).getOperator() == BinaryExpr.Operator.XOR))
        ) {
            return true;
        }
        return false;
    }

    private void incCount() {
        setCount(getCount() + 1);
    }
}

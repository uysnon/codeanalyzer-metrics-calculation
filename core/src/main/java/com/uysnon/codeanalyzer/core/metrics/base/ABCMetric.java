package com.uysnon.codeanalyzer.core.metrics.base;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.printer.PrettyPrinter;

public class ABCMetric extends BlockMetric {
    private static final String TITLE = "ABC";
    private static final String DESCRIPTION = "ABC метрика";

    public ABCMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    @Override
    public void process(Node node) {
        setCount((int) Math.round(getABCValue(node)));
    }

    protected double getABCValue(Node node) {
        ABC abc = new ABC();
        abc.process(node);
        return abc.getValue();
    }

    private class ABC {
        int a;
        int b;
        int c;

        double getValue() {
            return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2));
        }

        void process(Node node) {
            if (isAssigment(node)) {
                a++;
            }
            if (isBranch(node)) {
                b++;
            }
            if (isCondition(node)) {
                c++;
            }
            for (Node innerNode : node.getChildNodes()) {
                process(innerNode);
            }
        }

        boolean isAssigment(Node node) {
            return (node instanceof AssignExpr
                    || (node instanceof UnaryExpr && (
                    new PrettyPrinter().print(node).contains("++")
                            || new PrettyPrinter().print(node).contains("--")
            ))
            );
        }

        boolean isBranch(Node node) {
            return (node instanceof MethodCallExpr);
        }

        boolean isCondition(Node node) {
            return (node instanceof ConditionalExpr
                    || node instanceof CatchClause
                    || node instanceof TryStmt
                    || (node instanceof BinaryExpr
                    && (
                    (((BinaryExpr) node).getOperator() == BinaryExpr.Operator.EQUALS)
                            || (((BinaryExpr) node).getOperator() == BinaryExpr.Operator.LESS)
                            || (((BinaryExpr) node).getOperator() == BinaryExpr.Operator.GREATER)
                            || (((BinaryExpr) node).getOperator() == BinaryExpr.Operator.NOT_EQUALS)
                            || (((BinaryExpr) node).getOperator() == BinaryExpr.Operator.LESS_EQUALS)
                            || (((BinaryExpr) node).getOperator() == BinaryExpr.Operator.GREATER_EQUALS)
            )));
        }
    }
}

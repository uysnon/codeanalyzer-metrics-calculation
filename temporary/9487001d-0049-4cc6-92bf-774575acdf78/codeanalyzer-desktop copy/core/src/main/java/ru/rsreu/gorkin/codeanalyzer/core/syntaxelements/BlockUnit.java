package ru.rsreu.gorkin.codeanalyzer.core.syntaxelements;

import ru.rsreu.gorkin.codeanalyzer.core.metrics.base.*;

    public abstract class BlockUnit extends Unit {
        @Override
        protected void addBaseUnitMetrics() {
            super.addBaseUnitMetrics();
            addMetrics(
                    new IfStatementsMetric(),
                    new SwitchStatementsMetric(),
                    new ForStatementsMetric(),
                    new ForEachStatementsMetric(),
                    new WhileStatementsMetric(),
                    new DoWhileStatementsMetric(),
                    new ABCMetric(),
                    new RABCMetric(),
                    new CyclomaticComplexityMetric()
            );
        }
    }

package ru.rsreu.gorkin.codeanalyzer.desktop.metrics;

import ru.rsreu.gorkin.codeanalyzer.core.metrics.Metric;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.base.*;

public enum SourceFileMetrics {
    CODE_LINES_METRIC("LOC") {
        @Override
        public boolean isMe(Metric metric) {
            return metric instanceof CodeLinesMetric;
        }
    },
    FOR_STATEMENTS_METRIC("FST") {
        @Override
        public boolean isMe(Metric metric) {
            return metric instanceof ForStatementsMetric;
        }
    },
    FOR_EACH_STATEMENTS_METRIC("FEST") {
        @Override
        public boolean isMe(Metric metric) {
            return metric instanceof ForEachStatementsMetric;
        }
    },
    WHILE_STATEMENTS_METRIC("WST") {
        @Override
        public boolean isMe(Metric metric) {
            return metric instanceof WhileStatementsMetric;
        }
    },
    DO_WHILE_STATEMENTS_METRIC("DWST") {
        @Override
        public boolean isMe(Metric metric) {
            return metric instanceof DoWhileStatementsMetric;
        }
    },
    IF_STATEMENTS_METRIC("IFST") {
        @Override
        public boolean isMe(Metric metric) {
            return metric instanceof IfStatementsMetric;
        }
    },
    SWITCH_STATEMENTS_METRIC("SWST") {
        @Override
        public boolean isMe(Metric metric) {
            return metric instanceof SwitchStatementsMetric;
        }
    },
    ABC("ABC") {
        @Override
        public boolean isMe(Metric metric) {
            return metric.getClass() == ABCMetric.class;
        }
    },
    RABC("RABC") {
        @Override
        public boolean isMe(Metric metric) {
            return metric.getClass() == RABCMetric.class;
        }
    },
    CC("CC") {
        @Override
        public boolean isMe(Metric metric) {
            return metric.getClass() == CyclomaticComplexityMetric.class;
        }
    };

    private String shortName;

    SourceFileMetrics(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static String getShortName(Metric metric) {
        for (SourceFileMetrics sourceFileMetrics : SourceFileMetrics.values()) {
            if (sourceFileMetrics.isMe(metric)) {
                return sourceFileMetrics.shortName;
            }
        }
        return "unknown";
    }

    public abstract boolean isMe(Metric metric);
}

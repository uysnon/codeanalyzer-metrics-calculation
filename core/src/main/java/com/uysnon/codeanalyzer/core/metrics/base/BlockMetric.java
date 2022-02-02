package com.uysnon.codeanalyzer.core.metrics.base;

public abstract class BlockMetric extends BaseMetric{
    private static final int SPECIFICITY_RATE = 1;

    public BlockMetric() {
        setSpecificityRate(SPECIFICITY_RATE);
    }
}

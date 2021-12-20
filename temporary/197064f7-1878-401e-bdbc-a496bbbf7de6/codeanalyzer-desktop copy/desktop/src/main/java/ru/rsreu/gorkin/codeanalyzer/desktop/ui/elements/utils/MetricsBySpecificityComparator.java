package ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.utils;

import ru.rsreu.gorkin.codeanalyzer.core.metrics.Metric;

import java.util.Comparator;

public class MetricsBySpecificityComparator implements Comparator<Metric> {
    @Override
    public int compare(Metric o1, Metric o2) {
        int compareSpecificityResult = o2.getSpecificityRate() - o1.getSpecificityRate();
        if (compareSpecificityResult == 0) {
            compareSpecificityResult = o1.getTitle().compareTo(o2.getDescription());
        }
        return compareSpecificityResult;
    }
}

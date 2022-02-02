package com.uysnon.codeanalyzer.core.metrics.enums;

import com.github.javaparser.StaticJavaParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.uysnon.codeanalyzer.core.adapters.TreeCreator;
import com.uysnon.codeanalyzer.core.syntaxelements.SourceCodeUnit;

public class CountElementsEnumMetricTest {
    CountElementsEnumMetric countElementsEnumMetric = new CountElementsEnumMetric();

    @DisplayName("test process() method")
    @Test
    void testProcess() {
        TreeCreator treeCreator = new TreeCreator();
        SourceCodeUnit sourceCodeUnit =
                treeCreator.createSourceCodeUnit(
                        StaticJavaParser.parse(getEnumString())
                );
        countElementsEnumMetric.process(sourceCodeUnit.getEnumUnits().get(0).getEnumDeclaration());
        Assertions.assertEquals(7, countElementsEnumMetric.getCount());
    }


    private String getEnumString() {
        return "\n" +
                "public enum DayOfWeek {\n" +
                "\n" +
                "   SUNDAY,\n" +
                "   MONDAY,\n" +
                "   TUESDAY,\n" +
                "   WEDNESDAY,\n" +
                "   THURSDAY,\n" +
                "   FRIDAY,\n" +
                "   SATURDAY\n" +
                "}";
    }
}

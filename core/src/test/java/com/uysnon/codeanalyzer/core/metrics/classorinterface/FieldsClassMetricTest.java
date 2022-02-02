package com.uysnon.codeanalyzer.core.metrics.classorinterface;

import com.github.javaparser.StaticJavaParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.uysnon.codeanalyzer.core.adapters.TreeCreator;
import com.uysnon.codeanalyzer.core.syntaxelements.SourceCodeUnit;

public class FieldsClassMetricTest {
    FieldsClassMetric fieldsClassMetric = new FieldsClassMetric();

    @DisplayName("test process() method")
    @Test
    void testProcess() {
        TreeCreator treeCreator = new TreeCreator();
        SourceCodeUnit sourceCodeUnit =
                treeCreator.createSourceCodeUnit(
                        StaticJavaParser.parse(getCarClassString())
                );
        fieldsClassMetric.process(sourceCodeUnit.getClassOrInterfaceUnits().get(0).getClassOrInterfaceDeclaration());
        Assertions.assertEquals(4, fieldsClassMetric.getCount());
    }

    private String getCarClassString() {
        return "package ru.rsreu.cars;\n" +
                "public class Car \n" +
                "{\n" +
                "    private int yearModel;\n" +
                "    private String brand;\n" +
                "    private int priceModel;\n" +
                "    private int numberModel;\n" +
                "\n" +
                "    public Car(String b, int year, int price, int number)\n" +
                "    {\n" +
                "        yearModel = year;\n" +
                "        brand = b;\n" +
                "        priceModel = price;\n" +
                "        numberModel = number;      \n" +
                "    }\n" +
                "\n" +
                "    public int getYear()                \n" +
                "    {\n" +
                "        return yearModel;\n" +
                "    }\n" +
                "\n" +
                "    public String getBrand()\n" +
                "    {\n" +
                "        return brand;\n" +
                "    }\n" +
                "\n" +
                "    public int getPrice()\n" +
                "    {\n" +
                "        return priceModel;\n" +
                "    }\n" +
                "\n" +
                "    public int getNumber()\n" +
                "    {\n" +
                "        return numberModel;\n" +
                "    }\n" +
                "\n" +
                "    public void setYear(int year)\n" +
                "    {\n" +
                "        yearModel = year;\n" +
                "    }\n" +
                "\n" +
                "    public void setBrand(String carBrand)\n" +
                "    {\n" +
                "        brand = carBrand;\n" +
                "    }\n" +
                "\n" +
                "    public void setPrice(int price)                        \n" +
                "    {\n" +
                "        priceModel = price;   \n" +
                "}\n" +
                "    public void setNumber(int number)\n" +
                "    {\n" +
                "        numberModel = number;\n" +
                "    }\n" +
                "}";
    }
}

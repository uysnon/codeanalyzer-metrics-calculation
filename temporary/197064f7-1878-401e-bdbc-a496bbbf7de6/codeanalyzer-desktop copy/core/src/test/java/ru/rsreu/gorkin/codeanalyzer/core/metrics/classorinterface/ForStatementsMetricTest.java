package ru.rsreu.gorkin.codeanalyzer.core.metrics.classorinterface;

import com.github.javaparser.StaticJavaParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rsreu.gorkin.codeanalyzer.core.adapters.TreeCreator;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.base.ForStatementsMetric;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.SourceCodeUnit;

public class ForStatementsMetricTest {
    ForStatementsMetric metric = new ForStatementsMetric();

    @DisplayName("test process() method")
    @Test
    void testProcess() {
        TreeCreator treeCreator = new TreeCreator();
        SourceCodeUnit sourceCodeUnit =
                treeCreator.createSourceCodeUnit(
                        StaticJavaParser.parse(getCarClassString())
                );
        metric.process(sourceCodeUnit.getCompilationUnit());
        Assertions.assertEquals(2, metric.getCount());
    }

    private String getCarClassString() {
        return "package ru.rsreu.gorkin.codeanalyzer.core.metrics.classorinterface;\n" +
                "\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "\n" +
                "public class Car {\n" +
                "    private int yearModel;\n" +
                "    private String brand;\n" +
                "    private int priceModel;\n" +
                "    private int numberModel;\n" +
                "\n" +
                "    public Car(String b, int year, int price, int number) {\n" +
                "        yearModel = year;\n" +
                "        brand = b;\n" +
                "        priceModel = price;\n" +
                "        numberModel = number;\n" +
                "    }\n" +
                "\n" +
                "    public int getYear() {\n" +
                "        List<String> list = new ArrayList<>();\n" +
                "        for(int i = 0; i < list.size(); i++){\n" +
                "\n" +
                "        }\n" +
                "        for(int i = 0; i < list.size(); i++){\n" +
                "\n" +
                "        }\n" +
                "        return yearModel;\n" +
                "    }\n" +
                "\n" +
                "    public String getBrand() {\n" +
                "        return brand;\n" +
                "    }\n" +
                "\n" +
                "    public int getPrice() {\n" +
                "        return priceModel;\n" +
                "    }\n" +
                "\n" +
                "    public int getNumber() {\n" +
                "        return numberModel;\n" +
                "    }\n" +
                "\n" +
                "    public void setYear(int year) {\n" +
                "        yearModel = year;\n" +
                "    }\n" +
                "\n" +
                "    public void setBrand(String carBrand) {\n" +
                "        brand = carBrand;\n" +
                "    }\n" +
                "\n" +
                "    public void setPrice(int price) {\n" +
                "        priceModel = price;\n" +
                "    }\n" +
                "\n" +
                "    public void setNumber(int number) {\n" +
                "        numberModel = number;\n" +
                "    }\n" +
                "}";
    }
}

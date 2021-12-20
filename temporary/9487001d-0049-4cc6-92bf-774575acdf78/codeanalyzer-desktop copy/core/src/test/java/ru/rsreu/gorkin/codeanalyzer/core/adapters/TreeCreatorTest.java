package ru.rsreu.gorkin.codeanalyzer.core.adapters;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import org.mockito.junit.jupiter.MockitoExtension;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.ProjectTree;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.SourceCodeUnit;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class TreeCreatorTest {

    @Spy
    TreeCreator treeCreator;

    @DisplayName("test create(Collection<CompilationUnit> compilationUnits) method")
    @Test
    void testCreateWithCollectionCompilationUnitsParam() {
        CompilationUnit compilationUnit1 = StaticJavaParser.parse(getCarClassString());
        CompilationUnit compilationUnit2 = StaticJavaParser.parse(getAirplaneClassString());
        Collection<CompilationUnit> compilationUnitCollection = Stream.of(
                compilationUnit1,
                compilationUnit2).collect(Collectors.toList());
        ProjectTree projectTree = treeCreator.create(compilationUnitCollection);
        Assertions.assertEquals(2, projectTree.getSourceCodeUnits().size());
        Mockito.verify(treeCreator, Mockito.times(2))
                .createSourceCodeUnit(Mockito.any(CompilationUnit.class));
    }

    @DisplayName("test createSourceCodeUnit(CompilationUnit compilationUnit) method")
    @Test
    void testCreateSourceCodeUnitWithCompilationUnitParam() {
        CompilationUnit compilationUnit = StaticJavaParser.parse(getCarClassString());
        SourceCodeUnit sourceCodeUnit = treeCreator.createSourceCodeUnit(compilationUnit);
        Assertions.assertEquals(1, sourceCodeUnit.getClassOrInterfaceUnits().size());
        Assertions.assertEquals(1, sourceCodeUnit.getClassOrInterfaceUnits().get(0).getConstructorUnits().size());
        Assertions.assertEquals(4, sourceCodeUnit.getClassOrInterfaceUnits().get(0).getFieldUnits().size());
        Assertions.assertEquals(8, sourceCodeUnit.getClassOrInterfaceUnits().get(0).getMethodUnits().size());
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


    private String getAirplaneClassString() {
        return "package ru.rsreu.airplanes;\n" +
                "public class Airplane \n" +
                "{\n" +
                "    private int yearModel;\n" +
                "    private String brand;\n" +
                "    private int priceModel;\n" +
                "    private int numberModel;\n" +
                "\n" +
                "    public Airplane(String b, int year, int price, int number)\n" +
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

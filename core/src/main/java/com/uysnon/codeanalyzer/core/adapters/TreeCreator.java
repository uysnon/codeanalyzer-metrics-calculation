package com.uysnon.codeanalyzer.core.adapters;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.uysnon.codeanalyzer.core.syntaxelements.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, отвечающий за формирование дерева проекта
 */
public class TreeCreator {
    /**
     * Формирование дерева проекта на основе списка CompileUnit объектов
     *
     * @param compilationUnits список файлов с выявленными синтаксическими единицами, формируется
     *                         с помощью библиотеки JavaParser
     * @return дерево проекта
     */
    public ProjectTree create(Collection<CompilationUnit> compilationUnits) {
        ProjectTree tree = new ProjectTree();
        compilationUnits.forEach(
                compilationUnit -> tree.getSourceCodeUnits().add(createSourceCodeUnit(compilationUnit)));
        return tree;
    }

    /**
     * Формирование древовидного представления файла с исходным кодом на основе
     * CompileUnit объекта
     *
     * @param compilationUnit файл с выявленными синтаксическими единицами, формируется
     *                        с помощью библиотеки JavaParser
     * @return древовидное представления файла с исходным кодом
     */
    public SourceCodeUnit createSourceCodeUnit(CompilationUnit compilationUnit) {
        SourceCodeUnit codeUnit = new SourceCodeUnit(compilationUnit);
        List<ClassOrInterfaceUnit> classes = compilationUnit.getTypes().stream()
                .filter(node -> node instanceof ClassOrInterfaceDeclaration)
                .map(node -> new ClassOrInterfaceUnit((ClassOrInterfaceDeclaration) node))
                .collect(Collectors.toList());
        codeUnit.getClassOrInterfaceUnits().addAll(classes);

        List<EnumUnit> enums = compilationUnit.getTypes().stream()
                .filter(node -> node instanceof EnumDeclaration)
                .map(node -> new EnumUnit((EnumDeclaration) node))
                .collect(Collectors.toList());
        codeUnit.getEnumUnits().addAll(enums);
        classes.forEach(
                element -> element.getClassOrInterfaceDeclaration().getMembers().stream()
                        .filter(node -> node instanceof MethodDeclaration)
                        .map(node -> new MethodUnit((MethodDeclaration) node))
                        .forEach(methodUnit -> element.getMethodUnits().add(methodUnit))
        );
        classes.forEach(
                element -> element.getClassOrInterfaceDeclaration().getMembers().stream()
                        .filter(node -> node instanceof InitializerDeclaration)
                        .map(node -> new InitializerDeclarationUnit((InitializerDeclaration) node))
                        .forEach(initializerDeclarationUnit -> element.getInitializerDeclarationUnits().add(initializerDeclarationUnit))
        );
        classes.forEach(
                element -> element.getClassOrInterfaceDeclaration().getMembers().stream()
                        .filter(node -> node instanceof FieldDeclaration)
                        .map(node -> new FieldUnit((FieldDeclaration) node))
                        .forEach(fieldUnit -> element.getFieldUnits().add(fieldUnit))
        );
        classes.forEach(
                element -> element.getClassOrInterfaceDeclaration().getMembers().stream()
                        .filter(node -> node instanceof ConstructorDeclaration)
                        .map(node -> new ConstructorUnit((ConstructorDeclaration) node))
                        .forEach(constructorUnit -> element.getConstructorUnits().add(constructorUnit))
        );
        codeUnit.calculateMetrics();
        return codeUnit;
    }

    /**
     * Формирование древовидного представления файла с исходным кодом на основе
     * файла с исходном кодом на языке Java
     *
     * @param file файл с исходным кодом
     * @return древовидное представление файла
     * @throws FileNotFoundException указан несуществующий файл
     */
    public SourceCodeUnit createSourceCodeUnit(File file) throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(file);
        return createSourceCodeUnit(cu);
    }

    /**
     * Формирование дерева проекта на основе файла с исходным кодом на языке Java
     *
     * @param file файл с исходным текстом программы
     * @return дерево проекта
     * @throws FileNotFoundException указан несуществующий файл
     */
    public ProjectTree create(File file) throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(file);
        List<CompilationUnit> compilationUnits = new ArrayList<>();
        compilationUnits.add(cu);
        return create(compilationUnits);
    }

    /**
     * Формирование дерева проекта на основе файла с исходным кодом на языке Java
     *
     * @param files файлы с исходным текстом программы
     * @return дерево проекта
     * @throws FileNotFoundException указан несуществующий файл
     */
    public ProjectTree create(List<File> files) throws FileNotFoundException {
        List<CompilationUnit> compilationUnits = new ArrayList<>();

        files.forEach(file -> {
            CompilationUnit cu = null;
            try {
                cu = StaticJavaParser.parse(file);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ParseProblemException ex) {
                System.out.println(String.format("errror with file parsing: %s, exception: ", file.toString(), ex));
                throw ex;
            }
            compilationUnits.add(cu);
        });
        return create(compilationUnits);
    }
}

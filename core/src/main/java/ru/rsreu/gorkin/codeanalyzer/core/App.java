package ru.rsreu.gorkin.codeanalyzer.core;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import ru.rsreu.gorkin.codeanalyzer.core.adapters.TreeCreator;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.ProjectTree;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.SourceCodeUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final String FILE_PATH = "/Users/avgorkin/radik/vkr/project_old/codeanalyzer-desktop/core/src/main/java/ru/rsreu/gorkin/codeanalyzer/core/App.java";

    private App() {

    }

    public static void main(String[] args) throws FileNotFoundException {
        App app = new App();
        app.run();
    }

    public void run() throws FileNotFoundException {
        TreeCreator creator = new TreeCreator();
        SourceCodeUnit sourceCodeUnit = creator.createSourceCodeUnit(new File(FILE_PATH));
        int a = 1;
    }

    private ProjectTree createSourceCodeTree(File file) throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(file);
        List<CompilationUnit> compilationUnits = new ArrayList<>();
        compilationUnits.add(cu);
        TreeCreator treeCreator = new TreeCreator();
        return treeCreator.create(compilationUnits);
    }
}

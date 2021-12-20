package ru.rsreu.gorkin.codeanalyzer.core;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import ru.rsreu.gorkin.codeanalyzer.core.adapters.TreeCreator;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.ProjectTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final String FILE_PATH = "D:\\afterschool\\RSEU\\4 course\\7 sem\\javaWeb\\labs\\labSupermarket\\maven-project\\core\\src\\main\\java\\ru\\rsreu\\gorkin\\supermarket\\model\\Order.java";

    private App() {

    }

    public static void main(String[] args) throws FileNotFoundException {
        App app = new App();
        app.run();
    }

    public void run() throws FileNotFoundException {
        ProjectTree tree = createSourceCodeTree(new File(FILE_PATH));
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

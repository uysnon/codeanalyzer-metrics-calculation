package ru.rsreu.gorkin.codeanalyzer.core.metrics.classorinterface;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

public class FieldsClassMetric extends ClassMetric {
    private static final String TITLE = "Поля";
    private static final String DESCRIPTION = "Количество полей";

    public FieldsClassMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    @Override
    public void process(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        setCount((int)
                classOrInterfaceDeclaration.getMembers()
                        .stream()
                        .filter(node -> node instanceof FieldDeclaration)
                        .count()
        );
    }
}

package com.uysnon.codeanalyzer.core.metrics.classorinterface;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class MethodsClassMetric extends ClassMetric {
    private static final String TITLE = "Методы";
    private static final String DESCRIPTION = "Количество методов";

    public MethodsClassMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }


    @Override
    public void process(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        setCount((int)
                classOrInterfaceDeclaration.getMembers()
                        .stream()
                        .filter(node -> node instanceof MethodDeclaration)
                        .count()
        );
    }
}

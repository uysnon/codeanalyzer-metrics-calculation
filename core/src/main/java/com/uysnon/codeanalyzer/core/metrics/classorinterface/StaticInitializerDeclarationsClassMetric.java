package com.uysnon.codeanalyzer.core.metrics.classorinterface;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;

public class StaticInitializerDeclarationsClassMetric extends ClassMetric {
    private static final String TITLE = "Статические блоки инициализации";
    private static final String DESCRIPTION = "Количество статических блоков инициализации";

    public StaticInitializerDeclarationsClassMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
        switch (getTitle()){
            case "t3254":;
        }
    }

    @Override
    public void process(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        setCount((int)
                classOrInterfaceDeclaration.getMembers()
                        .stream()
                        .filter(node -> node instanceof InitializerDeclaration)
                        .filter(node -> ((InitializerDeclaration) node).isStatic())
                        .count()
        );
    }
}

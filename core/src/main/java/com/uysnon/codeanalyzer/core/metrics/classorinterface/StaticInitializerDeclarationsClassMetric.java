package com.uysnon.codeanalyzer.core.metrics.classorinterface;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;

public class StaticInitializerDeclarationsClassMetric extends ClassMetric {
    private static final String TITLE = "STATIC_INIT_BLOCKS_COUnT";
    private static final String DESCRIPTION = "Количество статических блоков инициализации";

    public StaticInitializerDeclarationsClassMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
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

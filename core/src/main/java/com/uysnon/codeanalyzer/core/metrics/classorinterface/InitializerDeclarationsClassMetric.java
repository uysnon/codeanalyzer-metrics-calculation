package com.uysnon.codeanalyzer.core.metrics.classorinterface;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;

public class InitializerDeclarationsClassMetric extends ClassMetric {
    private static final String TITLE = "INIT_BLOCKS_COUNT";
    private static final String DESCRIPTION = "Количество нестатических блоков инициализации";

    public InitializerDeclarationsClassMetric() {
        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }


    @Override
    public void process(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        setCount((int)
                classOrInterfaceDeclaration.getMembers()
                        .stream()
                        .filter(node -> node instanceof InitializerDeclaration)
                        .filter(node -> !((InitializerDeclaration) node).isStatic())
                        .count()
        );
    }
}

package com.uysnon.codeanalyzer.core.rules;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

public class VariableNameWithLocation {
    private String name;
    private String location;

    private VariableNameWithLocation() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static VariableNameWithLocation of(VariableDeclarator variableDeclarator) {
        VariableNameWithLocation variableNameWithLocation = new VariableNameWithLocation();
        variableNameWithLocation.create(variableDeclarator);
        return variableNameWithLocation;
    }

    private void create(VariableDeclarator variableDeclarator) {
        createName(variableDeclarator);
        createLocation(variableDeclarator);
    }


    private void createName(VariableDeclarator variableDeclarator) {
        name = variableDeclarator.getNameAsString();
    }

    private void createLocation(VariableDeclarator variableDeclarator) {
        ClassOrInterfaceDeclaration classDeclaration = (ClassOrInterfaceDeclaration) variableDeclarator
                .getParentNode()
                .orElse(null)
                .getParentNode()
                .orElse(null);
        String className = classDeclaration.getNameAsString();
        PackageDeclaration packageDeclaration = (PackageDeclaration) classDeclaration
                .getParentNode()
                .orElse(null)
                .getChildNodes()
                .stream()
                .filter(node -> node instanceof PackageDeclaration)
                .findAny()
                .orElse(null);
        String packageName = packageDeclaration.getNameAsString();
        String range = variableDeclarator.getRange().orElse(null).toString();
        location = String.format("%s.%s - %s", packageName, className, range);
    }
}

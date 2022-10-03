package com.uysnon.codeanalyzer.onlinecalculation.model.export;

import com.uysnon.codeanalyzer.core.syntaxelements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum ExportUnitTypes {
    FILE("file",
            unit -> unit.getClass() == SourceCodeUnit.class,
            unit -> ((SourceCodeUnit) unit).getFileName(),
            unit -> {
                List<Unit> list = new ArrayList<>();
                list.addAll(((SourceCodeUnit) unit).getClassOrInterfaceUnits());
                list.addAll(((SourceCodeUnit) unit).getEnumUnits());
                return list;
            }
    ),
    CLASS(
            "class",
            unit -> {
                if (unit.getClass() == ClassOrInterfaceUnit.class) {
                    ClassOrInterfaceUnit typedUnit = (ClassOrInterfaceUnit) unit;
                    return !((ClassOrInterfaceUnit) unit).getClassOrInterfaceDeclaration().isInterface();
                }
                return false;
            },
            unit -> ((ClassOrInterfaceUnit) unit).getClassOrInterfaceDeclaration().getNameAsString(),
            unit -> {
                List<Unit> list = new ArrayList<>();
                list.addAll(((ClassOrInterfaceUnit) unit).getFieldUnits());
                list.addAll(((ClassOrInterfaceUnit) unit).getMethodUnits());
                list.addAll(((ClassOrInterfaceUnit) unit).getConstructorUnits());
                list.addAll(((ClassOrInterfaceUnit) unit).getInitializerDeclarationUnits());
                return list;
            }
    ),
    INTERFACE("interface",
            unit -> {
                if (unit.getClass() == ClassOrInterfaceUnit.class) {
                    ClassOrInterfaceUnit typedUnit = (ClassOrInterfaceUnit) unit;
                    return ((ClassOrInterfaceUnit) unit).getClassOrInterfaceDeclaration().isInterface();
                }
                return false;
            },
            unit -> ((ClassOrInterfaceUnit) unit).getClassOrInterfaceDeclaration().getNameAsString(),
            unit -> {
                List<Unit> list = new ArrayList<>();
                list.addAll(((ClassOrInterfaceUnit) unit).getFieldUnits());
                list.addAll(((ClassOrInterfaceUnit) unit).getMethodUnits());
                list.addAll(((ClassOrInterfaceUnit) unit).getConstructorUnits());
                list.addAll(((ClassOrInterfaceUnit) unit).getInitializerDeclarationUnits());
                return list;
            }
    ),
    ENUM("enum",
            unit -> unit.getClass() == EnumUnit.class,
            unit -> ((EnumUnit) unit).getEnumDeclaration().getNameAsString(),
            unit -> new ArrayList<>()

    ),
    CONSTRUCTOR("constructor",
            unit -> unit.getClass() == ConstructorUnit.class,
            unit -> ((ConstructorUnit) unit).getConstructorDeclaration().getNameAsString(),
            unit -> new ArrayList<>()
    ),
    METHOD("method",
            unit -> unit.getClass() == MethodUnit.class,
            unit -> ((MethodUnit) unit).getMethodDeclaration().getNameAsString(),
            unit -> new ArrayList<>()
    ),
    INITIALIZER_DECLARATION_UNIT("initializer_declaration_unit",
            unit -> unit.getClass() == InitializerDeclarationUnit.class,
            unit -> {
                InitializerDeclarationUnit typedUnit = (InitializerDeclarationUnit) unit;
                int lineBegin = typedUnit.getInitializerDeclaration().getBegin().get().line;
                int lineEnd = typedUnit.getInitializerDeclaration().getEnd().get().line;
                return String.format("lines: $d -> $d", lineBegin, lineEnd);
            },
            unit -> new ArrayList<>()
    ),
    FIELD("field",
            unit -> unit.getClass() == FieldUnit.class,
            unit -> ((FieldUnit) unit).getFieldDeclaration().getVariable(0).getNameAsString(),
            unit -> new ArrayList<>()
    );

    private String type;
    private Predicate<Unit> isSuitablePredicate;
    private Function<Unit, String> calculationNameFunction;
    private Function<Unit, List<Unit>> getSubUnitsFunction;


    ExportUnitTypes(String type, Predicate<Unit> isSuitablePredicate, Function<Unit, String> calculationNameFunction, Function<Unit, List<Unit>> getSubUnitsFunction) {
        this.type = type;
        this.isSuitablePredicate = isSuitablePredicate;
        this.calculationNameFunction = calculationNameFunction;
        this.getSubUnitsFunction = getSubUnitsFunction;
    }

    public String getType() {
        return type;
    }

    public static ExportUnit export(Unit unit) {
        return recursiveCreateUnitFunction(unit);
    }

    private static ExportUnit recursiveCreateUnitFunction(Unit unit) {
        for (ExportUnitTypes exporter : values()) {
            if (exporter.isSuitablePredicate.test(unit)) {
                ExportUnit exportUnit = new ExportUnit();
                exportUnit.setType(exporter.type);
                exportUnit.setName(exporter.calculationNameFunction.apply(unit));
                exportUnit.setMetrics(unit.getMetrics()
                        .stream()
                        .map(ExportMetric::create)
                        .collect(Collectors.toMap(
                                ExportMetric::getCode,
                                m -> m)));
                List<ExportUnit> exportChildren = new ArrayList<>();
                List<Unit> children = exporter.getSubUnitsFunction.apply(unit);
                if (children != null && !children.isEmpty()) {
                    for (Unit innerUnit : children) {
                        exportChildren.add(recursiveCreateUnitFunction(innerUnit));
                    }
                }
                exportUnit.setChildren(exportChildren);
                return exportUnit;
            }
        }
        return null;
    }
}

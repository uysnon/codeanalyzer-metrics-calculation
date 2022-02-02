package com.uysnon.codeanalyzer.core.rules;

import com.uysnon.codeanalyzer.core.syntaxelements.FieldUnit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FieldNamingRule implements Rule {
    private static final String LINE = "----------------------------------------------";
    private List<FieldUnit> fieldUnits;
    private String namingPattern;

    public List<FieldUnit> getFieldUnits() {
        return fieldUnits;
    }

    public void setFieldUnits(List<FieldUnit> fieldUnits) {
        this.fieldUnits = fieldUnits;
    }

    public String getNamingPattern() {
        return namingPattern;
    }

    public void setNamingPattern(String namingPattern) {
        this.namingPattern = namingPattern;
    }

    public FieldNamingRule(String namingPattern) {
        this.namingPattern = namingPattern;
        fieldUnits = new ArrayList<>();
    }

    public FieldNamingRule(List<FieldUnit> fieldUnits, String namingPattern) {
        this.namingPattern = namingPattern;
        this.fieldUnits = fieldUnits;
    }

    public void addField(FieldUnit unit) {
        fieldUnits.add(unit);
    }

    public void addFields(Collection<FieldUnit> fields) {
        fieldUnits.addAll(fields);
    }

    @Override
    public ValidationResult validate() {
        StringBuilder resultDescriptionBuilder = new StringBuilder();
        resultDescriptionBuilder
                .append(LINE)
                .append("\n")
                .append("Проверка правильности именования полей: ")
                .append(namingPattern)
                .append("\n");
        boolean resultValue = true;
        for (FieldUnit unit : fieldUnits) {
            if (!unit.isStatic()) {
                List<VariableNameWithLocation> variableNames = unit.getFieldDeclaration()
                        .getVariables()
                        .stream()
                        .map(VariableNameWithLocation::of)
                        .collect(Collectors.toList());
                for (VariableNameWithLocation variableName : variableNames) {
                    ValidationResult iterationResult = validate(variableName);
                    if (!iterationResult.isNormal()) {
                        if (resultValue) {
                            resultValue = false;
                        }
                        resultDescriptionBuilder
                                .append(iterationResult.getDescription())
                                .append("\n");
                    }
                }
            }
        }
        resultDescriptionBuilder.append("Результат проверки: ");
        if (resultValue) {
            resultDescriptionBuilder.append("пройдено успешно");
        } else {
            resultDescriptionBuilder.append("выявлены ошибки");
        }
        resultDescriptionBuilder
                .append("\n")
                .append(LINE);

        return new ValidationResult(resultValue, resultDescriptionBuilder.toString());
    }

    private ValidationResult validate(VariableNameWithLocation fieldName) {
        ValidationResult result = null;
        if (fieldName.getName().matches(namingPattern)) {
            result = new ValidationResult(true, String.format(
                    "OK - %s : %s",
                    fieldName.getName(),
                    fieldName.getLocation()));
        } else {
            result = new ValidationResult(false, String.format(
                    "ERROR - %s : %s",
                    fieldName.getName(),
                    fieldName.getLocation()));
        }
        return result;
    }

}

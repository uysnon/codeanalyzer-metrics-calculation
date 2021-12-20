package ru.rsreu.gorkin.codeanalyzer.core.rules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.CompilationUnit;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.ClassOrInterfaceUnit;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.FieldUnit;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.SourceCodeUnit;

import java.util.ArrayList;
import java.util.List;

public class RulesFacade {

    public List<ValidationResult> validate(String jsonFileStructure, List<SourceCodeUnit> sourceFiles) {
        List<RuleStructure> ruleStructures = readRuleStructuresFromJson(jsonFileStructure);
        List<Rule> rules = transformRuleStructuresToRules(ruleStructures, sourceFiles);
        List<ValidationResult> validationResults = validateRules(rules);
        return validationResults;
    }

    private List<RuleStructure> readRuleStructuresFromJson(String jsonFileStructure) {
        ObjectMapper mapper = new ObjectMapper();
        List<RuleStructure> ruleStructures = null;
        try {
            ruleStructures = mapper.readValue(jsonFileStructure, new TypeReference<List<RuleStructure>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ruleStructures;
    }

    private List<Rule> transformRuleStructuresToRules(List<RuleStructure> ruleStructures, List<SourceCodeUnit> sourceFiles) {
        List<Rule> rules = new ArrayList<>();
        for (RuleStructure ruleStructure : ruleStructures) {
            rules.addAll(transformRuleStructure(ruleStructure, sourceFiles));
        }
        return rules;
    }

    private List<Rule> transformRuleStructure(RuleStructure ruleStructure, List<SourceCodeUnit> sourceFiles) {
        List<Rule> rules = new ArrayList<>();
        if ("naming".equals(ruleStructure.getType().getTitle())) {
            for (Scopes scope : ruleStructure.getType().getScopes()) {
                if (scope == Scopes.LOCAL_FIELD) {
                    FieldNamingRule rule = new FieldNamingRule(ruleStructure.getValue());
                    for (SourceCodeUnit compilationUnit : sourceFiles) {
                        for (ClassOrInterfaceUnit classOrInterfaceUnit : compilationUnit.getClassOrInterfaceUnits()) {
                            rule.addFields(classOrInterfaceUnit.getFieldUnits());
                        }
                    }
                    rules.add(rule);
                }
            }
        }
        return rules;
    }

    private List<ValidationResult> validateRules(List<Rule> rules){
        List<ValidationResult> validationResults = new ArrayList<>();
        for(Rule rule: rules){
            validationResults.add(rule.validate());
        }
        return validationResults;
    }
}

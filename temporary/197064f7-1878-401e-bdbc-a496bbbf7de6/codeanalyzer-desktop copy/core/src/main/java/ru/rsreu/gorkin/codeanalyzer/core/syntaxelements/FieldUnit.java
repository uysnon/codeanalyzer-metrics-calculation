package ru.rsreu.gorkin.codeanalyzer.core.syntaxelements;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.Metric;

import javax.naming.directory.ModificationItem;
import java.util.List;

public class FieldUnit extends Unit {
    private FieldDeclaration fieldDeclaration;

    public FieldUnit(FieldDeclaration fieldDeclaration) {
        this.fieldDeclaration = fieldDeclaration;
        addAllMetrics();
    }

    public FieldDeclaration getFieldDeclaration() {
        return fieldDeclaration;
    }

    public void setFieldDeclaration(FieldDeclaration fieldDeclaration) {
        this.fieldDeclaration = fieldDeclaration;
    }

    @Override
    public void calculateMetrics() {
        for (Metric metric : getMetrics()) {
            metric.process(fieldDeclaration);
        }
    }

    public boolean isStatic(){
        List<Modifier> modifierList  = fieldDeclaration.getModifiers();
        for (Modifier modifier: modifierList){
            if (modifier.getKeyword() == Modifier.Keyword.STATIC){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void addCustomUnitMetrics() {

    }
}

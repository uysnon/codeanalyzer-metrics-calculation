package ru.rsreu.gorkin.codeanalyzer.desktop.ui.forms;

import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.ClassOrInterfaceUnit;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.ConstructorUnit;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.FieldUnit;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.MethodUnit;
import ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.panels.DiagramPanel;
import ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.utils.TextColorUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClassForm extends UnitGUIForm {

    private static final String FIELD_TITLE = "field";
    private static final String METHOD_TITLE = "method";
    private static final String CONSTRUCTOR_TITLE = "constructor";
    private ClassOrInterfaceUnit classUnit;

    public ClassForm(ClassOrInterfaceUnit classUnit) {
        this.classUnit = classUnit;
        createUIComponents();
        setCurrentUnit(classUnit);
    }

    @Override
    protected void createUIComponents() {
        init();
        updateMetricsTable(classUnit);
        updateTextArea(classUnit.getClassOrInterfaceDeclaration());
        initDiagramPanel();
        updateLabel(classUnit.getClassOrInterfaceDeclaration().getName().toString());
    }

    @Override
    protected void initDiagramPanel() {
        for (FieldUnit unit : classUnit.getFieldUnits()) {
            JButton button = null;
            //String title =  unit.getFieldDeclaration().getVariables().get(0).getNameAsString();
            String title = unit.getFieldDeclaration().toString();
            button = new JButton(
                    TextColorUtils.getColorStringWithSupText(
                            title,
                            HEX_LIGHT_COLOR,
                            FIELD_TITLE,
                            HEX_LIGHT_COLOR));
            button.setBackground(FIELD_COLOR);
            button.setBorderPainted(false);


            button.addMouseListener(
                    new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            onOnceClickListener(unit, title, unit.getFieldDeclaration());
                        }
                    });
            button.setFont(new Font("Serif", Font.BOLD, 16));
            units.add(button);
        }

        for (ConstructorUnit unit : classUnit.getConstructorUnits()) {
            JButton button = null;
            String title = unit.getConstructorDeclaration().getDeclarationAsString(true, true, true);
            button = new JButton(
                    TextColorUtils.getColorStringWithSupText(
                            title,
                            HEX_LIGHT_COLOR,
                            CONSTRUCTOR_TITLE,
                            HEX_LIGHT_COLOR));
            button.setBackground(CONSTRUCTOR_COLOR);
            button.setBorderPainted(false);
            button.addMouseListener(
                    new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            onOnceClickListener(unit, title, unit.getConstructorDeclaration());
                        }
                    });
            button.setFont(new Font("Serif", Font.BOLD, 16));
            units.add(button);
        }

        for (MethodUnit unit : classUnit.getMethodUnits()) {
            JButton button = null;
            String title = unit.getMethodDeclaration().getDeclarationAsString(true, true, true);
            button = new JButton(
                    TextColorUtils.getColorStringWithSupText(
                            title,
                            HEX_LIGHT_COLOR,
                            METHOD_TITLE,
                            HEX_LIGHT_COLOR));
            button.setBackground(METHOD_COLOR);
            button.setBorderPainted(false);
            button.addMouseListener(
                    new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            onOnceClickListener(unit, title, unit.getMethodDeclaration());
                        }
                    });
            button.setFont(new Font("Serif", Font.BOLD, 16));
            units.add(button);
        }

        int cols = units.size() >= 2 ? 2 : 1;
        GridLayout gridLayout = new GridLayout(0,1, 5, 5);

        JButton sourceCodeUnitButton = new JButton(classUnit.getClassOrInterfaceDeclaration().getName().toString());
        sourceCodeUnitButton.setFont(new Font("Serif", Font.BOLD, 16));
        sourceCodeUnitButton.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        onOnceClickListener(
                                classUnit,
                                classUnit.getClassOrInterfaceDeclaration().getName().toString(),
                                classUnit.getClassOrInterfaceDeclaration()
                        );
                    }
                }
        );
        diagramPanel = new DiagramPanel(units, gridLayout, sourceCodeUnitButton);

        diagramPanel.add(sourceCodeUnitButton);
        units.forEach(diagramPanel::add);

        diagramParentPanel.add(diagramPanel);
    }
}

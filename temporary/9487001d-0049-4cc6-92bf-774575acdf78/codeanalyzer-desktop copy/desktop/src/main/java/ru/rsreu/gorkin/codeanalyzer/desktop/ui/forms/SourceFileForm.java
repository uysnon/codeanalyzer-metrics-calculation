package ru.rsreu.gorkin.codeanalyzer.desktop.ui.forms;

import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.ClassOrInterfaceUnit;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.EnumUnit;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.SourceCodeUnit;
import ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.panels.DiagramPanel;
import ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.utils.TextColorUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class SourceFileForm extends UnitGUIForm {
    private static final String ENUM_TITLE = "enum";
    private static final String CLASS_TITLE = "class";
    private static final String INTERFACE_TITLE = "interface";

    private SourceCodeUnit sourceCodeUnit;
    private Consumer<ClassOrInterfaceUnit> openClassFormConsumer;

    public SourceFileForm(SourceCodeUnit sourceCodeUnit) {
        this.sourceCodeUnit = sourceCodeUnit;
        createUIComponents();
        setCurrentUnit(sourceCodeUnit);
    }

    public void setOpenClassFormConsumer(Consumer<ClassOrInterfaceUnit> openClassFormConsumer) {
        this.openClassFormConsumer = openClassFormConsumer;
    }

    @Override
    protected void createUIComponents() {
        init();
        updateMetricsTable(sourceCodeUnit);
        updateTextArea(sourceCodeUnit.getCompilationUnit());
        initDiagramPanel();
        updateLabel(sourceCodeUnit.getFileName());
    }

    @Override
    protected void initDiagramPanel() {
        for (ClassOrInterfaceUnit unit : sourceCodeUnit.getClassOrInterfaceUnits()) {
            JButton button = null;
            String name = unit.getClassOrInterfaceDeclaration().getNameAsString();
            if (unit.getClassOrInterfaceDeclaration().isInterface()) {
                button = new JButton(
                        TextColorUtils.getColorStringWithSupText(
                                name,
                                HEX_LIGHT_COLOR,
                                INTERFACE_TITLE,
                                HEX_LIGHT_COLOR));
                button.setFont(new Font("Serif", Font.BOLD, 16));
                button.setBackground(INTERFACE_COLOR);

            } else {
                button = new JButton(
                        TextColorUtils.getColorStringWithSupText(
                                name,
                                HEX_LIGHT_COLOR,
                                CLASS_TITLE,
                                HEX_LIGHT_COLOR));
                button.setFont(new Font("", Font.BOLD, 16));
                button.setBackground(CLASS_COLOR);
            }
            button.addMouseListener(
                    new MouseAdapter() {
                        String title = unit.getClassOrInterfaceDeclaration().getName().toString();

                        @Override
                        public void mousePressed(MouseEvent e) {
                            if (e.getClickCount() == 1) {
                                onOnceClickListener(unit, title, unit.getClassOrInterfaceDeclaration());
                            } else {
                                if (e.getClickCount() == 2) {
                                    openClassFormConsumer.accept(unit);
                                }
                            }
                        }
                    }
            );
            button.setBorderPainted(false);
            units.add(button);
        }

        for (EnumUnit unit : sourceCodeUnit.getEnumUnits()) {
            JButton button = new JButton(
                    TextColorUtils.getColorStringWithSupText(
                            unit.getEnumDeclaration().getNameAsString(),
                            HEX_LIGHT_COLOR,
                            ENUM_TITLE,
                            HEX_LIGHT_COLOR));
            button.setBackground(ENUM_COLOR);
            button.setBorderPainted(false);
            button.setFont(new Font("Serif", Font.BOLD, 16));
            units.add(button);
        }

        int cols = units.size() >= 2 ? 2 : 1;
        GridLayout gridLayout = new GridLayout(0, cols, 5, 5);


        JButton sourceCodeUnitButton = new JButton(sourceCodeUnit.getFileName());
        sourceCodeUnitButton.setFont(new Font("Serif", Font.BOLD, 16));
        sourceCodeUnitButton.addMouseListener(
                new MouseAdapter() {
                    String title = sourceCodeUnit.getFileName();

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getClickCount() >= 1) {
                            onOnceClickListener(sourceCodeUnit, title, sourceCodeUnit.getCompilationUnit());
                        }
                    }
                }
        );
        diagramPanel = new DiagramPanel(units, gridLayout, sourceCodeUnitButton);

        diagramPanel.add(sourceCodeUnitButton);
        units.forEach(diagramPanel::add);

        diagramParentPanel.add(diagramPanel);
    }
}

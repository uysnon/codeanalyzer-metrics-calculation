package ru.rsreu.gorkin.codeanalyzer.desktop.ui.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ru.rsreu.gorkin.codeanalyzer.core.rules.RulesFacade;
import ru.rsreu.gorkin.codeanalyzer.core.rules.ValidationResult;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.SourceCodeUnit;
import ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.files.JSONFilesChooser;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class ScanByRulesForm {
    private JPanel parentPanel;
    private JButton rulesFileChooserButton;
    private JTextArea resultsArea;
    private JButton checkProjectButton;
    private JLabel selectedRulesFile;

    private String rulesJsonFileContent;

    private Supplier<List<SourceCodeUnit>> projectStructureSupplier;


    public ScanByRulesForm() {
        rulesFileChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONFilesChooser jsonFilesChooser = new JSONFilesChooser();
                int returnVal = jsonFilesChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = jsonFilesChooser.getSelectedFile();
                    try {
                        rulesJsonFileContent = Files.readString(Path.of(file.getPath()));
                        RulesFacade rulesFacade = new RulesFacade();
                        selectedRulesFile.setText(file.getName());
                        List<ValidationResult> validationResults = rulesFacade.validate(rulesJsonFileContent, projectStructureSupplier.get());
                        StringBuilder stringBuilder = new StringBuilder();
                        for (ValidationResult validationResult : validationResults) {
                            if (!validationResult.isNormal()) {
                                stringBuilder.append(validationResult.getDescription())
                                        .append("\n");
                            }
                        }
                        resultsArea.setText(stringBuilder.toString());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

    public void setProjectStructureSupplier(Supplier projectStructureSupplier) {
        this.projectStructureSupplier = projectStructureSupplier;
    }

    public JPanel getParentPanel() {
        return parentPanel;
    }

}

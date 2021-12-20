package ru.rsreu.gorkin.codeanalyzer.desktop;

import com.formdev.flatlaf.FlatIntelliJLaf;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.SourceCodeUnit;
import ru.rsreu.gorkin.codeanalyzer.desktop.ui.forms.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class App {
    private static final int WINDOW_HEIGHT = 540;
    private static final int WINDOW_WIDTH = 900;
    private static final int MAX_FILE_TAB_NAME_LENGTH = 20;
    private Map<File, SourceCodeUnit> fileSourceCodeUnitMap;

    private MainForm mainForm;
    private ProjectForm projectForm;
    private ScanByRulesForm scanByRulesForm;
    private Supplier<List<SourceCodeUnit>> projectStructureSupplier;

    public App() {
        mainForm = new MainForm();
        projectForm = new ProjectForm();
        projectForm.setProjectStructureConsumer(map -> fileSourceCodeUnitMap = map);
        projectStructureSupplier = () -> projectForm.getSourceCodeUnits();
        projectForm.setClickSourceCodeUnitConsumer(unit ->
        {
            SourceFileForm form = new SourceFileForm(unit);
            form.setOpenClassFormConsumer(classUnit -> {
                ClassForm classForm = new ClassForm(classUnit);
                String fullName = classUnit.getClassOrInterfaceDeclaration().getNameAsString();
                String title = getTabFileName(fullName);
                mainForm.addCloseableTab(title, classForm.getParentPanel(), fullName);
                mainForm.setActive(title);
            });
            String fullName = unit.getFileName();
            String title = getTabFileName(fullName);
            mainForm.addCloseableTab(title, form.getParentPanel(), fullName);
            mainForm.setActive(title);

        });
        scanByRulesForm = new ScanByRulesForm();
        scanByRulesForm.setProjectStructureSupplier(projectStructureSupplier);
        mainForm.addNoneCloseableTab("Проект", projectForm.getParentPanel(), "Проект");
        mainForm.addNoneCloseableTab("Проверка", scanByRulesForm.getParentPanel(), "Проверка");
    }

    public static void main(String[] args) {
        FlatIntelliJLaf.install();
        App app = new App();
        app.run();

    }

    public void run() {
        JFrame frame = new JFrame("CodeAnalyzer");
        frame.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        frame.add(mainForm.getParentPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private String getTabFileName(String fullFileName) {
        if (fullFileName.length() <= MAX_FILE_TAB_NAME_LENGTH) {
            return fullFileName;
        } else {
            return fullFileName.substring(0, MAX_FILE_TAB_NAME_LENGTH / 2 - 1 - 1)
                    + ".."
                    + fullFileName.substring(fullFileName.length() - MAX_FILE_TAB_NAME_LENGTH / 2 + 1);
        }
    }


}

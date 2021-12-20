package ru.rsreu.gorkin.codeanalyzer.desktop.ui.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ru.rsreu.gorkin.codeanalyzer.desktop.excel.export.ExcelExporter;
import ru.rsreu.gorkin.codeanalyzer.desktop.screenshots.ScreenshotMaker;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class UnitGUIForm extends UnitForm {
    public static final Color FILE_COLOR = new Color(25, 114, 120);
    public static final Color CLASS_COLOR = new Color(196, 69, 54);
    public static final Color INTERFACE_COLOR = new Color(119, 46, 37);
    public static final Color ENUM_COLOR = new Color(119, 46, 37);
    public static final String HEX_LIGHT_COLOR = "FFFFFF";


    protected JPanel parentPanel;
    protected JTabbedPane sourceCodePanel;
    protected JButton exportDiagramButton;
    protected JPanel diagramPanel;
    protected JPanel metricsPanel;
    protected JPanel programTextPanel;
    protected JTable metricsTable;
    protected JTextArea unitTextArea;
    protected JButton exportMetricsButton;
    protected JLabel unitLabel;
    protected JScrollPane diagramScrollPane;
    protected JPanel diagramParentPanel;

    protected List<JButton> units;

    public UnitGUIForm() {
        this.units = new ArrayList<>();
        exportDiagramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeScreenshotAction();
            }
        });
        exportMetricsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportMetrics();
            }
        });
    }


    public JPanel getParentPanel() {
        return parentPanel;
    }

    @Override
    protected JTextArea getUnitTextArea() {
        return unitTextArea;
    }

    @Override
    protected JTable getMetricsTable() {
        return metricsTable;
    }

    @Override
    protected JLabel getUnitLabel() {
        return unitLabel;
    }

    protected abstract void createUIComponents();

    protected abstract void initDiagramPanel();

    private void makeScreenshotAction() {
        JFileChooser fileChooser = new JFileChooser();
        String currentPath = Paths.get("").toAbsolutePath().toString();
        fileChooser.setCurrentDirectory(new File(currentPath));
        int retrival = fileChooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                ScreenshotMaker screenshotMaker = new ScreenshotMaker();
                screenshotMaker.makeScreenshot(
                        diagramPanel,
                        new File(fileChooser.getSelectedFile().getAbsoluteFile().toString() + ".png"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void exportMetrics() {
        JFileChooser fileChooser = new JFileChooser();
        String currentPath = Paths.get("").toAbsolutePath().toString();
        fileChooser.setCurrentDirectory(new File(currentPath));
        int retrival = fileChooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                ExcelExporter excelExporter = new ExcelExporter();
                excelExporter.exportSourceFileMetrics(
                        getCurrentUnit().getMetrics(),
                        new File(fileChooser.getSelectedFile().getAbsoluteFile().toString() + ".xls"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}

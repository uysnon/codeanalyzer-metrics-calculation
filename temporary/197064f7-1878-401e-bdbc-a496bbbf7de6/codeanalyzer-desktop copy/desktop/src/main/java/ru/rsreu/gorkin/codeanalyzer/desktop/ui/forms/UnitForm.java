package ru.rsreu.gorkin.codeanalyzer.desktop.ui.forms;

import com.github.javaparser.ast.Node;
import com.github.javaparser.printer.PrettyPrinter;
import ru.rsreu.gorkin.codeanalyzer.core.syntaxelements.Unit;
import ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.table.NoneEditableTableModel;
import ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.utils.MetricsBySpecificityComparator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class UnitForm {
    public static final Color FIELD_COLOR = new Color(25, 114, 120);
    public static final Color METHOD_COLOR = new Color(196, 69, 54);
    public static final Color CONSTRUCTOR_COLOR = new Color(119, 46, 37);
    public static final Color FILE_COLOR = new Color(25, 114, 120);
    public static final Color CLASS_COLOR = new Color(196, 69, 54);
    public static final Color INTERFACE_COLOR = new Color(119, 46, 37);
    public static final Color ENUM_COLOR = new Color(119, 46, 37);
    public static final String HEX_LIGHT_COLOR = "EDDDD4";
    public static final String FIELD_TITLE = "field";
    public static final String METHOD_TITLE = "method";
    public static final String CONSTRUCTOR_TITLE = "constructor";

        private JTextArea unitText;
        private JTable metricsTable;
        private JLabel label;
        private DefaultTableModel metricsTableModel;
        private PrettyPrinter prettyPrinter;
        private Unit currentUnit;

    public Unit getCurrentUnit() {
        return currentUnit;
    }

    public void setCurrentUnit(Unit currentUnit) {
        this.currentUnit = currentUnit;
    }

    public UnitForm() {
        this.prettyPrinter = new PrettyPrinter();
    }

    private void initTableModel() {
        metricsTableModel = new NoneEditableTableModel();
        metricsTableModel.addColumn("Метрика");
        metricsTableModel.addColumn("Величина");
        metricsTable.setModel(metricsTableModel);
    }

    protected void init() {
        unitText = getUnitTextArea();
        metricsTable = getMetricsTable();
        label = getUnitLabel();
        initTableModel();
    }

    protected abstract JTextArea getUnitTextArea();

    protected abstract JTable getMetricsTable();

    protected abstract JLabel getUnitLabel();


    protected void onOnceClickListener(Unit unit, String labelTitle, Node node) {
        this.currentUnit = unit;
        updateMetricsTable(unit);
        updateLabel(labelTitle);
        updateTextArea(node);
    }

    protected void updateMetricsTable(Unit unit) {
        metricsTableModel.setRowCount(0);
        unit.getMetrics().stream()
                .sorted(new MetricsBySpecificityComparator())
                .forEach(metric ->
                        metricsTableModel.addRow(new Object[]
                                {
                                        metric.getTitle(),
                                        metric.getCount()
                                })
                );
    }

    protected void updateLabel(String title) {
        label.setText(title);
    }

    protected void updateTextArea(Node node) {
        unitText.setText(prettyPrinter.print(node));
    }
}

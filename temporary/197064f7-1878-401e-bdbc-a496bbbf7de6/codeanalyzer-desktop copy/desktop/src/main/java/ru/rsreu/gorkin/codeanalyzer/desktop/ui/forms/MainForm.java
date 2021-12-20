package ru.rsreu.gorkin.codeanalyzer.desktop.ui.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.buttons.PlainJButton;

import javax.swing.*;
import java.awt.*;

public class MainForm {
    private JTabbedPane tabbedPane;
    private JPanel parentPanel;

    public MainForm() {
        initWindow();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    public JPanel getParentPanel() {
        return parentPanel;
    }

    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    public void addCloseableTab(String title, Component component, String description) {
        tabbedPane.addTab(title, null, component, description);
        int index = tabbedPane.indexOfTab(title);
        JPanel pnlTab = new JPanel(new GridBagLayout());
        pnlTab.setOpaque(false);
        JLabel lblTitle = new JLabel(title);
        JButton btnClose = new PlainJButton("  x");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        pnlTab.add(lblTitle, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx++;
        gbc.weightx = 0;
        pnlTab.add(btnClose, gbc);

        tabbedPane.setTabComponentAt(index, pnlTab);

        btnClose.addActionListener(evt -> {
            int listnerIndex = tabbedPane.indexOfTab(title);
            if (listnerIndex >= 0) {
                tabbedPane.removeTabAt(listnerIndex);
            }
        });

    }

    public void addNoneCloseableTab(String title, Component component, String description) {
        tabbedPane.addTab(title, null, component, description);
    }


    public void setActive(String title) {
        int listnerIndex = tabbedPane.indexOfTab(title);
        tabbedPane.setSelectedIndex(listnerIndex);
    }

    private void initWindow() {
        tabbedPane.removeAll();
    }

}

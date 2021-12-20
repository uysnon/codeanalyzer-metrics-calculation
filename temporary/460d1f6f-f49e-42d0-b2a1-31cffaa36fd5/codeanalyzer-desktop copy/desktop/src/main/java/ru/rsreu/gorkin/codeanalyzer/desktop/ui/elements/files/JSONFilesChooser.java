package ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.files;

import ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.files.fileutils.ExtensionUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class JSONFilesChooser extends JFileChooser {
    private static final String basePath = "D:\\afterschool\\RSEU\\4 course\\VKR\\project\\core\\codestatistics\\src\\main\\java\\ru\\rsreu\\gorkin\\codeanalyzer";

    public JSONFilesChooser() {
        super(basePath);
        setMultiSelectionEnabled(true);
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setAcceptAllFileFilterUsed(false);
        setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return ExtensionUtils.isJsonFile(f);
            }

            @Override
            public String getDescription() {
                return "Json files";
            }
        });
    }


}

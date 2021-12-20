package ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.files.fileutils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;

public class ExtensionUtils {
    public static final String JAVA_FILES = "java";
    public static final String JSON_FILES = "json";

    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        return FilenameUtils.getExtension(f.getName());
    }

    public static String getExtension(Path path) {
        return FilenameUtils.getExtension(path.toString());
    }

    public static boolean isJavaFile(File f){
        String extension = getExtension(f);
        if (extension != null) {
            return extension.equals(ExtensionUtils.JAVA_FILES);
        }
        return false;
    }

    public static boolean isJavaFile(Path path){
        String extension = getExtension(path);
        if (extension != null) {
            return extension.equals(ExtensionUtils.JAVA_FILES);
        }
        return false;
    }

    public static boolean isJsonFile(File f){
        String extension = getExtension(f);
        if (extension != null) {
            return extension.equals(ExtensionUtils.JSON_FILES);
        }
        return false;
    }

    public static String getFileNameFrom(String pathToFile){
        return FilenameUtils.getName(pathToFile);
    }
}

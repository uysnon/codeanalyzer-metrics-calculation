package ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.files.fileutils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilesFinder {
    public static List<File> findFilesFromFilesAndDirectories(File[] filesAndDirectories) throws IOException {
        List<File> result = new ArrayList<>();
        for (File file : filesAndDirectories) {
            result.addAll(Files.find(file.toPath(),
                    Integer.MAX_VALUE,
                    (filePath, fileAttr) -> fileAttr.isRegularFile() && ExtensionUtils.isJavaFile(filePath))
                    .peek(System.out::println)
                    .map(Path::toFile)
                    .collect(Collectors.toList()));
        }
        return result;
    }
}

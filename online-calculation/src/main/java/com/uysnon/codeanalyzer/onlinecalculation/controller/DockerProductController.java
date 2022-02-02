package com.uysnon.codeanalyzer.onlinecalculation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.uysnon.codeanalyzer.core.adapters.TreeCreator;
import com.uysnon.codeanalyzer.core.syntaxelements.ProjectTree;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
public class DockerProductController {

    @PostMapping("/analyze/")
    public ProjectTree getMessage(@RequestParam("file") MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String unzipLocation = "temporary/" + uuid + "/";
        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<File> javaFiles = new ArrayList<>();
        for (ZipEntry entry = zipInputStream.getNextEntry();
             entry != null;
             entry = zipInputStream.getNextEntry()) {
            Path filePath = Paths.get(unzipLocation, entry.getName());
            if (!entry.isDirectory()) {
                if (filePath.toString().matches(".*\\.java$")) {
                    javaFiles.add(filePath.toFile());
                    unzipFiles(zipInputStream, filePath);
                }
            } else {
                Files.createDirectories(filePath);
            }
        }
        TreeCreator treeCreator = new TreeCreator();
        ProjectTree projectTree = treeCreator.create(javaFiles);
        return projectTree;
    }

    public static void unzipFiles(final ZipInputStream zipInputStream, final Path unzipFilePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzipFilePath.toAbsolutePath().toString()))) {
            byte[] bytesIn = new byte[1024];
            int read = 0;
            while ((read = zipInputStream.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }

    }
}

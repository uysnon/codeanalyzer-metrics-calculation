package com.uysnon.codeanalyzer.onlinecalculation.service;

import com.uysnon.codeanalyzer.onlinecalculation.model.file.ProjectLocation;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

@Service
public class ArchiveService {

    public void unzipFiles(final ZipInputStream zipInputStream,
                           final Path unzipFilePath) throws IOException {
        com.google.common.io.Files.createParentDirs(unzipFilePath.toFile());
        try (BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(unzipFilePath.toAbsolutePath().toString()))) {
            byte[] bytesIn = new byte[1024];
            int read = 0;
            while ((read = zipInputStream.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    public ProjectLocation unzipProject(UUID projectUUID,
                                        String unzipLocation,
                                        MultipartFile file) throws IOException {
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
                if (!filePath.toString().contains(".mvn")
                        && filePath.toString().matches(".*\\.java$")) {
                    javaFiles.add(filePath.toFile());
                    unzipFiles(zipInputStream, filePath);
                }
            } else {
                Files.createDirectories(filePath);
            }
        }
        return new ProjectLocation(projectUUID, unzipLocation, javaFiles);
    }
}

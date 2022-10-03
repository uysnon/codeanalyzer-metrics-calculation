package com.uysnon.codeanalyzer.onlinecalculation.model.file;

import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectLocation {
    private UUID projectUUID;
    private String pathToProject;
    private List<File> javaFiles;

    public ProjectLocation(UUID projectUUID, String pathToProject, List<File> javaFiles) {
        this.projectUUID = projectUUID;
        this.pathToProject = pathToProject;
        this.javaFiles = javaFiles;
    }

    public UUID getProjectUUID() {
        return projectUUID;
    }

    public String getPathToProject() {
        return pathToProject;
    }

    public List<File> getJavaFiles() {
        return javaFiles;
    }
}

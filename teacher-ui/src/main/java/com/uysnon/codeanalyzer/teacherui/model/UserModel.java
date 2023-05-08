package com.uysnon.codeanalyzer.teacherui.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {
    private long id;
    private String title;
    private byte[] content;
    private double accuracy;
    private String pathToFile;
    private String learnedPath;
}

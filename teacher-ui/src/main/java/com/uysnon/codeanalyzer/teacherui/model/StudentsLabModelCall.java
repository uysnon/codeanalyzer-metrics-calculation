package com.uysnon.codeanalyzer.teacherui.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentsLabModelCall {
    private long id;
    private long modelId;
    private String className;
    private String description;
    private String meta;
}

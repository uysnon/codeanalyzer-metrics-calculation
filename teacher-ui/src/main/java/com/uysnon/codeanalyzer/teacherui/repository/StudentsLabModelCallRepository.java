package com.uysnon.codeanalyzer.teacherui.repository;


import com.uysnon.codeanalyzer.teacherui.model.StudentsLabModelCall;

public interface StudentsLabModelCallRepository {
    long save(StudentsLabModelCall studentsLabModelCall);

    StudentsLabModelCall get(long id);
}

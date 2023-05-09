package com.uysnon.codeanalyzer.teacherui.repository;


import com.uysnon.codeanalyzer.teacherui.model.StudentsLabModelCall;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryStudentsLabModelCallRepository implements StudentsLabModelCallRepository {
    private static StudentsLabModelCall studentsLabModelCall =
            StudentsLabModelCall.builder()
                    .id(1)
                    .modelId(4)
                    .className("Засчитана")
                    .description("Программный код отвечает поставленным требованям")
                    .meta("")
                    .build();

    @Override
    public long save(StudentsLabModelCall studentsLabModelCall) {
        return 1L;
    }

    @Override
    public StudentsLabModelCall get(long id) {
        return studentsLabModelCall;
    }
}

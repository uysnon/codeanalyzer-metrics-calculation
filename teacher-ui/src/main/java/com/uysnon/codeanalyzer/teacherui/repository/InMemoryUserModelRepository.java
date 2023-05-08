package com.uysnon.codeanalyzer.teacherui.repository;

import com.uysnon.codeanalyzer.teacherui.model.UserModel;
import org.springframework.stereotype.Repository;


@Repository
public class InMemoryUserModelRepository implements UserModelRepository {
    private static UserModel userModel = UserModel
            .builder()
            .id(1L)
            .accuracy(0.78)
            .pathToFile("/src/labs/generated/model-1.h5")
            .title("Некоторое название")
            .build();

    @Override
    public UserModel get(long id) {
        return userModel;
    }

    @Override
    public void save(UserModel userModel) {

    }
}

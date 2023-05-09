package com.uysnon.codeanalyzer.teacherui.repository;

import com.uysnon.codeanalyzer.teacherui.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class InMemoryUserModelRepository implements UserModelRepository {
    private static UserModel userModel = UserModel
            .builder()
            .id(1L)
            .accuracy(0.78)
            .pathToFile("/src/labs/generated/model-1.h5")
            .title("")
            .build();

    private static List<UserModel> userModelList = new ArrayList<>();

    static {
        userModelList.add(userModel);
        userModelList.add(UserModel
                .builder()
                .id(1L)
                .accuracy(0.78)
                .pathToFile("/src/labs/generated/model-1.h5")
                .title("Лабораторная 2")
                .build());
        userModelList.add(UserModel
                .builder()
                .id(2L)
                .accuracy(0.63)
                .pathToFile("/src/labs/generated/model-1.h5")
                .title("Лабораторная 3")
                .build());
        userModelList.add(UserModel
                .builder()
                .id(3L)
                .accuracy(0.89)
                .pathToFile("/src/labs/generated/model-1.h5")
                .title("Лабораторная 4")
                .build());
        userModelList.add(UserModel
                .builder()
                .id(4L)
                .accuracy(0.91)
                .pathToFile("/src/labs/generated/model-1.h5")
                .title("Лабораторная 5")
                .build());
        userModelList.add(UserModel
                .builder()
                .id(5L)
                .accuracy(0.88)
                .pathToFile("/src/labs/generated/model-1.h5")
                .title("Лабораторная 6")
                .build());
    }

    @Override
    public UserModel get(long id) {
        return userModel;
    }

    @Override
    public long save(UserModel userModel) {
        return 1L;
    }

    @Override
    public void update(UserModel userModel) {
        InMemoryUserModelRepository.userModel = userModel;
    }

    @Override
    public List<UserModel> getAll() {
        return userModelList;
    }
}

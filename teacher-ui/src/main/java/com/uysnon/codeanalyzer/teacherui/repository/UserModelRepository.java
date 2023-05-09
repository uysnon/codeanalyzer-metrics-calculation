package com.uysnon.codeanalyzer.teacherui.repository;

import com.uysnon.codeanalyzer.teacherui.model.UserModel;

import java.util.List;

public interface UserModelRepository {
    UserModel get(long id);
    long save(UserModel userModel);

    void update(UserModel userModel);

    List<UserModel> getAll();
}

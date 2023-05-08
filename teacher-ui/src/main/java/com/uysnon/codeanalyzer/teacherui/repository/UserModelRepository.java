package com.uysnon.codeanalyzer.teacherui.repository;

import com.uysnon.codeanalyzer.teacherui.model.UserModel;

public interface UserModelRepository {
    UserModel get(long id);
    void save(UserModel userModel);
}

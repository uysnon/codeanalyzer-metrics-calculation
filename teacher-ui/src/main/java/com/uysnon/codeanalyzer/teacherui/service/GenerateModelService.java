package com.uysnon.codeanalyzer.teacherui.service;

import com.uysnon.codeanalyzer.teacherui.model.UserModel;
import com.uysnon.codeanalyzer.teacherui.repository.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerateModelService {
    private UserModelRepository userModelRepository;

    @Autowired
    public void setUserModelRepository(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    public UserModel generateModel(String pathToLabs) {
        return userModelRepository.get(1);
    }

    public UserModel updateModel(UserModel userModel) {
        userModelRepository.update(userModel);
        return userModel;
    }

    public UserModel getModel(long id) {
        return userModelRepository.get(id);
    }

    public List<UserModel> getAllModels() {
        return userModelRepository.getAll();
    }
}

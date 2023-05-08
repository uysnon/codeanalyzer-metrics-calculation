package com.uysnon.codeanalyzer.teacherui.service;

import com.uysnon.codeanalyzer.teacherui.model.UserModel;
import com.uysnon.codeanalyzer.teacherui.repository.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateModelService {
    private UserModelRepository userModelRepository;

    @Autowired
    public void setUserModelRepository(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    public UserModel generateModel(String pathToLabs){
        return userModelRepository.get(1);
    }

}

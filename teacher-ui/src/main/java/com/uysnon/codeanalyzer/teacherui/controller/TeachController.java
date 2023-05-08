package com.uysnon.codeanalyzer.teacherui.controller;

import com.uysnon.codeanalyzer.teacherui.model.UserModel;
import com.uysnon.codeanalyzer.teacherui.repository.UserModelRepository;
import com.uysnon.codeanalyzer.teacherui.service.GenerateModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;


@Controller
@RequestMapping("model")
public class TeachController {

    private GenerateModelService generateModelService;

    private UserModelRepository userModelRepository;

    @Autowired
    public void setGenerateModelService(GenerateModelService generateModelService) {
        this.generateModelService = generateModelService;
    }

    @Autowired
    public void setUserModelRepository(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    @GetMapping("/select")
    public String selectProject() {
        return "select_project";
    }


    @PostMapping("/generate")
    public String generate(@RequestParam("pathToLabs")String pathToLabs,
                            Model model) {
        UserModel userModel = generateModelService.generateModel(pathToLabs);
        model.addAttribute("userModel", userModel);
        return String.format("redirect:/model/generated/%d", userModel.getId());
    }

    @GetMapping("/generated/{id}")
    public String generated(@PathVariable("id") long id,
                            Model model) {
        UserModel userModel = userModelRepository.get(id);
        model.addAttribute("userModel", userModel);
        return "generated";
    }


    @GetMapping("/save")
    public String result(@PathVariable("id") Long id, Model model) throws IOException {

        return "result";
    }


    @ExceptionHandler({MissingServletRequestPartException.class})
    public String exceptionHandler() {
        return "redirect:/calculate/select";
    }


}

package com.uysnon.codeanalyzer.teacherui.controller;

import com.uysnon.codeanalyzer.teacherui.model.UserModel;
import com.uysnon.codeanalyzer.teacherui.repository.UserModelRepository;
import com.uysnon.codeanalyzer.teacherui.service.Base64Util;
import com.uysnon.codeanalyzer.teacherui.service.GenerateModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


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
    public String generate(@RequestParam("pathToLabs") String pathToLabs,
                           Model model) {
        UserModel userModel = generateModelService.generateModel(pathToLabs);
        model.addAttribute("userModel", userModel);
        return String.format("redirect:/model/generated/%d", userModel.getId());
    }

    @GetMapping("/generated/{id}")
    public String generated(@PathVariable("id") long id,
                            @RequestParam("messageSaved") Optional<String> messageSaved,
                            Model model) {
        UserModel userModel = userModelRepository.get(id);
        model.addAttribute("userModel", userModel);
        messageSaved.ifPresent(s -> model.addAttribute("messageSaved", UriUtils.decode(s, StandardCharsets.UTF_8)));
        return "generated";
    }


    @PostMapping("/save")
    public String result(@RequestParam("id") Long id,
                         @RequestParam("title") String title,
                         Model model) throws IOException {
        UserModel userModel = userModelRepository.get(id);
        userModel.setTitle(title);
        userModelRepository.update(userModel);
        String redirectUrl = String.format("redirect:/model/generated/%d?messageSaved=%s",
                id,
                UriUtils.encode("Сохранение прошло успешно!", StandardCharsets.UTF_8));
        return redirectUrl;

    }


    @ExceptionHandler({MissingServletRequestPartException.class})
    public String exceptionHandler() {
        return "redirect:/calculate/select";
    }


}

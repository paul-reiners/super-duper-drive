package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class HomeController {

    private final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("newFile") FileForm newFile, Model model) {
        model.addAttribute("files", this.fileService.getFileListings());

        return "home";
    }

    @PostMapping("/home")
    public String newFile(FileForm newFile, Model model) throws IOException {
        fileService.addFile(newFile.getFile());
        model.addAttribute("files", fileService.getFileListings());

        return "home";
    }
}

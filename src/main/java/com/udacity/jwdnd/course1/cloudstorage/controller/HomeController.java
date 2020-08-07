package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("newFile") FileForm newFile, Model model) {
        model.addAttribute("files", this.fileService.getFileListings());

        return "home";
    }

    @PostMapping
    public String newFile(Authentication authentication, @ModelAttribute("newFile") FileForm newFile, Model model) throws IOException {
        String userName = authentication.getName();
        fileService.addFile(newFile.getFile(), userName);
        model.addAttribute("files", fileService.getFileListings());

        return "home";
    }

    @GetMapping(
            value = "/get-file",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getFile(@RequestParam String fileName) {
        return fileService.getFile(fileName).getFileData();
    }
}

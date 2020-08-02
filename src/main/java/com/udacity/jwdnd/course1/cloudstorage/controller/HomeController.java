package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private FileService messageListService;

    public HomeController(FileService messageListService) {
        this.messageListService = messageListService;
    }

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("newMessage") FileForm newMessage, Model model) {
        model.addAttribute("files", this.messageListService.getFileListings());
        return "home";
    }

    @PostMapping("/home")
    public String addMessage(@ModelAttribute("newMessage") FileForm messageForm, Model model) {
        messageListService.addFile(messageForm.getText());
        model.addAttribute("files", messageListService.getFileListings());
        messageForm.setText("");
        return "home";
    }
}

package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
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
    public String getHomePage(
            @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newNote") NoteForm newNote, Model model) {
        model.addAttribute("files", this.fileService.getFileListings());

        return "home";
    }

    @PostMapping
    public String newFile(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote, Model model) throws IOException {
        String userName = authentication.getName();
        fileService.addFile(newFile.getFile(), userName);
        model.addAttribute("files", fileService.getFileListings());

        return "home";
    }

    @GetMapping(
            value = "/get-file/{fileName}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileService.getFile(fileName).getFileData();
    }

    @GetMapping(value = "/delete-file/{fileName}")
    public String deleteFile(
            @PathVariable String fileName, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote, Model model) {
        fileService.deleteFile(fileName);
        model.addAttribute("files", fileService.getFileListings());

        return "home";
    }
}

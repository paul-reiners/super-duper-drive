package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newNote") NoteForm newNote, Model model) {
        model.addAttribute("notes", this.noteService.getNoteListings());

        return "home";
    }

    @PostMapping("add-note")
    public String newNote(Authentication authentication, @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newNote") NoteForm newNote, Model model) {
        String userName = authentication.getName();
        String newTitle = newNote.getTitle();
        Note existingNote = getNote(newTitle);
        String newDescription = newNote.getDescription();
        if (existingNote == null) {
            noteService.addNote(newTitle, newDescription, userName);
        } else {
            noteService.updateNote(existingNote.getNoteId(), newTitle, newDescription);
        }
        model.addAttribute("notes", noteService.getNoteListings());

        return "home";
    }

    @GetMapping(
            value = "/get-note/{noteTitle}"
    )
    public Note getNote(@PathVariable String noteTitle) {
        return noteService.getNote(noteTitle);
    }

    @GetMapping(value = "/delete-note/{noteTitle}")
    public String deleteNote(@PathVariable String noteTitle, @ModelAttribute("newNote") NoteForm newNote, Model model) {
        noteService.deleteNote(noteTitle);
        model.addAttribute("notes", noteService.getNoteListings());

        return "home";
    }
}

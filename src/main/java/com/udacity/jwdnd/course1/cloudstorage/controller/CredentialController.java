package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("credential")
public class CredentialController {

    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        String userName = authentication.getName();
        model.addAttribute("credentials", this.credentialService.getCredentialListings(userName));

        return "home";
    }

    @PostMapping("add-credential")
    public String newCredential(Authentication authentication, @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        String userName = authentication.getName();
        String newUrl = newCredential.getUrl();
        String credentialIdStr = newCredential.getCredentialId();
        String newPassword = newCredential.getPassword();
        if (credentialIdStr.isEmpty()) {
            credentialService.addCredential(newUrl, userName, "", newPassword);
        } else {
            Credential existingCredential = getCredential(Integer.parseInt(credentialIdStr));
            credentialService.updateCredential(existingCredential.getCredentialId(), newUrl, "", newPassword);
        }
        model.addAttribute("credentials", credentialService.getCredentialListings(userName));

        return "home";
    }

    @GetMapping(value = "/get-credential/{credentialId}")
    public Credential getCredential(@PathVariable Integer credentialId) {
        return credentialService.getCredential(credentialId);
    }

    @GetMapping(value = "/delete-credential/{credentialId}")
    public String deleteCredential(
            Authentication authentication, @PathVariable Integer credentialId, @ModelAttribute("newCredential") CredentialForm newCredential,
            @ModelAttribute("newFile") FileForm newFile, Model model) {
        credentialService.deleteCredential(credentialId);
        String userName = authentication.getName();
        model.addAttribute("credentials", credentialService.getCredentialListings(userName));

        return "home";
    }
}

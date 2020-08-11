package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private final UserMapper userMapper;
    private final CredentialMapper noteMapper;

    public CredentialService(UserMapper userMapper, CredentialMapper noteMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }

    public void addCredential(String url, String userName, String key, String password) {
        Integer userId = userMapper.getUser(userName).getUserId();
        Credential credential = new Credential(0, url, userName, key, password, userId);
        noteMapper.insert(credential);
    }

    public Credential[] getCredentialListings(String userName) {
        return noteMapper.getCredentialListings(userName);
    }

    public Credential getCredential(Integer noteId) {
        return noteMapper.getCredential(noteId);
    }

    public void deleteCredential(Integer noteId) {
        noteMapper.deleteCredential(noteId);
    }

    public void updateCredential(Integer credentialId, String url, String key, String password) {
        noteMapper.updateCredential(credentialId, url, key, password);
    }
}

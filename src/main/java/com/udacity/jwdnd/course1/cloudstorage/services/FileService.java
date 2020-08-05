package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public String[] getFileListings() {
        return fileMapper.getFileListings();
    }

    public void addFile(MultipartFile multipartFile) throws IOException {
        int fileId = 0;
        InputStream fis = multipartFile.getInputStream();
        String fileName = multipartFile.getName();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        Integer userId = 0;
        byte[] fileData = multipartFile.getBytes();
        File file = new File(fileId, fileName, contentType, fileSize, userId, fileData);
        fileId = fileMapper.insert(file);
        System.out.println(fileId);
    }
}

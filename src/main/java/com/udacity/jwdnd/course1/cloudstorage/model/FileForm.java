package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.web.multipart.MultipartFile;

public class FileForm {
    private MultipartFile multiPartFile;

    public MultipartFile getMultiPartFile() {
        return multiPartFile;
    }

    public void setMultiPartFile(MultipartFile multiPartFile) {
        this.multiPartFile = multiPartFile;
    }
}

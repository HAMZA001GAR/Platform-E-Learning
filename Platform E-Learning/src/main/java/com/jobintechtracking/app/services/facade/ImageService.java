package com.jobintechtracking.app.services.facade;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface ImageService {

    public String uploadImage(MultipartFile file) throws IOException;
    InputStream getImage(String filename) throws IOException;
    public String updateFile(String blobFileUrl, MultipartFile file);


}

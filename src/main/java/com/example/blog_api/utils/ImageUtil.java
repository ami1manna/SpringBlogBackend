package com.example.blog_api.utils;

import com.example.blog_api.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ImageUtil {
    @Value("app.upload.dir")
    private  String uploadDir;
    @Value("app.allowed.extension")
    private final String allowedExt = "png";

    public String saveImage(MultipartFile file) {

        // Multipart has app detail of image with  actual image

        if(file.isEmpty()) {
            throw new ApiException("Image file is empty");
        }
        // check extension
        String originalName = file.getOriginalFilename();
        if(originalName == null || !originalName.toLowerCase().endsWith("." + allowedExt)) {
            throw new ApiException("Image file is not supported Need PNG!");
        }

        // ensure upload directory exits
        File dir = new File(uploadDir);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        // generate unique uuid
        String newName = UUID.randomUUID().toString() + "." + allowedExt;

        File dest = new File(dir , newName);
        try{
            file.transferTo(dest);
        }
        catch(IOException e) {
            throw new ApiException("Failed to save image" + e.getMessage());
        }

        // return file path for string in Db
        return uploadDir + File.separator + newName;
    }
}

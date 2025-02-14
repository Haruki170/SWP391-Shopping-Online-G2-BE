package com.example.demo.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class FileUpload {
    public final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";
    public String uploadImage(MultipartFile file) throws IOException {
        Date date = new Date();
        String fileName = file.getOriginalFilename()+date.getTime();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        // Lưu file vào thư mục "uploads"
        file.transferTo(filePath.toFile());  // Chuyển file vào thư mục
        String savePath = "http://localhost:8080/uploads/"+fileName;
        return savePath;
    }
}

package com.example.minireddit.service.imp;

import com.example.minireddit.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path uploadDir;

    public FileStorageServiceImpl(@Value("${app.upload.dir:uploads}") String path) throws IOException {
        this.uploadDir = Path.of(path);
        Files.createDirectories(this.uploadDir);
    }

    @Override
    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        try {
            String ext = "";
            String original = file.getOriginalFilename();
            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf('.'));
            }
            String filename = UUID.randomUUID() + ext;
            Path target = uploadDir.resolve(filename);
            file.transferTo(target.toFile());
            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }
}


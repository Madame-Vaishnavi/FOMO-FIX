package com.project.eventservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageUploadService {

    @Value("${app.upload.dir:${user.home}/event-images}")
    private String uploadDir;

    @Value("${app.upload.max-size:10485760}") // 10MB default
    private long maxFileSize;

    @Value("${app.upload.allowed-types:jpg,jpeg,png,gif}")
    private String allowedTypes;

    public String uploadImage(MultipartFile file) throws IOException {
        // Validate file
        validateFile(file);

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID().toString() + "." + fileExtension;

        // Save file
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the file path that can be used to access the image
        return "/api/events/images/" + uniqueFilename;
    }

    private void validateFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds maximum limit of " + (maxFileSize / 1024 / 1024) + "MB");
        }

        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (fileExtension == null || !allowedTypes.toLowerCase().contains(fileExtension.toLowerCase())) {
            throw new IllegalArgumentException("File type not allowed. Allowed types: " + allowedTypes);
        }

        // Check if file is actually an image by reading first few bytes
        byte[] bytes = file.getBytes();
        if (bytes.length < 4) {
            throw new IllegalArgumentException("Invalid image file");
        }

        // Simple magic number check for common image formats
        if (!isImageFile(bytes)) {
            throw new IllegalArgumentException("File is not a valid image");
        }
    }

    private boolean isImageFile(byte[] bytes) {
        // JPEG
        if (bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xD8 && bytes[2] == (byte) 0xFF) {
            return true;
        }
        // PNG
        if (bytes[0] == (byte) 0x89 && bytes[1] == (byte) 0x50 && bytes[2] == (byte) 0x4E && bytes[3] == (byte) 0x47) {
            return true;
        }
        // GIF
        if ((bytes[0] == (byte) 0x47 && bytes[1] == (byte) 0x49 && bytes[2] == (byte) 0x46)) {
            return true;
        }
        return false;
    }

    public Path getImagePath(String filename) {
        return Paths.get(uploadDir, filename);
    }
}

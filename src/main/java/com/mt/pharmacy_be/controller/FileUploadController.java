package com.mt.pharmacy_be.controller;

import com.mt.pharmacy_be.service.FileStorageService;
import com.mt.pharmacy_be.util.JsonResponse;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FileUploadController {

    FileStorageService fileStorageService;

    /**
     * Handles file upload requests.
     * Author: Thanh Truc
     * Date: 23/07/2025
     * Description: This endpoint allows users to upload files, which are then processed and stored.
     */
    @PermitAll
    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("files") List<MultipartFile> files) {
        return null;// JsonResponse.ok(fileStorageService.uploadFile(files));
    }
}

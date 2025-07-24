package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.exception.ApiException;
import com.mt.pharmacy_be.service.FileStorageService;
import com.mt.pharmacy_be.service.cloudinary.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileStorageServiceImpl implements FileStorageService {

    CloudinaryService cloudinaryService;

    @Override
    public List<String> uploadFile(List<MultipartFile> files) {
        return files.stream()
                .map(file -> {
                    try {
                        return cloudinaryService.uploadImage(file);
                    } catch (IOException e) {
                        throw new ApiException(ErrorCode.FAILED_TO_UPLOAD_IMAGE);
                    }
                })
                .toList();
    }
}

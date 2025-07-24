package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.service.FileStorageService;
import com.mt.pharmacy_be.service.cloudinary.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileStorageServiceImpl implements FileStorageService {

    CloudinaryService cloudinaryService;

    @Override
    public List<String> uploadFile(List<MultipartFile> files) {
        List<CompletableFuture<String>> futures = files.stream()
                .map(cloudinaryService::uploadImageAsync)
                .toList();
        // lỗi sẽ throw ngay (join)
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return futures.stream()
                .map(CompletableFuture::join) // dùng get() để checked exception
                .collect(Collectors.toList());
    }
}

package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.exception.ApiException;
import com.mt.pharmacy_be.service.FileStorageService;
import com.mt.pharmacy_be.service.cloudinary.CloudinaryService;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    CloudinaryService cloudinaryService;

    @Autowired
    public FileStorageServiceImpl(CloudinaryService cloudinaryService,
                                  @Qualifier("taskExecutor") Executor ioTaskExecutor) {
        this.cloudinaryService = cloudinaryService;
        log.info("FileStorageServiceImpl initialized with executor: {}", ioTaskExecutor.getClass().getName());
    }

    @Override
    public List<String> uploadFile(List<MultipartFile> files) {
        if (CollectionUtils.isEmpty(files)) {
            throw new ApiException(ErrorCode.FILES_NOT_EMPTY);
        }
        log.info("Starting file upload process on thread: {}, number of files: {}",
                Thread.currentThread().getName(), files.size());

        List<CompletableFuture<String>> futures = files.stream()
                .map(file -> {
                    log.info("Submitting file {} for async upload on thread: {}",
                            file.getOriginalFilename(), Thread.currentThread().getName());
                    return cloudinaryService.uploadImageAsync(file);
                })
                .toList();

        log.info("All files submitted for upload, waiting for completion on thread: {}",
                Thread.currentThread().getName());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        log.info("All file uploads completed, collecting results on thread: {}",
                Thread.currentThread().getName());

        return futures.stream()
                .map(future -> {
                    try {
                        String url = future.join();
                        log.debug("Retrieved upload result: {}", url);
                        return url;
                    } catch (Exception e) {
                        log.error("Error retrieving upload result", e);
                        throw e;
                    }
                })
                .collect(Collectors.toList());
    }
}
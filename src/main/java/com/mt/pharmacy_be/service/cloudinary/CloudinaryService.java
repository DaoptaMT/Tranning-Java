package com.mt.pharmacy_be.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Service for handling image uploads to Cloudinary.
 * Author: Thanh Truc
 * Date: 22/07/2025
 * Description: This service provides methods to upload images to Cloudinary and return the URL of the uploaded image.
 */
@Service
@Slf4j
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Async
    public CompletableFuture<String> uploadImageAsync(MultipartFile file){
        try {
            String imageUrl = uploadImage(file);
            return CompletableFuture.completedFuture(imageUrl);
        }catch (IOException e) {
            CompletableFuture<String> failed = new CompletableFuture<>();
            failed.completeExceptionally(new ApiException(ErrorCode.FAILED_TO_UPLOAD_IMAGE));
            return failed;
        }
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("secure", true)
        );
        return (String) uploadResult.get("url");
    }
}



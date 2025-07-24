package com.mt.pharmacy_be.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileStorageService {
    List<String> uploadFile(List<MultipartFile> files);
}

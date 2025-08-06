package com.mt.pharmacy_be.service;

import com.mt.pharmacy_be.dto.export.ExportProgressDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Service interface for handling export events related to data exports.
 * This service provides methods to create a stream of export progress updates and send export progress notifications.
 * Author: Thanh Truc
 * Date: 05/08/2025
 * Description: This interface defines the contract for managing export progress events, allowing clients to subscribe to updates via Server-Sent Events (SSE).
 */
@Service
public interface ExportEventService {
    Flux<ExportProgressDTO> createProgressStream();
    void sendExportProgress(ExportProgressDTO progress);
}

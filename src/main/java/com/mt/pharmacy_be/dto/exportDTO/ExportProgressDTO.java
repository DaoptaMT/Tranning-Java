package com.mt.pharmacy_be.dto.exportDTO;

import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for tracking export progress.
 * This class contains information about the export file, its status, and any messages related to the export process.
 * Author: Thanh Truc
 * Date: 05/08/2025
 * Description: This DTO is used to communicate the progress of export operations, including file details and status updates.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ExportProgressDTO {
    private String fileName;
    private String filePath;
    private String status;
    private String message;
    private LocalDateTime when;
}

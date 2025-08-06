package com.mt.pharmacy_be.batch.processor;

import com.mt.pharmacy_be.dto.medicineDTO.MedicineCsvDTO;
import com.mt.pharmacy_be.entity.KindOfMedicineEntity;
import com.mt.pharmacy_be.entity.MedicineEntity;
import com.mt.pharmacy_be.mapper.MedicineMapper;
import com.mt.pharmacy_be.repository.KindOfMedicineRepository;
import com.mt.pharmacy_be.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Processor for converting MedicineCsvDTO to Medicine entity.
 * This class validates the input data, checks for duplicates, and maps it to the Medicine entity.
 * Author: Thanh Truc
 * Date: 30/07/2025
 * Description: This processor handles the conversion of CSV data to the Medicine entity
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MedicineCsvItemProcessor implements ItemProcessor<MedicineCsvDTO, MedicineEntity> {

    private final KindOfMedicineRepository kindOfMedicineRepository;
    private final MedicineRepository medicineRepository;
    private final MedicineMapper medicineMapper;

    // Cache for medicines to check duplicates (name -> kindOfMedicineId -> exists)
    private final Map<String, Set<Long>> existingMedicinesCache = new ConcurrentHashMap<>();

    // Cache for kind of medicines
    private final Map<Long, KindOfMedicineEntity> kindOfMedicineCache = new ConcurrentHashMap<>();

    private boolean cacheInitialized = false;

    private static final int BATCH_SIZE = 500;

    @Override
    public MedicineEntity process(MedicineCsvDTO item) throws Exception {
        log.info("Processing medicine data: {}", item);

        if (!cacheInitialized) {
            initializeCache();
        }

        List<String> validationErrors = validateMedicineCsvDTO(item);
        if (!validationErrors.isEmpty()) {
            log.warn("Validation failed for item: {}. Errors: {}", item, String.join("; ", validationErrors));
            return null;
        }

        // Check for duplicate
        if (StringUtils.hasText(item.getName())) {
            String name = item.getName().toLowerCase();

            if (StringUtils.hasText(item.getKindOfMedicineId())) {
                Long kindOfMedicineId = Long.parseLong(item.getKindOfMedicineId());

                // Check if this medicine with this kind already exists
                if (existingMedicinesCache.containsKey(name) &&
                    existingMedicinesCache.get(name).contains(kindOfMedicineId)) {
                    log.info("Medicine with name '{}' and kind of medicine ID '{}' already exists. Skipping...",
                            item.getName(), kindOfMedicineId);
                    return null;
                }
            }
        }

        MedicineEntity medicineEntity = medicineMapper.toMedicineEntity(item);

        if (StringUtils.hasText(item.getKindOfMedicineId())) {
            Long kindOfMedicineId = Long.parseLong(item.getKindOfMedicineId());

            KindOfMedicineEntity kindOfMedicineEntity = kindOfMedicineCache.get(kindOfMedicineId);

            if (kindOfMedicineEntity == null) {
                // If not in cache, fetch from database and update cache
                Optional<KindOfMedicineEntity> kindOfMedicineOpt = kindOfMedicineRepository.findById(kindOfMedicineId);
                if (kindOfMedicineOpt.isPresent()) {
                    kindOfMedicineEntity = kindOfMedicineOpt.get();
                    kindOfMedicineCache.put(kindOfMedicineId, kindOfMedicineEntity);
                } else {
                    log.warn("Kind of medicine with ID {} not found", kindOfMedicineId);
                }
            }

            if (kindOfMedicineEntity != null) {
                medicineEntity.setKindOfMedicineEntity(kindOfMedicineEntity);

                // Update cache with new medicine
                if (StringUtils.hasText(item.getName())) {
                    String name = item.getName().toLowerCase();
                    existingMedicinesCache.computeIfAbsent(name, k -> new HashSet<>())
                                          .add(kindOfMedicineId);
                }
            }
        }

        medicineEntity.setCode(String.valueOf(new Random().nextInt(1000000)));
        medicineEntity.setFlagDeleted(false);

        log.info("Successfully processed medicine: {}", medicineEntity);
        return medicineEntity;
    }

    private void initializeCache() {
        log.info("Initializing medicine cache for duplicate checking with batch loading");

        int pageNumber = 0;
        boolean hasMoreMedicines = true;
        int totalMedicines = 0;

        // Load medicines using pagination to avoid loading all data at once
        while (hasMoreMedicines) {
            Pageable pageable = PageRequest.of(pageNumber, BATCH_SIZE);
            Page<MedicineEntity> medicinePage = medicineRepository.findAll(pageable);

            if (medicinePage.hasContent()) {
                List<MedicineEntity> medicineEntityBatches = medicinePage.getContent();
                totalMedicines += medicineEntityBatches.size();

                for (MedicineEntity medicineEntity : medicineEntityBatches) {
                    if (medicineEntity.getName() != null) {
                        String name = medicineEntity.getName().toLowerCase();

                        // Add to existing medicines cache
                        if (medicineEntity.getKindOfMedicineEntity() != null) {
                            Long kindId = medicineEntity.getKindOfMedicineEntity().getId();
                            existingMedicinesCache.computeIfAbsent(name, k -> new HashSet<>())
                                                 .add(kindId);

                            // Cache the kind of medicine as well
                            kindOfMedicineCache.putIfAbsent(kindId, medicineEntity.getKindOfMedicineEntity());
                        } else {
                            existingMedicinesCache.putIfAbsent(name, new HashSet<>());
                        }
                    }
                }

                // Check if we have more pages
                hasMoreMedicines = pageNumber < medicinePage.getTotalPages() - 1;
                pageNumber++;

                log.info("Loaded batch {} of medicines, batch size: {}",
                        pageNumber, medicineEntityBatches.size());
            } else {
                hasMoreMedicines = false;
            }
        }

        // Load all kinds of medicines
        pageNumber = 0;
        boolean hasMoreKinds = true;
        int totalKinds = 0;

        while (hasMoreKinds) {
            Pageable pageable = PageRequest.of(pageNumber, BATCH_SIZE);
            Page<KindOfMedicineEntity> kindPage = kindOfMedicineRepository.findAll(pageable);

            if (kindPage.hasContent()) {
                List<KindOfMedicineEntity> kindBatch = kindPage.getContent();
                totalKinds += kindBatch.size();

                kindBatch.forEach(kind ->
                    kindOfMedicineCache.putIfAbsent(kind.getId(), kind)
                );

                // Check if we have more pages
                hasMoreKinds = pageNumber < kindPage.getTotalPages() - 1;
                pageNumber++;

                log.info("Loaded batch {} of kinds of medicine, batch size: {}",
                        pageNumber, kindBatch.size());
            } else {
                hasMoreKinds = false;
            }
        }

        cacheInitialized = true;
        log.info("Cache initialized with {} medicines and {} kinds of medicines",
                totalMedicines, totalKinds);
    }

    private List<String> validateMedicineCsvDTO(MedicineCsvDTO item) {
        List<String> errors = new ArrayList<>();

        if (!StringUtils.hasText(item.getName())) {
            errors.add("Name is required");
        }

        if (item.getPrice() == null) {
            errors.add("Price is required");
        } else if (item.getPrice() < 0) {
            errors.add("Price cannot be negative");
        }

        if (item.getQuantity() == null) {
            errors.add("Quantity is required");
        } else if (item.getQuantity() < 0) {
            errors.add("Quantity cannot be negative");
        }

        if (item.getVat() != null && item.getVat() < 0) {
            errors.add("VAT cannot be negative");
        }

        if (item.getRetailProfit() != null && item.getRetailProfit() < 0) {
            errors.add("Retail profit cannot be negative");
        }

        if (StringUtils.hasText(item.getKindOfMedicineId())) {
            try {
                long kindOfMedicineId = Long.parseLong(item.getKindOfMedicineId());
                if (kindOfMedicineId <= 0) {
                    errors.add("Kind of medicine ID must be positive");
                }
            } catch (NumberFormatException e) {
                errors.add("Kind of medicine ID must be a valid number");
            }
        }

        return errors;
    }
}

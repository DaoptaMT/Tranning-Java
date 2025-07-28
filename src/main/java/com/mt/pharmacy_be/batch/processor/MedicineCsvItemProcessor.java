package com.mt.pharmacy_be.batch.processor;

import com.mt.pharmacy_be.dto.medicineDTO.MedicineCsvDTO;
import com.mt.pharmacy_be.entity.Kind_Of_Medicine;
import com.mt.pharmacy_be.entity.Medicine;
import com.mt.pharmacy_be.mapper.MedicineMapper;
import com.mt.pharmacy_be.repository.KindOfMedicineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Processor for converting MedicineCsvDTO to Medicine entity.
 * This class validates the input data and maps it to the Medicine entity.
 * Author: Thanh Truc
 * Date: 28/07/2024
 * Description: This processor handles the conversion of CSV data to the Medicine entity,
 * including validation of required fields and setting the kind of medicine based on ID.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MedicineCsvItemProcessor implements ItemProcessor<MedicineCsvDTO, Medicine> {

    private final KindOfMedicineRepository kindOfMedicineRepository;
    private final MedicineMapper medicineMapper;

    @Override
    public Medicine process(MedicineCsvDTO item) throws Exception {
        log.info("Processing medicine data: {}", item);

        List<String> validationErrors = validateMedicineCsvDTO(item);

        if (!validationErrors.isEmpty()) {
            log.warn("Validation failed for item: {}. Errors: {}", item, String.join("; ", validationErrors));
            return null;
        }

        Medicine medicine = medicineMapper.toMedicineEntity(item);

        if (StringUtils.hasText(item.getKindOfMedicineId())) {
            Long kindOfMedicineId = Long.parseLong(item.getKindOfMedicineId());
            Optional<Kind_Of_Medicine> kindOfMedicine = kindOfMedicineRepository.findById(kindOfMedicineId);
            if (kindOfMedicine.isPresent()) {
                medicine.setKindOfMedicine(kindOfMedicine.get());
            } else {
                log.warn("Kind of medicine with ID {} not found", kindOfMedicineId);
            }
        }

        medicine.setCode(String.valueOf(new Random().nextInt(1000000)));
        medicine.setFlagDeleted(false);

        log.info("Successfully processed medicine: {}", medicine);
        return medicine;
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

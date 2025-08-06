package com.mt.pharmacy_be.util;

import com.mt.pharmacy_be.dto.medicineDTO.MedicineSearchRequestDTO;
import com.mt.pharmacy_be.entity.MedicineEntity;
import com.mt.pharmacy_be.repository.specification.MedicineSpecification;
import com.mt.pharmacy_be.repository.specification.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * MedicineSpecificationFactory is a service that builds JPA Specifications for querying medicines.
 * It constructs complex queries based on the provided search criteria encapsulated in MedicineSearchRequestDTO.
 * Author: Thanh Truc
 * Date: 05/08/2025
 * Description: This class provides methods to create specifications for filtering medicines based on various attributes such as
 */
@Service
public class MedicineSpecificationFactory {

    public Specification<MedicineEntity> buildMedicineSpecification(MedicineSearchRequestDTO request) {
        SpecificationBuilder<MedicineEntity> builder = new SpecificationBuilder<>();

        builder.and(MedicineSpecification.hasCode(request.getCode()))
                .and(MedicineSpecification.hasName(request.getName()))
                .and(MedicineSpecification.minPrice(parseDouble(request.getMinPrice())))
                .and(MedicineSpecification.maxPrice(parseDouble(request.getMaxPrice())))
                .and(MedicineSpecification.minQuantity(parseLong(request.getMinQuantity())))
                .and(MedicineSpecification.maxQuantity(parseLong(request.getMaxQuantity())))
                .and(MedicineSpecification.hasMaker(request.getMaker()))
                .and(MedicineSpecification.hasOrigin(request.getOrigin()))
                .and(MedicineSpecification.hasActiveElement(request.getActiveElement()))
                .and(MedicineSpecification.hasKindOfMedicine(parseLong(request.getKindOfMedicineId())))
                .and(MedicineSpecification.sortPrice(request.getSortPrice()));

        return builder.build();
    }

    private Double parseDouble(String value) {
        return (value == null || value.isBlank()) ? null : Double.valueOf(value);
    }

    private Long parseLong(String value) {
        return (value == null || value.isBlank()) ? null : Long.valueOf(value);
    }
}
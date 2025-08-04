package com.mt.pharmacy_be.repository;

import com.mt.pharmacy_be.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>, JpaSpecificationExecutor<Medicine> {

    /**
     * Checks if a medicine with the given name and kind of medicine ID exists.
     * Author: Thanh Truc
     * Date: 29/07/2025
     * Description: This method checks for the existence of a medicine by its name (case-insensitive)
     */
    boolean existsByNameIgnoreCaseAndKindOfMedicineId(String name, Long kindOfMedicineId);
}

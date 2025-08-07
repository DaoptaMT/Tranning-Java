package com.mt.pharmacy_be.repository;

import com.mt.pharmacy_be.entity.ImageMedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageMedicineRepository extends JpaRepository<ImageMedicineEntity, Long> {

    /*
     * Find images by medicine ID.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: This method retrieves a list of images associated with a specific medicine ID.
     */
    Optional<List<ImageMedicineEntity>> findByMedicineEntityId(Long medicineId);
}

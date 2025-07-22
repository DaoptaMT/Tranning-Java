package com.mt.pharmacy_be.repository;

import com.mt.pharmacy_be.entity.Image_Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageMedicineRepository extends JpaRepository<Image_Medicine, Long> {

    /*
     * Find images by medicine ID.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: This method retrieves a list of images associated with a specific medicine ID.
     */
    Optional<List<Image_Medicine>> findByMedicineId(Long medicineId);
}

package com.mt.pharmacy_be.repository;

import com.mt.pharmacy_be.entity.Unit_Detail;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitDetailRepository extends JpaRepository<Unit_Detail, Long> {

    /**
     * Find all Unit_Detail entities by Medicine ID.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This method retrieves a list of Unit_Detail entities associated with a specific Medicine ID.
     */
    List<Unit_Detail> findByMedicineEntityId(@Param("id") Long id);
}

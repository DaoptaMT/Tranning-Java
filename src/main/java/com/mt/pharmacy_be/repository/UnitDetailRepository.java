package com.mt.pharmacy_be.repository;

import com.mt.pharmacy_be.entity.UnitDetailEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitDetailRepository extends JpaRepository<UnitDetailEntity, Long> {

    /**
     * Find all Unit_Detail entities by Medicine ID.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This method retrieves a list of Unit_Detail entities associated with a specific Medicine ID.
     */
    List<UnitDetailEntity> findByMedicineEntityId(@Param("id") Long id);

    @Query("SELECT ud FROM UnitDetailEntity ud WHERE ud.medicineEntity.id = :medicineId AND ud.unitEntity.flag_deleted = false")
    List<UnitDetailEntity> findValidUnitDetailsByMedicineEntityId(@Param("medicineId") Long medicineId);

}

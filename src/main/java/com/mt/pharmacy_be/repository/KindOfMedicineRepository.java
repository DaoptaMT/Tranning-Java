package com.mt.pharmacy_be.repository;

import com.mt.pharmacy_be.entity.KindOfMedicineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KindOfMedicineRepository extends JpaRepository<KindOfMedicineEntity, Long> {
    Optional<KindOfMedicineEntity> findByCode(String code);

    Page<KindOfMedicineEntity> findByName(String name, Pageable pageable);

    Boolean existsByName(String name);
}

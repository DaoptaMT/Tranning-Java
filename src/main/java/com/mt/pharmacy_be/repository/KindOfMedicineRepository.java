package com.mt.pharmacy_be.repository;

import com.mt.pharmacy_be.entity.KindOfMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KindOfMedicineRepository extends JpaRepository<KindOfMedicine, Long> {
    Optional<KindOfMedicine> findByCode(String code);

    List<KindOfMedicine> findByName(String name);
}

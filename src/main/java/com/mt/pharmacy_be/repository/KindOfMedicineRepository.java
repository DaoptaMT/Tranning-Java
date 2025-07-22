package com.mt.pharmacy_be.repository;

import com.mt.pharmacy_be.entity.Kind_Of_Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KindOfMedicineRepository extends JpaRepository<Kind_Of_Medicine, Long> {
}

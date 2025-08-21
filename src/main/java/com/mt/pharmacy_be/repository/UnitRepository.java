package com.mt.pharmacy_be.repository;

import com.mt.pharmacy_be.entity.UnitEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

    Boolean existsByName(String name);

    Page<UnitEntity> findByName(String name, Pageable pageable);
}

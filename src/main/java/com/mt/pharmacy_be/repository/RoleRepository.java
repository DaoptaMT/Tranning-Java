package com.mt.pharmacy_be.repository;

import com.mt.pharmacy_be.entity.RoleEntity;
import com.mt.pharmacy_be.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    /**
     * Find by Name
     * Author: Thanh Truc
     * Date: 17/07/2025
     * Description: This method retrieves a RoleEntity by its name.
     */
    Optional<RoleEntity> findByName(RoleType name);
}

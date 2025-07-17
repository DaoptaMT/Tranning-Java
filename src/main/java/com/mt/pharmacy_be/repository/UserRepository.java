package com.mt.pharmacy_be.repository;

import com.mt.pharmacy_be.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * Find by Username
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: This method retrieves a UserEntity by its username.
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Check if username exists
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: This method checks if a UserEntity with the given username exists.
     */
    Boolean existsByUsername(String username);
}

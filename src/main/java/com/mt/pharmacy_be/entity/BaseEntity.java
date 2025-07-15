package com.mt.pharmacy_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public abstract class BaseEntity {

    @Column(updatable = false)
    @CreatedBy
    String createdBy;

    @Column(updatable = false)
    @CreationTimestamp
    LocalDateTime createdDate;

    @UpdateTimestamp
    LocalDateTime modifiedDate;

    @LastModifiedBy
    String modifiedBy;
}

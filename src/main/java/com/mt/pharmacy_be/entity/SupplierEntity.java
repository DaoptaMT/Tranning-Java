package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "flag_deleted = false")
@Table(name = "supplier")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplierEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String code;

    String name;

    String email;

    String address;

    String phoneNumber;

    @Column(columnDefinition = "NTEXT")
    String note;

    Boolean flagDeleted;
}

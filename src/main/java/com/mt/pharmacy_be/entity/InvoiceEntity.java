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
@Table(name = "invoice")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String code;

    String documentNumber;

    LocalDateTime creationDate;

    Double paid;

    @Column(columnDefinition = "NTEXT")
    String note;

    Boolean flagDeleted;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    SupplierEntity supplierEntity;
}

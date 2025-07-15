package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "flag_deleted = false")
@Table(name = "order")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String code;

    LocalDateTime dateTime;

    @Column(columnDefinition = "NTEXT")
    String note;

    Boolean flagDeleted;
}

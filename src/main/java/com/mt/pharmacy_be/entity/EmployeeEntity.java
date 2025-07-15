package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "flag_deleted = false")
@Table(name = "employee")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String code;

    @Column(columnDefinition = "NTEXT")
    String image;

    String nameEmployee;

    String address;

    String phoneNumber;

    LocalDate birthDate;

    LocalDate startDate;

    String cartId;

    @Column(columnDefinition = "NTEXT")
    String note;

    Boolean flagDeleted;

    @OneToOne
    @JoinColumn(name = "app_user_id")
    UserEntity userEntity;
}

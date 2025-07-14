package com.mt.pharmacy_be.entity;

import com.mt.pharmacy_be.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "isDeleted = false")
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String fullName;

    String email;

    String password;

    String phone;

    @Enumerated(EnumType.STRING)
    UserStatus status;

    Boolean isDeleted;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    RoleEntity roleEntity;
}

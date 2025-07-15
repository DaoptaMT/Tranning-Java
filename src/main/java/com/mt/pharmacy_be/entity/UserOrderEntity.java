package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "flag_deleted = false")
@Table(name = "user_order")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserOrderEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne()
    @JoinColumn(name = "app_user_id")
    UserEntity userEntity;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    OrderEntity orderEntity;
}

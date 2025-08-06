package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "cart_details")
@Where(clause = "flag_deleted = false")
public class CartDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double current_price;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private MedicineEntity medicineEntity;

    private Long quantity;


}
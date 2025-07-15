package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.query.Order;

@Getter
@Setter
@Entity
@Table(name = "cart_details")
public class Cart_Details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double current_price;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private Long quantity;


}
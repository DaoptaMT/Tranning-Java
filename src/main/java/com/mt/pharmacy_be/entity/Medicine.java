package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "medicine")
@Where(clause = "flag_deleted = false")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private Double price;
    private Long quantity;
    private Float vat;
    private String note;
    private String maker;
    private String origin;
    private Float retailProfit;
    @ManyToOne()
    @JoinColumn(name = "kind_of_medicine_id")
    private KindOfMedicine kindOfMedicine;
    private boolean flagDeleted;
    private String activeElement;

}
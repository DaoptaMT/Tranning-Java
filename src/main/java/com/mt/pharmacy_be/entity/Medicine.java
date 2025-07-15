package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private  Double price;
    private Long quantity;
    private Float vat;
    private String note;
    private String maker;
    private String origin;
    private Float retailProfit;
    @ManyToOne()
    @JoinColumn(name = "kind_of_medicine_id")
    private Kind_Of_Medicine kindOfMedicine;
    private boolean flagDeleted;
    private String activeElement;

}
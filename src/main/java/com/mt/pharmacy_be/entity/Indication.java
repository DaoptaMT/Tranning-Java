package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "indication")
public class Indication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer dosage;
    private Integer frequency;
    private boolean flag_deleted;
    @ManyToOne()
    @JoinColumn(name = "medicine_id")
    private  Medicine medicine;
    @ManyToOne()
    @JoinColumn(name = "perscription_id")
    private Prescription prescription;

}
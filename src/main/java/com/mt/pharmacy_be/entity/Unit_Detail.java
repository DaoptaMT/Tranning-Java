package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "unit_detail")
public class Unit_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean flag_deleted;
    private Long conversion_unit;
    @ManyToOne()
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
    @ManyToOne()
    @JoinColumn(name = "unit_id")
    private Unit unit;

}
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private boolean flag_deleted;
    private Long conversion_unit;
    private Long medicine_id;
    private Long unit_id;

}
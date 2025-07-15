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
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private Integer dosage;
    private Integer frequency;
    private boolean flag_deleted;
    private Long medicine_id;
    private Long perscription_id;

}
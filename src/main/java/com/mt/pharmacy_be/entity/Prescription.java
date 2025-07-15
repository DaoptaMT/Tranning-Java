package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prescription")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private Long id;
    private String code;
    private String name;
    private String symptoms;
    private String note;
    private Integer duration;
    private boolean flag_deleted;
    private Long patient_id;

}
package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "prescription")
@Where(clause = "flag_deleted = false")
public class PrescriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String code;

    private String name;

    private String symptoms;

    private String note;

    private Integer duration;

    private boolean flag_deleted;

    @ManyToOne()
    @JoinColumn(name = "patient_id")
    private PatientEntity patientEntity;

}
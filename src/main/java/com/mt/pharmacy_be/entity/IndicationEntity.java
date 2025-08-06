package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "indication")
@Where(clause = "flag_deleted = false")
public class IndicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer dosage;

    private Integer frequency;

    private boolean flag_deleted;

    @ManyToOne()
    @JoinColumn(name = "medicine_id")
    private MedicineEntity medicineEntity;

    @ManyToOne()
    @JoinColumn(name = "prescription_id")
    private PrescriptionEntity prescriptionEntity;

}
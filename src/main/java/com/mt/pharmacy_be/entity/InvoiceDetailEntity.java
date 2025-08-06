package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "invoice_details")
@Where(clause = "flag_deleted = false")
public class InvoiceDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float discount;

    private Integer medicine_quantity;

    private String lot;

    private boolean flag_deleted;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private InvoiceEntity invoiceEntity;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private MedicineEntity medicineEntity;




}
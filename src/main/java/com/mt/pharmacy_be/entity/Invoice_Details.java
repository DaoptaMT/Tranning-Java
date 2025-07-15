package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invoice_details")
public class Invoice_Details {

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
    private Medicine medicine;




}
package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "unit_detail")
@Where(clause = "flag_deleted = false")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
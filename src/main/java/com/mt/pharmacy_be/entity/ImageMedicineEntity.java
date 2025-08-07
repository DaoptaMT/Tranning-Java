package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "flag_deleted = false")
@Table(name = "image_medicine")
public class ImageMedicineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image_path;

    private boolean flag_deleted;

    @ManyToOne()
    @JoinColumn(name = "medicine_id")
    private MedicineEntity medicineEntity;
}
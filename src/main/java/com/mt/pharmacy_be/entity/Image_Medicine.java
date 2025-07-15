package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "image_medicine")
public class Image_Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private String image_path;
    private boolean flag_deleted;
    private Long medicine_id;
}
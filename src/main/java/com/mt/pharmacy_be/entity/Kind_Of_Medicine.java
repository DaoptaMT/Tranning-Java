package com.mt.pharmacy_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "kind_of_medicine")
public class Kind_Of_Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private String code;
    private String name;
    private boolean flag_deleted;
}


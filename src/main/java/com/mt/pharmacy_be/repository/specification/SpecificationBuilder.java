package com.mt.pharmacy_be.repository.specification;

import org.springframework.data.jpa.domain.Specification;

/**
 * SpecificationBuilder is a utility class to build JPA Specifications dynamically.
 * Author: Thanh Truc
 * Date: 22/07/2025
 * Description: This class allows for the construction of complex queries by combining multiple specifications using logical AND operations.
 */
public class SpecificationBuilder<T> {

    private Specification<T> specification;

    public SpecificationBuilder() {
        this.specification = (root, query, criteriaBuilder) -> null;
    }

    public SpecificationBuilder<T> and(Specification<T> spec) {
        if (spec != null) {
            this.specification = this.specification.and(spec);
        }
        return this;
    }

    public Specification<T> build() {
        return this.specification;
    }
}
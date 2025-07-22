package com.mt.pharmacy_be.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    Integer page;
    Integer pageSize;
    Integer totalPages;
    T items;
}
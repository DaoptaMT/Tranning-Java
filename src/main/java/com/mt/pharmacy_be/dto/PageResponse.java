package com.mt.pharmacy_be.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * DTO representing a paginated response.
 * Author: Thanh Truc
 * Date: 21/07/2025
 * Description: This class encapsulates the details of a paginated response
 */
@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    Integer page;
    Integer pageSize;
    Integer totalPages;
    T items;
}

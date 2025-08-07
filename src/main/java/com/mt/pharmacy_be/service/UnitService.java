package com.mt.pharmacy_be.service;

import com.mt.pharmacy_be.dto.PageResponse;
import com.mt.pharmacy_be.dto.unitDTO.UnitRequestDTO;
import com.mt.pharmacy_be.dto.unitDTO.UnitResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UnitService {

    PageResponse<?> getAll(int page, int pageSize);

    UnitResponseDTO getById(Long id);

    UnitResponseDTO create(UnitRequestDTO unit);

    UnitResponseDTO update(Long id, UnitRequestDTO updatedUnit);

    void delete(Long id);

    PageResponse<?> searchByName(String name, int page, int pageSize);
}

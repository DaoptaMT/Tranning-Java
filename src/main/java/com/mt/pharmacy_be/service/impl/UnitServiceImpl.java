package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.dto.PageResponse;
import com.mt.pharmacy_be.dto.unitDTO.UnitRequestDTO;
import com.mt.pharmacy_be.dto.unitDTO.UnitResponseDTO;
import com.mt.pharmacy_be.entity.Unit;
import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.exception.ApiException;
import com.mt.pharmacy_be.mapper.UnitMapper;
import com.mt.pharmacy_be.repository.UnitRepository;
import com.mt.pharmacy_be.service.UnitService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UnitServiceImpl implements UnitService {

    UnitRepository unitRepository;
    UnitMapper unitMapper;

    @Override
    public PageResponse<?> getAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, pageSize);
        Page<Unit> unitPage = unitRepository.findAll(pageable);
        List<UnitResponseDTO> unitResponseDTOS = unitPage.map(unitMapper::toUnitResponseDTO)
                .stream().collect(Collectors.toList());

        return PageResponse.builder()
                .page(page)
                .pageSize(pageSize)
                .totalPages(unitPage.getTotalPages())
                .items(unitResponseDTOS)
                .build();
    }

    @Override
    public UnitResponseDTO getById(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.UNIT_NOT_FOUND));
        return unitMapper.toUnitResponseDTO(unit);
    }

    @Override
    public UnitResponseDTO create(UnitRequestDTO unit) {
        if (unitRepository.existsByName(unit.getName())) {
            throw new ApiException(ErrorCode.UNIT_ALREADY_EXISTS);
        }
        Unit newUnit = unitMapper.toUnit(unit);
        newUnit.setFlag_deleted(false);

        Unit savedUnit = unitRepository.save(newUnit);
        return unitMapper.toUnitResponseDTO(savedUnit);
    }

    @Override
    public UnitResponseDTO update(Long id, UnitRequestDTO updatedUnit) {
        Unit existingUnit = unitRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.UNIT_NOT_FOUND));

        if (unitRepository.existsByName(updatedUnit.getName()) &&
                !existingUnit.getName().equals(updatedUnit.getName())) {
            throw new ApiException(ErrorCode.UNIT_ALREADY_EXISTS);
        }

        Unit newUnit = unitMapper.toUnit(updatedUnit);
        newUnit.setId(id);

        unitRepository.save(newUnit);
        return unitMapper.toUnitResponseDTO(newUnit);
    }

    @Override
    public void delete(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.UNIT_NOT_FOUND));

        unit.setFlag_deleted(true);
        unitRepository.save(unit);

    }

    @Override
    public PageResponse<?> searchByName(String name, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, pageSize);
        Page<Unit> unitPage = unitRepository.findByName(name, pageable);
        List<UnitResponseDTO> unitResponseDTOS = unitPage.map(unitMapper::toUnitResponseDTO)
                .stream().collect(Collectors.toList());
        return PageResponse.builder()
                .page(page)
                .pageSize(pageSize)
                .totalPages(unitPage.getTotalPages())
                .items(unitResponseDTOS)
                .build();
    }
}

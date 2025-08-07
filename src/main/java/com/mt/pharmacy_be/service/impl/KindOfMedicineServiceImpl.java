package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.dto.PageResponse;
import com.mt.pharmacy_be.dto.kindOfMedicineDTO.KindOfMedicineRequestDTO;
import com.mt.pharmacy_be.dto.kindOfMedicineDTO.KindOfMedicineResponseDTO;
import com.mt.pharmacy_be.entity.KindOfMedicineEntity;
import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.exception.ApiException;
import com.mt.pharmacy_be.repository.KindOfMedicineRepository;
import com.mt.pharmacy_be.service.KindOfMedicineService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class KindOfMedicineServiceImpl implements KindOfMedicineService {

    ModelMapper modelMapper;
    KindOfMedicineRepository kindOfMedicineRepository;

    @Override
    public KindOfMedicineResponseDTO createKindOfMedicine(KindOfMedicineRequestDTO requestDTO) {
        if (kindOfMedicineRepository.existsByName(requestDTO.getName())) {
            throw new ApiException(ErrorCode.KIND_EXISTED);
        }
        KindOfMedicineEntity kindOfMedicineEntity = modelMapper.map(requestDTO, KindOfMedicineEntity.class);

        kindOfMedicineEntity.setCode(String.valueOf(new Random().nextInt(1000000)));

        kindOfMedicineRepository.save(kindOfMedicineEntity);

        return modelMapper.map(getKindOfMedicineByCode(kindOfMedicineEntity.getCode()),
                KindOfMedicineResponseDTO.class);
    }

    @Override
    public PageResponse<?> getKindOfMedicineByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size);

        Page<KindOfMedicineEntity> kindOfMedicines = kindOfMedicineRepository.findByName(name, pageable);

        List<KindOfMedicineResponseDTO> responseDTOs = kindOfMedicines.stream()
                .map(item -> modelMapper.map(item, KindOfMedicineResponseDTO.class))
                .toList();

        return PageResponse.builder()
                .page(page)
                .pageSize(size)
                .totalPages(kindOfMedicines.getTotalPages())
                .items(responseDTOs)
                .build();
    }

    @Override
    public KindOfMedicineResponseDTO getKindOfMedicineByCode(String code) {
        KindOfMedicineEntity kindOfMedicineEntity = kindOfMedicineRepository.findByCode(code)
                .orElseThrow(() -> new ApiException(ErrorCode.KIND_NOT_FOUND));
        return modelMapper.map(kindOfMedicineEntity, KindOfMedicineResponseDTO.class);
    }

    @Override
    public PageResponse<?> getAllKindOfMedicines(int page, int size) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size);
        Page<KindOfMedicineEntity> pageResult = kindOfMedicineRepository.findAll(pageable);
        List<KindOfMedicineResponseDTO> medicineResponseDTOList = pageResult.stream()
                .map(item -> modelMapper.map(item, KindOfMedicineResponseDTO.class))
                .toList();

        return PageResponse.builder()
                .page(page)
                .pageSize(size)
                .totalPages(pageResult.getTotalPages())
                .items(medicineResponseDTOList)
                .build();
    }

    @Override
    public void deleteKindOfMedicine(Long id) {
        KindOfMedicineEntity kindOfMedicineEntity = kindOfMedicineRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.KIND_NOT_FOUND));

        kindOfMedicineEntity.setFlag_deleted(true);

        kindOfMedicineRepository.save(kindOfMedicineEntity);
    }

    @Override
    public KindOfMedicineResponseDTO updateKindOfMedicine(Long id, KindOfMedicineRequestDTO kindOfMedicineRequestDTO) {
        KindOfMedicineEntity existingEntity = kindOfMedicineRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.KIND_NOT_FOUND));

        if (kindOfMedicineRepository.existsByName(kindOfMedicineRequestDTO.getName()) &&
                !existingEntity.getName().equals(kindOfMedicineRequestDTO.getName())) {
            throw new ApiException(ErrorCode.KIND_EXISTED);
        }

        KindOfMedicineEntity kindOfMedicineEntity = modelMapper.map(kindOfMedicineRequestDTO, KindOfMedicineEntity.class);

        kindOfMedicineEntity.setId(existingEntity.getId());
        kindOfMedicineEntity.setCode(existingEntity.getCode());

        kindOfMedicineRepository.save(kindOfMedicineEntity);

        return modelMapper.map(kindOfMedicineEntity, KindOfMedicineResponseDTO.class);
    }

    @Override
    public KindOfMedicineResponseDTO getById(Long id) {
        KindOfMedicineEntity kindOfMedicineEntity = kindOfMedicineRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.KIND_NOT_FOUND));
        return modelMapper.map(kindOfMedicineEntity, KindOfMedicineResponseDTO.class);
    }


}

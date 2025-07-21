package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.dto.PageResponse;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineResponseDTO;
import com.mt.pharmacy_be.dto.unitDetailDTO.UnitDetailResponseDTO;
import com.mt.pharmacy_be.entity.Medicine;
import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.exception.ApiException;
import com.mt.pharmacy_be.mapper.MedicineMapper;
import com.mt.pharmacy_be.repository.MedicineRepository;
import com.mt.pharmacy_be.repository.UnitDetailRepository;
import com.mt.pharmacy_be.service.MedicineService;
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
public class MedicineServiceImpl implements MedicineService {
    MedicineRepository medicineRepository;
    MedicineMapper medicineMapper;
    UnitDetailRepository unitDetailRepository;

    /**
     * Retrieves a paginated list of all medicines along with their unit details.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This method fetches all medicines from the database,
     */
    @Override
    public PageResponse<?> getAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, pageSize);
        Page<Medicine> medicinePage = medicineRepository.findAll(pageable);
        List<MedicineResponseDTO> medicineResponseDTOList = medicinePage.map(this::getMedicineResponseDTO)
                .stream().collect(Collectors.toList());

        return PageResponse.builder()
                .page(page)
                .pageSize(pageSize)
                .totalPages(medicinePage.getTotalPages())
                .items(medicineResponseDTOList)
                .build();
    }

    /**
     * Retrieves a medicine by its ID along with its unit details.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This method fetches a specific medicine from the database
     */
    @Override
    public MedicineResponseDTO getById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.MEDICINE_NOT_FOUND));
        return getMedicineResponseDTO(medicine);
    }

    /**
     * Constructs a MedicineResponseDTO from a Medicine entity.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This method maps a Medicine entity to a MedicineResponseDTO,
     */
    private MedicineResponseDTO getMedicineResponseDTO(Medicine medicine) {
        MedicineResponseDTO responseDTO = medicineMapper.toMedicineResponseDTO(medicine);
        List<UnitDetailResponseDTO> unitDetails = unitDetailRepository.findByMedicineId(medicine.getId())
                .stream()
                .map(unitDetail -> UnitDetailResponseDTO.builder()
                        .conversionUnit(unitDetail.getConversion_unit())
                        .id(unitDetail.getId())
                        .unitId(unitDetail.getUnit().getId())
                        .unitName(unitDetail.getUnit().getName())
                        .build())
                .collect(Collectors.toList());
        responseDTO.setUnitDetails(unitDetails);

        return responseDTO;
    }
}

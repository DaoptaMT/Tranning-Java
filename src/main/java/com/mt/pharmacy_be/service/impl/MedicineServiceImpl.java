package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.dto.PageResponse;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineRequestDTO;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineResponseDTO;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineSearchRequestDTO;
import com.mt.pharmacy_be.dto.unitDetailDTO.UnitDetailResponseDTO;
import com.mt.pharmacy_be.entity.ImageMedicineEntity;
import com.mt.pharmacy_be.entity.KindOfMedicineEntity;
import com.mt.pharmacy_be.entity.MedicineEntity;
import com.mt.pharmacy_be.entity.UnitDetailEntity;
import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.exception.ApiException;
import com.mt.pharmacy_be.mapper.MedicineMapper;
import com.mt.pharmacy_be.repository.*;
import com.mt.pharmacy_be.service.MedicineService;
import com.mt.pharmacy_be.util.MedicineSpecificationFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MedicineServiceImpl implements MedicineService {
    MedicineRepository medicineRepository;
    MedicineMapper medicineMapper;
    UnitDetailRepository unitDetailRepository;
    ImageMedicineRepository imageMedicineRepository;
    KindOfMedicineRepository kindOfMedicineRepository;
    UnitRepository unitRepository;
    MedicineSpecificationFactory medicineSpecificationFactory;

    /**
     * Retrieves a paginated list of all medicines along with their unit details.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This method fetches all medicines from the database,
     */
    @Override
    public PageResponse<?> getAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, pageSize);
        Page<MedicineEntity> medicinePage = medicineRepository.findAll(pageable);
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
        MedicineEntity medicineEntity = medicineRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.MEDICINE_NOT_FOUND));
        return getMedicineResponseDTO(medicineEntity);
    }

    /**
     * Creates a new medicine in the system.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This method is intended to create a new medicine
     */
    @Override
    @Transactional
    public MedicineResponseDTO create(MedicineRequestDTO request) {
        KindOfMedicineEntity kindOfMedicineEntity = kindOfMedicineRepository.findById(request.getKindOfMedicineId())
                .orElseThrow(() -> new ApiException(ErrorCode.KIND_OF_MEDICINE_NOT_FOUND));

        MedicineEntity medicineEntity = medicineMapper.toMedicineEntity(request);

        saveMedicineImages(request, medicineEntity);

        saveListUnitDetail(request, medicineEntity);

        medicineEntity.setKindOfMedicineEntity(kindOfMedicineEntity);
        medicineEntity.setCode(String.valueOf(new Random().nextInt(1000000)));
        medicineRepository.save(medicineEntity);

        return getMedicineResponseDTO(medicineEntity);
    }

    /**
     * Updates an existing medicine's details.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: This method updates the details of an existing medicine
     */
    @Override
    @Transactional
    public MedicineResponseDTO update(Long id, MedicineRequestDTO request) {
        MedicineEntity medicineEntity = medicineRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.MEDICINE_NOT_FOUND));

        KindOfMedicineEntity kindOfMedicineEntity = kindOfMedicineRepository.findById(request.getKindOfMedicineId())
                .orElseThrow(() -> new ApiException(ErrorCode.KIND_OF_MEDICINE_NOT_FOUND));

        MedicineEntity medicineEntityMap = medicineMapper.toMedicineEntity(request);
        medicineEntityMap.setId(id);
        medicineEntityMap.setCode(medicineEntity.getCode());
        medicineEntityMap.setKindOfMedicineEntity(kindOfMedicineEntity);

        medicineRepository.save(medicineEntityMap);

        if (!CollectionUtils.isEmpty(request.getUnitDetails())) {
            unitDetailRepository.deleteAll(unitDetailRepository.findByMedicineEntityId(id));
            saveListUnitDetail(request, medicineEntityMap);
        }

        if (!CollectionUtils.isEmpty(request.getImageUrls())) {
            List<ImageMedicineEntity> oldImages = imageMedicineRepository.findByMedicineEntityId(id)
                    .orElse(List.of());
            imageMedicineRepository.deleteAll(oldImages);
            saveMedicineImages(request, medicineEntityMap);
        }

        return getMedicineResponseDTO(medicineEntityMap);
    }

    /**
     * Deletes a medicine by its ID.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: This method marks a medicine as deleted by setting its flagDeleted field to true.
     */
    @Override
    public void delete(Long id) {
        MedicineEntity medicineEntity = medicineRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.MEDICINE_NOT_FOUND));
        medicineEntity.setFlagDeleted(true);
        medicineRepository.save(medicineEntity);
    }

    /**
     * Searches for medicines based on various criteria.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: This method allows searching for medicines using the provided search criteria.
     */
    @Override
    public PageResponse<?> searchMedicines(MedicineSearchRequestDTO request, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, pageSize);

        Specification<MedicineEntity> specification = medicineSpecificationFactory.buildMedicineSpecification(request);
        Page<MedicineEntity> medicinePage = medicineRepository.findAll(specification, pageable);

        List<MedicineResponseDTO> medicineResponseDTOList = medicinePage
                .map(this::getMedicineResponseDTO)
                .toList();

        return PageResponse.builder()
                .page(page)
                .pageSize(pageSize)
                .totalPages(medicinePage.getTotalPages())
                .items(medicineResponseDTOList)
                .build();
    }

    /**
     * Saves the unit details for a medicine based on the request data.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: This method processes the unit details from the request,
     */
    private void saveListUnitDetail(MedicineRequestDTO request, MedicineEntity medicineEntity) {
        List<UnitDetailEntity> unitDetails = request.getUnitDetails().stream()
                .map(unitDetailDTO -> UnitDetailEntity.builder()
                        .conversion_unit(unitDetailDTO.getConversionUnit())
                        .medicineEntity(medicineEntity)
                        .unitEntity(unitRepository.findById(unitDetailDTO.getUnitId())
                                .orElseThrow(() -> new ApiException(ErrorCode.UNIT_NOT_FOUND)))
                        .build())
                .collect(Collectors.toList());
        unitDetailRepository.saveAll(unitDetails);
    }

    /**
     * Constructs a MedicineResponseDTO from a Medicine entity.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This method maps a Medicine entity to a MedicineResponseDTO,
     */
    private MedicineResponseDTO getMedicineResponseDTO(MedicineEntity medicineEntity) {
        MedicineResponseDTO responseDTO = medicineMapper.toMedicineResponseDTO(medicineEntity);

        List<UnitDetailResponseDTO> unitDetails = unitDetailRepository
                .findValidUnitDetailsByMedicineEntityId(medicineEntity.getId())
                .stream()
                .map(unitDetail -> UnitDetailResponseDTO.builder()
                        .conversionUnit(unitDetail.getConversion_unit())
                        .id(unitDetail.getId())
                        .unitId(unitDetail.getUnitEntity().getId())
                        .unitName(unitDetail.getUnitEntity().getName())
                        .build())
                .collect(Collectors.toList());

        List<String> imagePaths = imageMedicineRepository.findByMedicineEntityId(medicineEntity.getId())
                .orElseThrow(() -> new ApiException(ErrorCode.IMAGE_NOT_FOUND))
                .stream()
                .map(ImageMedicineEntity::getImage_path)
                .collect(Collectors.toList());

        responseDTO.setUnitDetails(unitDetails);
        responseDTO.setImages(imagePaths);

        return responseDTO;
    }

    /**
     * Saves the images for a medicine from the provided files.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: This method uploads images to the cloud and saves their URLs in the database.
     */
    private void saveMedicineImages(MedicineRequestDTO requestDTO, MedicineEntity medicineEntity) {
        List<ImageMedicineEntity> imageMedicines = requestDTO.getImageUrls().stream()
                .map(url -> ImageMedicineEntity.builder()
                        .image_path(url)
                        .medicineEntity(medicineEntity)
                        .flag_deleted(false)
                        .build())
                .collect(Collectors.toList());
        imageMedicineRepository.saveAll(imageMedicines);
    }
}

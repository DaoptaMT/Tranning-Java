package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.dto.PageResponse;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineRequestDTO;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineResponseDTO;
import com.mt.pharmacy_be.dto.unitDetailDTO.UnitDetailResponseDTO;
import com.mt.pharmacy_be.entity.Image_Medicine;
import com.mt.pharmacy_be.entity.Kind_Of_Medicine;
import com.mt.pharmacy_be.entity.Medicine;
import com.mt.pharmacy_be.entity.Unit_Detail;
import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.exception.ApiException;
import com.mt.pharmacy_be.mapper.MedicineMapper;
import com.mt.pharmacy_be.repository.*;
import com.mt.pharmacy_be.service.MedicineService;
import com.mt.pharmacy_be.service.cloudinary.CloudinaryService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    CloudinaryService cloudinaryService;
    ImageMedicineRepository imageMedicineRepository;
    KindOfMedicineRepository kindOfMedicineRepository;
    UnitRepository unitRepository;

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
     * Creates a new medicine in the system.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This method is intended to create a new medicine
     */
    @Override
    @Transactional
    public MedicineResponseDTO create(MedicineRequestDTO request, List<MultipartFile> files) {
        Kind_Of_Medicine kindOfMedicine = kindOfMedicineRepository.findById(request.getKindOfMedicineId())
                .orElseThrow(() -> new ApiException(ErrorCode.KIND_OF_MEDICINE_NOT_FOUND));

        Medicine medicine = medicineMapper.toMedicineEntity(request);

        List<String> imageUrls = files.stream()
                .map(file -> {
                    try {
                        return cloudinaryService.uploadImage(file);
                    } catch (IOException e) {
                        throw new ApiException(ErrorCode.FAILED_TO_UPLOAD_IMAGE);
                    }
                })
                .toList();

        List<Image_Medicine> imageMedicines = imageUrls.stream()
                .map(url -> Image_Medicine.builder()
                        .image_path(url)
                        .medicine(medicine)
                        .flag_deleted(false)
                        .build())
                .collect(Collectors.toList());
        imageMedicineRepository.saveAll(imageMedicines);

        List<Unit_Detail> unitDetails = request.getUnitDetails().stream()
                .map(unitDetailDTO -> Unit_Detail.builder()
                        .conversion_unit(unitDetailDTO.getConversionUnit())
                        .medicine(medicine)
                        .unit(unitRepository.findById(unitDetailDTO.getUnitId())
                                .orElseThrow(() -> new ApiException(ErrorCode.UNIT_NOT_FOUND)))
                        .build())
                .collect(Collectors.toList());
        unitDetailRepository.saveAll(unitDetails);

        medicine.setKindOfMedicine(kindOfMedicine);
        medicine.setCode(String.valueOf(new Random().nextInt(1000000)));
        medicineRepository.save(medicine);

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

        List<String> imagePaths = imageMedicineRepository.findByMedicineId(medicine.getId())
                .orElseThrow(() -> new ApiException(ErrorCode.IMAGE_NOT_FOUND))
                .stream()
                .map(Image_Medicine::getImage_path)
                .collect(Collectors.toList());

        responseDTO.setUnitDetails(unitDetails);
        responseDTO.setImages(imagePaths);

        return responseDTO;
    }
}

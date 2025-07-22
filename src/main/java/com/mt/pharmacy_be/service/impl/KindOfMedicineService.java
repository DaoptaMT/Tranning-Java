package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.dto.kindofmedicineDTO.KindOfMedicineRequestDTO;
import com.mt.pharmacy_be.dto.kindofmedicineDTO.KindOfMedicineResponseDTO;
import com.mt.pharmacy_be.entity.KindOfMedicine;
import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.exception.ApiException;
import com.mt.pharmacy_be.repository.KindOfMedicineRepository;
import com.mt.pharmacy_be.service.KindOfMedicineServiceInterface;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class KindOfMedicineService implements KindOfMedicineServiceInterface {

     ModelMapper modelMapper;
     KindOfMedicineRepository kindOfMedicineRepository;
     

    @Override
    public KindOfMedicineResponseDTO createKindOfMedicine(KindOfMedicineRequestDTO requestDTO) {
//        KindOfMedicineResponseDTO kind = getKindOfMedicineByCode(requestDTO.getCode());
        Optional<KindOfMedicine> kind = kindOfMedicineRepository.findByCode(requestDTO.getCode());

        if (kind != null) {
            throw new ApiException(ErrorCode.KIND_EXISTED);
        }
        KindOfMedicine kindOfMedicine = modelMapper.map(requestDTO, KindOfMedicine.class);
        kindOfMedicineRepository.save(kindOfMedicine);
        return modelMapper.map(getKindOfMedicineByCode(kindOfMedicine.getCode()),
                KindOfMedicineResponseDTO.class);
    }

    @Override
    public List<?> getKindOfMedicineByName(String name) {
        List<KindOfMedicine> kindOfMedicines = kindOfMedicineRepository.findByName(name);

        if (kindOfMedicines.isEmpty()) {
            return List.of();  // Trả về một list rỗng rõ ràng
        }

        return kindOfMedicines.stream()
                .map(item -> modelMapper.map(item, KindOfMedicineResponseDTO.class))
                .toList();
    }
    @Override
    public KindOfMedicineResponseDTO getKindOfMedicineByCode(String code) {
        KindOfMedicine kindOfMedicine =  kindOfMedicineRepository.findByCode(code)
                .orElseThrow(() -> new ApiException(ErrorCode.KIND_NOT_FOUND));
        return modelMapper.map(kindOfMedicine, KindOfMedicineResponseDTO.class);
    }


    @Override
    public Page<KindOfMedicineResponseDTO> getAllKindOfMedicines(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<KindOfMedicine> pageResult = kindOfMedicineRepository.findAll(pageable);

        return pageResult.map(item -> modelMapper.map(item, KindOfMedicineResponseDTO.class));
    }


    public String deleteKindOfMedicine(String code) {
        // Tìm loại thuốc theo code
        KindOfMedicine kindOfMedicine = kindOfMedicineRepository.findByCode(code)
                .orElseThrow(() -> new ApiException(ErrorCode.KIND_NOT_FOUND));

        // Đánh dấu đã xóa
        kindOfMedicine.setFlag_deleted(true);

        // Lưu lại vào database
        kindOfMedicineRepository.save(kindOfMedicine);

        return "Kind of medicine deleted successfully";

    }

    @Override
    public KindOfMedicineResponseDTO updateKindOfMedicine(String oldcode,KindOfMedicineRequestDTO kindOfMedicineRequestDTO) {
        // Tìm đối tượng trong DB dựa vào code
        KindOfMedicine existingEntity = kindOfMedicineRepository.findByCode(oldcode)
                .orElseThrow(() -> new ApiException(ErrorCode.KIND_NOT_FOUND));

        // Cập nhật các field cần thay đổi
        existingEntity.setName(kindOfMedicineRequestDTO.getName());
        existingEntity.setFlag_deleted(kindOfMedicineRequestDTO.isFlag_deleted());
        existingEntity.setCode(kindOfMedicineRequestDTO.getCode());
        // Nếu có thêm field khác, bạn cũng update tương tự

        // Lưu lại
        kindOfMedicineRepository.save(existingEntity);

        // Trả về response DTO
        return modelMapper.map(existingEntity, KindOfMedicineResponseDTO.class);
    }


}

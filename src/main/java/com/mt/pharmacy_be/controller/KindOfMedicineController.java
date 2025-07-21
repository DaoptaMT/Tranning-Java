package com.mt.pharmacy_be.controller;

import com.mt.pharmacy_be.dto.PageResponse;
import com.mt.pharmacy_be.dto.kindOfMedicineDTO.KindOfMedicineRequestDTO;
import com.mt.pharmacy_be.dto.kindOfMedicineDTO.KindOfMedicineResponseDTO;
import com.mt.pharmacy_be.service.KindOfMedicineServiceInterface;
import com.mt.pharmacy_be.util.JsonResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(makeFinal = true,level = lombok.AccessLevel.PRIVATE)
@RequestMapping("/api/v1/kindofmedicine")
public class KindOfMedicineController {



    KindOfMedicineServiceInterface kindOfMedicineService;


    ModelMapper modelMapper;
/**
 * Handles get all kind of medicines requests.
 * Author: Tri Dung
 * Date: 21/7/2025
 * Description: This endpoint retrieves a paginated list of all kind of medicines.
 */
    @GetMapping("")

    public ResponseEntity<?> getAllKindOfMedicines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<KindOfMedicineResponseDTO> result = kindOfMedicineService.getAllKindOfMedicines(page, size);
        PageResponse<List<?>>  list = PageResponse.<List<?>>builder()
                .page(result.getPageable().getPageNumber())
                .pageSize(result.getPageable().getPageSize())
                .totalPages(result.getTotalPages())
                .items(result.getContent())
                .build();
        return JsonResponse.ok(list);
    }

    /**
     * Handles get kind of medicine by code requests.
     * Author: Tri Dung
     * Date: 21/7/2025
     * Description: This endpoint retrieves a kind of medicine by its code.
     * */
    @GetMapping("/{code}")
    public ResponseEntity<?> getKindOfMedicineByCode(@PathVariable String code) {
        KindOfMedicineResponseDTO kindOfMedicine = modelMapper.map(kindOfMedicineService.getKindOfMedicineByCode(code), KindOfMedicineResponseDTO.class);
      return JsonResponse.ok(kindOfMedicine);
    }
    /**
     * Handles create kind of medicine requests.
     * Author: Tri Dung
     * Date: 21/7/2025
     * Description: This endpoint creates a new kind of medicine.
     * */
    @PostMapping("")
    public  ResponseEntity<?> createKindOfMedicine(@Valid @RequestBody KindOfMedicineRequestDTO kindOfMedicineDTO) {
        KindOfMedicineResponseDTO createdKindOfMedicine = kindOfMedicineService.createKindOfMedicine(kindOfMedicineDTO);
        return JsonResponse.created(createdKindOfMedicine);
    }
    /**
     * Handles delete kind of medicine requests.
     * Author: Tri Dung
     * Date: 21/7/2025
     * Description: This endpoint deletes a kind of medicine by its code.
     * */
    @DeleteMapping("{code}")
    public ResponseEntity<?> deleteKindOfMedicine(@PathVariable String code) {
       String result = kindOfMedicineService.deleteKindOfMedicine(code);
        if (result != null) {
            return ResponseEntity.ok("Kind of medicine deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Handles update kind of medicine requests.
     * Author: Tri Dung
     * Date: 21/7/2025
     * Description: This endpoint updates an existing kind of medicine by its code.
     * */
    @PutMapping("{code}")
    public ResponseEntity<?> updateKindOfMedicine(@PathVariable String code, @RequestBody KindOfMedicineRequestDTO kindOfMedicineDTO) {
        KindOfMedicineResponseDTO updatedKindOfMedicine = modelMapper.map(kindOfMedicineService.updateKindOfMedicine(code,kindOfMedicineDTO), KindOfMedicineResponseDTO.class);
        if (updatedKindOfMedicine != null) {
            return JsonResponse.ok(updatedKindOfMedicine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
   /**
    * Handles search kind of medicine by name requests.
    * Author: Tri Dung
    * Date: 21/7/2025
    * Description: This endpoint searches for kind of medicines by their name.
    * */
    @GetMapping("/search")
    public ResponseEntity<?> getKindOfMedicineByName(@RequestParam String name) {
        List<?> kindOfMedicines = kindOfMedicineService.getKindOfMedicineByName(name);
        return JsonResponse.ok(kindOfMedicines);
    }
}
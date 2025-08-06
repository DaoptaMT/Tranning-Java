package com.mt.pharmacy_be.controller;

import com.mt.pharmacy_be.dto.kindOfMedicineDTO.KindOfMedicineRequestDTO;
import com.mt.pharmacy_be.service.KindOfMedicineService;
import com.mt.pharmacy_be.util.JsonResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@RequestMapping("/api/v1/kinds-of-medicine")
public class KindOfMedicineController {

    KindOfMedicineService kindOfMedicineService;

    /**
     * Handles get all kind of medicines requests.
     * Author: Tri Dung
     * Date: 21/7/2025
     * Description: This endpoint retrieves a paginated list of all kind of medicines.
     */
    @GetMapping()
    public ResponseEntity<?> getAllKindOfMedicines(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return JsonResponse.ok(kindOfMedicineService.getAllKindOfMedicines(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return JsonResponse.ok(kindOfMedicineService.getById(id));
    }

    /**
     * Handles get kind of medicine by code requests.
     * Author: Tri Dung
     * Date: 21/7/2025
     * Description: This endpoint retrieves a kind of medicine by its code.
     */
//    @GetMapping("/{code}")
//    public ResponseEntity<?> getKindOfMedicineByCode(@PathVariable String code) {
//        KindOfMedicineResponseDTO kindOfMedicine = modelMapper.map(kindOfMedicineService.getKindOfMedicineByCode(code), KindOfMedicineResponseDTO.class);
//        return JsonResponse.ok(kindOfMedicine);
//    }

    /**
     * Handles create kind of medicine requests.
     * Author: Tri Dung
     * Date: 21/7/2025
     * Description: This endpoint creates a new kind of medicine.
     */
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    @PostMapping()
    public ResponseEntity<?> createKindOfMedicine(@Valid @RequestBody KindOfMedicineRequestDTO kindOfMedicineDTO) {
        return JsonResponse.created(kindOfMedicineService.createKindOfMedicine(kindOfMedicineDTO));
    }

    /**
     * Handles delete kind of medicine requests.
     * Author: Tri Dung
     * Date: 21/7/2025
     * Description: This endpoint deletes a kind of medicine by its code.
     */
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKindOfMedicine(@PathVariable Long id) {
        kindOfMedicineService.deleteKindOfMedicine(id);
        return JsonResponse.deleted();
    }

    /**
     * Handles update kind of medicine requests.
     * Author: Tri Dung
     * Date: 21/7/2025
     * Description: This endpoint updates an existing kind of medicine by its code.
     */
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateKindOfMedicine(@PathVariable Long id,
                                                  @RequestBody KindOfMedicineRequestDTO kindOfMedicineDTO) {
        return JsonResponse.ok(kindOfMedicineService.updateKindOfMedicine(id, kindOfMedicineDTO));
    }

    /**
     * Handles search kind of medicine by name requests.
     * Author: Tri Dung
     * Date: 21/7/2025
     * Description: This endpoint searches for kind of medicines by their name.
     */
    @GetMapping("/search")
    public ResponseEntity<?> getKindOfMedicineByName(@RequestParam String name,
                                                     @RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return JsonResponse.ok(kindOfMedicineService.getKindOfMedicineByName(name, page, size));
    }
}
package com.mt.pharmacy_be.controller;

import com.mt.pharmacy_be.dto.unitDTO.UnitRequestDTO;
import com.mt.pharmacy_be.service.UnitService;
import com.mt.pharmacy_be.util.JsonResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/units")
public class UnitController {

    UnitService unitService;

    /**
     * Handles requests to retrieve all units with pagination.
     * Author: Thanh Truc
     * Date: 06/08/2025
     * Description: This endpoint retrieves a paginated list of all units.
     */
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return JsonResponse.ok(unitService.getAll(page, pageSize));
    }

    /**
     * Handles requests to retrieve a unit by its ID.
     * Author: Thanh Truc
     * Date: 06/08/2025
     * Description: This endpoint retrieves a specific unit by its ID.
     */
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return JsonResponse.ok(unitService.getById(id));
    }

    /**
     * Handles requests to create a new unit.
     * Author: Thanh Truc
     * Date: 06/08/2025
     * Description: This endpoint creates a new unit in the system.
     */
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid UnitRequestDTO unit) {
        return JsonResponse.ok(unitService.create(unit));
    }

    /**
     * Handles requests to update an existing unit.
     * Author: Thanh Truc
     * Date: 06/08/2025
     * Description: This endpoint updates an existing unit in the system.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                   @RequestBody @Valid UnitRequestDTO unit) {
        return JsonResponse.ok(unitService.update(id, unit));
    }

    /**
     * Handles requests to delete a unit by its ID.
     * Author: Thanh Truc
     * Date: 06/08/2025
     * Description: This endpoint deletes a specific unit by its ID.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        unitService.delete(id);
        return JsonResponse.deleted();
    }

    /**
     * Handles requests to search for units by name.
     * Author: Thanh Truc
     * Date: 06/08/2025
     * Description: This endpoint allows searching for units based on their name.
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam String name,
                                         @RequestParam(required = false, defaultValue = "1") int page,
                                         @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return JsonResponse.ok(unitService.searchByName(name, page, pageSize));
    }
}

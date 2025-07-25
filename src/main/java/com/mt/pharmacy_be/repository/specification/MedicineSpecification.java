package com.mt.pharmacy_be.repository.specification;

import com.mt.pharmacy_be.entity.Medicine;
import org.springframework.data.jpa.domain.Specification;

public class MedicineSpecification {

    /**
     * Function: Filters medicines by code using case-insensitive partial matching.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns a Specification that checks if the medicine's code contains the given value.
     */
    public static Specification<Medicine> hasCode(String code) {
        return (root, query, cb) ->
                code == null ? null : cb.like(cb.lower(root.get("code")), "%" + code.toLowerCase() + "%");
    }

    /**
     * Function: Filters medicines by name using case-insensitive partial matching.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns a Specification that checks if the medicine's name contains the given value.
     */
    public static Specification<Medicine> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    /**
     * Function: Filters medicines by minimum price.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns a Specification that checks if the medicine's price is greater than or equal to the given value.
     */
    public static Specification<Medicine> minPrice(Double minPrice) {
        return (root, query, cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    /**
     * Function: Filters medicines by maximum price.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns a Specification that checks if the medicine's price is less than or equal to the given value.
     */
    public static Specification<Medicine> maxPrice(Double maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    /**
     * Function: Filters medicines by minimum quantity in stock.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns a Specification that checks if the medicine's quantity is greater than or equal to the given value.
     */
    public static Specification<Medicine> minQuantity(Long minQuantity) {
        return (root, query, cb) ->
                minQuantity == null ? null : cb.greaterThanOrEqualTo(root.get("quantity"), minQuantity);
    }

    /**
     * Function: Filters medicines by maximum quantity in stock.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns a Specification that checks if the medicine's quantity is less than or equal to the given value.
     */
    public static Specification<Medicine> maxQuantity(Long maxQuantity) {
        return (root, query, cb) ->
                maxQuantity == null ? null : cb.lessThanOrEqualTo(root.get("quantity"), maxQuantity);
    }

    /**
     * Function: Filters medicines by maker (manufacturer) using case-insensitive partial matching.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns a Specification that checks if the medicine's maker contains the given value.
     */
    public static Specification<Medicine> hasMaker(String maker) {
        return (root, query, cb) ->
                maker == null ? null : cb.like(cb.lower(root.get("maker")), "%" + maker.toLowerCase() + "%");
    }

    /**
     * Function: Filters medicines by origin using case-insensitive partial matching.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns a Specification that checks if the medicine's origin contains the given value.
     */
    public static Specification<Medicine> hasOrigin(String origin) {
        return (root, query, cb) ->
                origin == null ? null : cb.like(cb.lower(root.get("origin")), "%" + origin.toLowerCase() + "%");
    }

    /**
     * Function: Filters medicines by active ingredient using case-insensitive partial matching.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns a Specification that checks if the medicine's active element contains the given value.
     */
    public static Specification<Medicine> hasActiveElement(String activeElement) {
        return (root, query, cb) ->
                activeElement == null ? null : cb.like(cb.lower(root.get("activeElement")), "%" + activeElement.toLowerCase() + "%");
    }

    /**
     * Function: Filters medicines by kind of medicine ID.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns a Specification that checks if the medicine belongs to the specified kind of medicine.
     */
    public static Specification<Medicine> hasKindOfMedicine(Long kindOfMedicineId) {
        return (root, query, cb) ->
                kindOfMedicineId == null ? null :
                        cb.equal(root.get("kindOfMedicine").get("id"), kindOfMedicineId);
    }

    /**
     * Function: Sorts medicines by price in ascending or descending order.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns a Specification that applies sorting by price based on the order ("ASC" or "DESC").
     */
    public static Specification<Medicine> sortPrice(String order) {
        return (root, query, cb) -> {
            if (order == null) return null;
            if ("ASC".equalsIgnoreCase(order)) {
                assert query != null;
                query.orderBy(cb.asc(root.get("price")));
            } else if ("DESC".equalsIgnoreCase(order)) {
                assert query != null;
                query.orderBy(cb.desc(root.get("price")));
            }
            return null;
        };
    }

}


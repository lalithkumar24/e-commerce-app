package com.laltih.ecommerce.product;


public record ProductResponse(
        Integer id,
        String name,
        String description,
        Double availableQuantity,
        java.math.BigDecimal price,
        Integer categoryId,
        String categoryName,
        String categoryDescription
) {
}

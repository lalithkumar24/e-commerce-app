package com.laltih.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        @NotNull(message = "Product must have name")
        String name,
        @NotNull(message = "product must have a valid description")
        String description,
        @Positive(message = "product's available quantity must be positive")
        Double availableQuantity,
        @Positive(message = "product price must be positive ")
        BigDecimal price,
        @NotNull(message = "product categoryId is required")
        Integer categoryId
) {
}

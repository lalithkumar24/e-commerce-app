package com.laltih.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product must be present")
        Integer productId,
        @Positive(message = "Quantity must be postive")
        double quantity
) {
}

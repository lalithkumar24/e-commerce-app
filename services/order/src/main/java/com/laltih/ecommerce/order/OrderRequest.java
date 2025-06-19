package com.laltih.ecommerce.order;

import com.laltih.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "order amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Payment methode should be added")
        PaymentMethod paymentMethod,
        @NotNull(message = "customer Id should be present")
        @NotEmpty(message = "customer Id should be present")
        @NotBlank(message = "customer Id should be present")
        String customerId,
        @NotNull(message = "you should at least by one product")
        List<PurchaseRequest> products
) {
}

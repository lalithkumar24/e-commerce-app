package com.lalith.ecommerce.kafka.payment;

import java.math.BigDecimal;

public record PaymentConformation(
        String orderReference,
        BigDecimal TotalAmount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
) {
}

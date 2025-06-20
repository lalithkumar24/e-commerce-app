package com.lalith.ecommerce.payment;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
        
) {
}

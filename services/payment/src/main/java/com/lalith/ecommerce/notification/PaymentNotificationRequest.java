package com.lalith.ecommerce.notification;

import com.lalith.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        String CustomerFirstName,
        String CustomerLastName,
        String CustomerEmail
) {
}

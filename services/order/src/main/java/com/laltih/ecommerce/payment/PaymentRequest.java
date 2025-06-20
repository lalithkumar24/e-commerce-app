package com.laltih.ecommerce.payment;

import com.laltih.ecommerce.customer.CustomerResponse;
import com.laltih.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer

) {
}

package com.laltih.ecommerce.kafka;

import com.laltih.ecommerce.customer.CustomerResponse;
import com.laltih.ecommerce.order.PaymentMethod;
import com.laltih.ecommerce.product.ProductPurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<ProductPurchaseResponse> products

) {
}

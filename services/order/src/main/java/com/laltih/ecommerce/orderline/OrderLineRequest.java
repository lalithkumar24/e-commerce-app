package com.laltih.ecommerce.orderline;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(
        Integer id,
        Integer Orderid,
        Integer productid,
        double quantity
) {
}

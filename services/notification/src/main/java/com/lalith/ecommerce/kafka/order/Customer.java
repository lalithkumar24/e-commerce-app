package com.lalith.ecommerce.kafka.order;

public record Customer(
        String Id,
        String firstName,
        String lastName,
        String email
) {
}

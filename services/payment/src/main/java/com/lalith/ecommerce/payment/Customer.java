package com.lalith.ecommerce.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
    String Id,
    @NotNull(message = "First Name is required")
    String firstName,
    @NotNull(message = "Last Name is required")
    String lastName,
    @NotNull(message = "Email can't be null")
    @Email(message = "Email is not correct format")
    String email
) {
}

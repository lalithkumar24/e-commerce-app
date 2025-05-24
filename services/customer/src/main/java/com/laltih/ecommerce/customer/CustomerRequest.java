package com.laltih.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
public record CustomerRequest(
         String id,
         @NotNull(message = "customer firstname is required")
         String firstName,
         @NotNull(message = "customer firstname is required")
         String lastName,
         @NotNull(message = "customer email is required")
         @Email(message = "customer email is not a valid email")
         String email,

         Address address
) {
}

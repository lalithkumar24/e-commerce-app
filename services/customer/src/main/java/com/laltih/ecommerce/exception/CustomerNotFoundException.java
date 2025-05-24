package com.laltih.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class CustomerNotFoundException extends RuntimeException {
    private final String msg;
}

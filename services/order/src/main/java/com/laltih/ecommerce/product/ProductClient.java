package com.laltih.ecommerce.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(
        name="product-service",
        url = "${application.config.product-url}"
)
public interface ProductClient {
    @PostMapping(value = "/purchase",consumes = "application/json",produces = "application/json")
    List<ProductPurchaseResponse> purchaseProducts(@RequestBody List<PurchaseRequest> request);
}

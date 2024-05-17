package com.example.paymentsService.controllers;

import com.example.paymentsService.dto.PaymentDataDto;
import com.example.paymentsService.dto.ProductResponseDto;
import com.example.paymentsService.dto.UserProductDto;
import com.example.paymentsService.dto.UserProductsList;
import com.example.paymentsService.services.ProductIntegrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentServiceController {

    private final ProductIntegrationService productIntegrationService;

    public PaymentServiceController(ProductIntegrationService productIntegrationService) {
        this.productIntegrationService = productIntegrationService;
    }

    @GetMapping("/getAllProducts")
    public ProductResponseDto getProductsByProductService() {
        return productIntegrationService.callAllProductsMethod("/users/1");
    }

    @PostMapping("/execute")
    public ProductResponseDto executePayment(@RequestBody PaymentDataDto paymentDataDto) {
        return productIntegrationService.callProductMethod("/products/" + paymentDataDto.getProductId(), paymentDataDto);
    }

}

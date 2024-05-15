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
        return new ProductResponseDto("Список счетов пользователя",
                productIntegrationService.callAllProductsMethod("/users/1"));
    }

    @PostMapping("/execute")
    public ProductResponseDto executePayment(@RequestBody PaymentDataDto paymentDataDto) {
        Double paymentSum = paymentDataDto.getSum(); // сумма, которая нужна для оплаты заказа
        Long paymentId = paymentDataDto.getProductId(); // id счёта, с которого будет списана оплата

        UserProductDto product = productIntegrationService.callProductMethod("/products/" + paymentId);

        Double realBalance = product.balance();

        if (realBalance < paymentSum) {
            return new ProductResponseDto("Оплата невозможна: на балансе недостаточно средств", realBalance);
        }

        return new ProductResponseDto("Результат оплаты", realBalance - paymentSum);
    }

}

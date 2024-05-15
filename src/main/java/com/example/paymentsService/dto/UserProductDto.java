package com.example.paymentsService.dto;

public record UserProductDto(Long id, String billingNumber, Double balance, String productType) {
}

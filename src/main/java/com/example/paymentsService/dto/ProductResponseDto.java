package com.example.paymentsService.dto;

public class ProductResponseDto {
    private final String message;
    private final Object object;

    public ProductResponseDto(String clientMessage, Object object) {
        this.message = clientMessage;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return object;
    }
}

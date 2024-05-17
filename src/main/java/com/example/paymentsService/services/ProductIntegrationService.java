package com.example.paymentsService.services;

import com.example.paymentsService.configs.props.ExecutorProperties;
import com.example.paymentsService.dto.PaymentDataDto;
import com.example.paymentsService.dto.ProductResponseDto;
import com.example.paymentsService.dto.UserProductDto;
import com.example.paymentsService.dto.UserProductsList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductIntegrationService {

    private final RestTemplate executorClient;

    private final ExecutorProperties properties;

    public ProductIntegrationService(RestTemplate executorClient, ExecutorProperties properties) {
        this.executorClient = executorClient;
        this.properties = properties;
    }

    public ProductResponseDto callAllProductsMethod(String integrationMethodName) {
        String productsUrl = properties.getProductsExecutorClient().url() + integrationMethodName; // id пользователя
        ResponseEntity<UserProductsList> response = executorClient.getForEntity(productsUrl, UserProductsList.class);

        UserProductsList productsList = response.getBody();

        if (productsList == null || productsList.getUserProductDtoList().isEmpty()) {
            return new ProductResponseDto("Счета для пользователя не найдены", productsList);
        }

        return new ProductResponseDto("Список счетов пользователя", response.getBody());
    }

    public ProductResponseDto callProductMethod(String integrationMethodName, PaymentDataDto paymentDataDto) {
        Double paymentSum = paymentDataDto.getSum(); // сумма, которая нужна для оплаты заказа
        Long paymentId = paymentDataDto.getProductId(); // id счёта, с которого будет списана оплата

        String productsUrl = properties.getProductsExecutorClient().url() + integrationMethodName; // id пользователя
        ResponseEntity<UserProductDto> response = executorClient.getForEntity(productsUrl, UserProductDto.class);

        UserProductDto product = response.getBody();

        if (product == null) {
            return new ProductResponseDto("Оплата невозможна: счёт не найден", null);
        }

        Double realBalance = product.balance();

        if (realBalance < paymentSum) {
            return new ProductResponseDto("Оплата невозможна: на балансе недостаточно средств", realBalance);
        }

        return new ProductResponseDto("Результат оплаты", realBalance - paymentSum);
    }
}

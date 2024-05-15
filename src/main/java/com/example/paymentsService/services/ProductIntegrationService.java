package com.example.paymentsService.services;

import com.example.paymentsService.configs.props.ExecutorProperties;
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

    public UserProductsList callAllProductsMethod(String integrationMethodName) {
        String productsUrl = properties.getProductsExecutorClient().url() + integrationMethodName; // id пользователя
        ResponseEntity<UserProductsList> response = executorClient.getForEntity(productsUrl, UserProductsList.class);

        return response.getBody();
    }

    public UserProductDto callProductMethod(String integrationMethodName) {
        String productsUrl = properties.getProductsExecutorClient().url() + integrationMethodName; // id пользователя
        ResponseEntity<UserProductDto> response = executorClient.getForEntity(productsUrl, UserProductDto.class);

        return response.getBody();
    }
}

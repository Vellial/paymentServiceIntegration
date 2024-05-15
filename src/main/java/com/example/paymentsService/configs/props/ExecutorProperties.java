package com.example.paymentsService.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "integrations")
public class ExecutorProperties {
    private final RestTemplateProperties productExecutorClient;

    @ConstructorBinding
    public ExecutorProperties(RestTemplateProperties productExecutorClient) {
        this.productExecutorClient = productExecutorClient;
    }

    public RestTemplateProperties getProductsExecutorClient() {
        return productExecutorClient;
    }
}

package com.example.paymentsService.configs;

import com.example.paymentsService.configs.props.ExecutorProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableConfigurationProperties({
        ExecutorProperties.class
})
public class AppConfig {

    @Bean
    public RestTemplate executorClient(
            ExecutorProperties executorProperties,
            RTResponseErrorHandler errorHandler) {

        return new RestTemplateBuilder()
                .rootUri(executorProperties.getProductsExecutorClient().url())
                .setConnectTimeout(executorProperties.getProductsExecutorClient().connectionTimeout())
                .setReadTimeout(executorProperties.getProductsExecutorClient().readTimeout())
                .errorHandler(errorHandler)
                .build();
    }
}

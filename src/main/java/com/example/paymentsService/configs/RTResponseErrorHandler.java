package com.example.paymentsService.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import com.example.paymentsService.dto.ExecutorErrorDto;

import java.io.IOException;

@Component
public class RTResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatusCode statusCode = response.getStatusCode();
        return statusCode.is4xxClientError() || statusCode.is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            ObjectMapper objectMapper = new ObjectMapper();
            ExecutorErrorDto executorErrorDto = objectMapper.readValue(response.getBody(), ExecutorErrorDto.class);
            throw new RuntimeException(executorErrorDto.message());
        }
    }
}

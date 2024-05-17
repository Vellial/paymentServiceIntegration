package com.example.paymentsService.configs.props;

import java.time.Duration;

public record RestTemplateProperties(String url, Duration connectionTimeout, Duration readTimeout) {
}

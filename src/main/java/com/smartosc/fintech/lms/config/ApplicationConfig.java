package com.smartosc.fintech.lms.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationConfig {
    @Value("${payment.gateway.url}")
    private String paymentGatewayUrl;
}

package com.smartosc.fintech.lms.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    @Data
    public static class PaymentResponseStatus {
        private int code;
        private String message;
    }

    private PaymentResponseStatus status;
    private String data;
}

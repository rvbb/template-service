package com.smartosc.fintech.lms.exception;

import lombok.Data;

@Data
public class InternalServiceException extends RuntimeException {
    private final String code;

    public InternalServiceException(String message) {
        this(message, null);
    }

    public InternalServiceException(String message, String code) {
        super(message);
        this.code = code;
    }
}

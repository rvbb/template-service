package com.smartosc.fintech.lms.exception;

import lombok.Data;

@Data
public class BusinessServiceException extends RuntimeException {
    private final int code;

    public BusinessServiceException(String message, int code) {
        super(message);
        this.code = code;
    }
}

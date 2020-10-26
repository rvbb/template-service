package com.smartosc.lending.lms.service.exception.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Error {

  private HttpStatus httpStatus;

  private LocalDateTime localDateTime;

  private String message;

  private String debugMessage;

  private List<DetailError> detailErrors;

  public Error() {
    this.localDateTime = LocalDateTime.now();
  }

  public Error(HttpStatus httpStatus) {
    this();
    this.httpStatus = httpStatus;
  }

  public Error(HttpStatus httpStatus, Throwable throwable) {
    this();
    this.httpStatus = httpStatus;
    this.message = "Unexpected error";
    this.debugMessage = throwable.getMessage();
  }

  public Error(HttpStatus httpStatus, String message, Throwable throwable) {
    this();
    this.httpStatus = httpStatus;
    this.message = message;
    this.debugMessage = throwable.getMessage();
  }
}

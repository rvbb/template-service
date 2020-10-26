package com.smartosc.lending.lms.service.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationError<T> implements DetailError {
  private String object;

  private String field;

  private T rejectedValue;

  private String message;

  public ValidationError(String object, String message) {
    this.object = object;
    this.message = message;
  }
}

package com.smartosc.fintech.lms.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ApiValidationError<T> extends ApiSubError {
  private String object;

  private String field;

  private T rejectedValue;

  private String message;

  public ApiValidationError(String object, String message) {
    this.object = object;
    this.message = message;
  }
}

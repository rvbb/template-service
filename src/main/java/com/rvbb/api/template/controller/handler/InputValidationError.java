package com.rvbb.api.template.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class InputValidationError<T> extends NestedError {
  private String object;

  private String field;

  private T wrongValue;

  private String message;

  public InputValidationError(String object, String message) {
    this.object = object;
    this.message = message;
  }
}
